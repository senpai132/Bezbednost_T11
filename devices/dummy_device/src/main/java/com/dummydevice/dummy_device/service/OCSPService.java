package com.dummydevice.dummy_device.service;

import com.dummydevice.dummy_device.config.DeviceKeyStore;
import com.dummydevice.dummy_device.config.RestTemplateConfiguration;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.Arrays;

@Service
public class OCSPService {

    @Value("${application.keystore.password}")
    private String keyStorePassword;

    @Value("${OCSPReqURL}")
    private String ocspReqURL;

    @Autowired
    private DeviceKeyStore deviceKeyStore;

    /*@Autowired
    @Qualifier("NoOCSP")
    @Lazy
    private RestTemplate restTemplate;*/

    private RestTemplateConfiguration restTemplateConfiguration;

    public OCSPReq generateOCSPRequest(X509Certificate[] chain) throws Exception {
        KeyStore keyStore = deviceKeyStore.setUpStore();
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
        PrivateKey privateKey =  (PrivateKey) keyStore.getKey(deviceKeyStore.getAlias(), keyStorePassword.toCharArray());
        ContentSigner contentSigner = builder.build(privateKey);

        X500NameBuilder nameBuilder = new X500NameBuilder();
        nameBuilder.addRDN(BCStyle.CN, "novi_komon1"); // nije bitno posto samo proveravamo alias
        nameBuilder.addRDN(BCStyle.UNIQUE_IDENTIFIER, deviceKeyStore.getAlias());
        ocspGen.setRequestorName(nameBuilder.build());

        OCSPReq request = ocspGen.build(contentSigner, null);
        return request;
    }

    public OCSPResp sendOCSPRequest(OCSPReq ocspReq) throws Exception{
        HttpHeaders headers = new HttpHeaders();

        byte[] ocspBytes = ocspReq.getEncoded();
        HttpEntity<byte[]> entityReq = new HttpEntity<>(ocspBytes, headers);
        ResponseEntity<byte[]> ocspResponse = null;

        try {
            RestTemplate restTemplate = restTemplateConfiguration.getRestTemplate();
            ocspResponse = restTemplate.exchange(ocspReqURL, HttpMethod.POST, entityReq, byte[].class);
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


        X509Certificate rootCA = (X509Certificate) deviceKeyStore.getTruststore().getCertificate("root");

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
