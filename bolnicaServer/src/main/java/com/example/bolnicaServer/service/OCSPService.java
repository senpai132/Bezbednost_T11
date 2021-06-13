package com.example.bolnicaServer.service;

//import org.apache.http.HttpEntity;
import com.example.bolnicaServer.config.HospitalKeyStore;
import com.example.bolnicaServer.config.RestTemplateConfiguration;
import com.example.bolnicaServer.model.Admin;
import com.example.bolnicaServer.model.Authority;
import com.example.bolnicaServer.model.User;
import com.example.bolnicaServer.security.TokenUtils;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.ocsp.OCSPObjectIdentifiers;
import org.bouncycastle.asn1.ocsp.OCSPResponse;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.ocsp.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.ContentVerifierProvider;
import org.bouncycastle.operator.bc.BcDigestCalculatorProvider;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.math.BigInteger;
//import java.net.http.HttpHeaders;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OCSPService {

    @Value("${application.keystore.password}")
    private String keyStorePassword;

    @Value("${OCSPReqURL}")
    private String ocspReqURL;

    @Autowired
    private HospitalKeyStore hospitalKeyStore;

    /*@Autowired
    @Qualifier("NoOCSP")
    @Lazy
    private RestTemplate restTemplate;*/
    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private RestTemplateConfiguration restTemplateConfiguration;

    public OCSPReq generateOCSPRequest(X509Certificate[] chain) throws Exception {
        KeyStore keyStore = hospitalKeyStore.setUpStore();
        X509Certificate certificate = chain[0];
        X509Certificate issuerCert = chain[1];

        BcDigestCalculatorProvider util = new BcDigestCalculatorProvider();

        CertificateID id = new CertificateID(util.get(  CertificateID.HASH_SHA1),
                new X509CertificateHolder(issuerCert.getEncoded()), certificate.getSerialNumber());

        OCSPReqBuilder ocspGen = new OCSPReqBuilder();
        ocspGen.addRequest(id);

        BigInteger nonce = BigInteger.valueOf(System.currentTimeMillis());
        Extension ext = new Extension(OCSPObjectIdentifiers.id_pkix_ocsp_nonce, true, new DEROctetString(nonce.toByteArray()));
        ocspGen.setRequestExtensions(new Extensions(new Extension[] { ext }));

        Security.addProvider(new BouncyCastleProvider());
        JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
        builder = builder.setProvider("BC");
        PrivateKey privateKey =  (PrivateKey) keyStore.getKey(hospitalKeyStore.getAlias(), keyStorePassword.toCharArray());
        ContentSigner contentSigner = builder.build(privateKey);

        X500NameBuilder nameBuilder = new X500NameBuilder();
        nameBuilder.addRDN(BCStyle.CN, "PKI");

        nameBuilder.addRDN(BCStyle.UNIQUE_IDENTIFIER, hospitalKeyStore.getAlias());
        ocspGen.setRequestorName(nameBuilder.build());

        OCSPReq request = ocspGen.build(contentSigner, null);
        return request;
    }

    private String generateOCSPToken(){
        String jwt = "";
        User user = new Admin();
        user.setId(0);
        user.setUsername("OCSP");
        Authority authority = new Authority();
        authority.setId(0L);
        authority.setName("ROLE_OCSP");
        List<Authority> authoritys = new ArrayList<>();
        authoritys.add(authority);
        //user.setAuthorities(authoritys);
        jwt = tokenUtils.generateToken(user);
        return "Bearer " + jwt;
    }

    public OCSPResp sendOCSPRequest(OCSPReq ocspReq) throws Exception{
        HttpHeaders headers = new HttpHeaders();

        byte[] ocspBytes = ocspReq.getEncoded();
        HttpEntity<byte[]> entityReq = new HttpEntity<>(ocspBytes, headers);
        ResponseEntity<byte[]> ocspResponse = null;
        //HttpEntity<OCSPReq> entityReq = new HttpEntity<>(ocspReq, headers);
        //System.out.println("Token " + generateOCSPToken());
        try {
            restTemplateConfiguration.setToken(generateOCSPToken());
            RestTemplate restTemplate = restTemplateConfiguration.getRestTemplate();
            ocspResponse = restTemplate.exchange(ocspReqURL, HttpMethod.POST, entityReq, byte[].class);
            //ocspResponse = restTemplate.exchange(ocspReqURL, HttpMethod.POST, entityReq, byte[].class);
        } catch (HttpClientErrorException e) {
            System.out.println("[ERROR] You are not allowed to make CSR request");
            return null;
        }

        OCSPResp ocspResp = new OCSPResp(OCSPResponse.getInstance(ocspResponse.getBody()));
        return ocspResp;
    }

    public boolean processOCSPResponse(OCSPReq ocspReq, OCSPResp ocspResp) throws Exception {
        if(!(ocspResp.getStatus() == OCSPRespBuilder.SUCCESSFUL)){
            throw new Exception("ocspResp now good overall");
        }
        BasicOCSPResp basicResponse = (BasicOCSPResp)ocspResp.getResponseObject();

        X509Certificate rootCA = (X509Certificate) hospitalKeyStore.getTruststore().getCertificate("root");

        ContentVerifierProvider prov = new JcaContentVerifierProviderBuilder().build(rootCA.getPublicKey());
        boolean signatureValid = basicResponse.isSignatureValid(prov);

        if(!signatureValid) {
            throw new Exception("ocspResp signature corupted");
        }

        byte[] reqNonce = ocspReq.getExtension(OCSPObjectIdentifiers.id_pkix_ocsp_nonce).getEncoded();
        byte[] respNonce = basicResponse.getExtension(OCSPObjectIdentifiers.id_pkix_ocsp_nonce).getEncoded();

        if(!Arrays.equals(reqNonce,respNonce)) {
            throw new Exception("nonce extension not same");
        }

        boolean allGood = true;

        for(SingleResp response: basicResponse.getResponses()) {
            if (response.getCertStatus() != CertificateStatus.GOOD) {
                allGood = false;
            }
        }
        return allGood;
    }
}
