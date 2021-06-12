package adminapi.adminaplication.service;

import adminapi.adminaplication.config.ApiKeyStore;
import adminapi.adminaplication.repository.RevokedCertificateRepository;
import org.bouncycastle.asn1.ocsp.OCSPObjectIdentifiers;
import org.bouncycastle.asn1.x509.CRLReason;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.ocsp.*;
import org.bouncycastle.operator.ContentVerifierProvider;
import org.bouncycastle.operator.bc.BcDigestCalculatorProvider;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Date;

@Service
public class OCSPService {

    @Autowired
    private CertificateService certificateService;

    @Autowired
    private ApiKeyStore apiKeyStore;

    @Autowired
    private RevokedCertificateRepository revokedCertificateRepository;

    public OCSPResp generateOCSPResponse(OCSPReq request)
            throws Exception {

        String requestorCerticateSerialNumber = null;
        String requestorName = request.getRequestorName().toString();
        for(String reqParam: requestorName.split(",")) {
            if(reqParam.contains("UniqueIdentifier")) {
                requestorCerticateSerialNumber = reqParam.split("=")[1];
            }
        }

        X509Certificate requestMaker = (X509Certificate)
                apiKeyStore.setUpStore().getCertificate(requestorCerticateSerialNumber);
        if(requestMaker == null){
            throw  new Exception("request certificate not found");
        }



        ContentVerifierProvider prov = new JcaContentVerifierProviderBuilder().build(requestMaker.getPublicKey());
        if (!request.isSignatureValid(prov)) {
            throw  new Exception("bed request signature ");
        }


        X509Certificate rootCaCert = (X509Certificate) apiKeyStore.setUpStore().getCertificate("1");
        PrivateKey rootCAPrivateKey =  (PrivateKey) apiKeyStore.setUpStore().getKey("1", apiKeyStore.getKEYSTORE_PASSWORD().toCharArray());

        PrivateKey responderKey = rootCAPrivateKey;
        PublicKey pubKey = rootCaCert.getPublicKey();


        BcDigestCalculatorProvider util = new BcDigestCalculatorProvider();

        BasicOCSPRespBuilder respBuilder = new BasicOCSPRespBuilder(
                SubjectPublicKeyInfo.getInstance(pubKey.getEncoded()),
                util.get(CertificateID.HASH_SHA1));


        Extensions extensions = null;
        Extension nonce_ext = request.getExtension(OCSPObjectIdentifiers.id_pkix_ocsp_nonce);
        if (nonce_ext != null) {
            extensions = new Extensions(new Extension[]{ nonce_ext});
        }
        respBuilder.setResponseExtensions(extensions);

        Req[] requests = request.getRequestList();
        /*if (isRevoked(requestorCerticateSerialNumber)) {
            //respBuilder.addResponse(req.getCertID(), new RevokedStatus(new Date(), CRLReason.superseded));
            throw new Exception("request maker certificate is revoked");
        }*/
        if (isRevoked(requestorCerticateSerialNumber)) {
            //respBuilder.addResponse(req.getCertID(), new RevokedStatus(new Date(), CRLReason.superseded));
            respBuilder.addResponse(requests[0].getCertID(), new UnknownStatus());
            BasicOCSPResp resp = respBuilder.build(
                    new JcaContentSignerBuilder("SHA256withRSA").build(responderKey),
                    null, new Date());

            OCSPRespBuilder builder = new OCSPRespBuilder();
            return builder.build(OCSPRespBuilder.SUCCESSFUL, resp);
        }

        for (Req req : requests) {
            BigInteger sn = req.getCertID().getSerialNumber();
            X509Certificate cert = (X509Certificate) apiKeyStore.setUpStore().getCertificate(sn.toString());

            if (cert == null) {
                respBuilder.addResponse(req.getCertID(), new UnknownStatus());
            } else {
                try {
                    cert.checkValidity();
                    boolean revoked = isRevoked(sn.toString());
                    if (revoked) {
                        respBuilder.addResponse(req.getCertID(), new RevokedStatus(new Date(), CRLReason.superseded));
                    }
                    else{
                        respBuilder.addResponse(req.getCertID(), CertificateStatus.GOOD);
                    }

                }
                catch (Exception e) {
                    respBuilder.addResponse(req.getCertID(), new RevokedStatus(new Date(), CRLReason.superseded));
                }
            }
        }

        BasicOCSPResp resp = respBuilder.build(
                new JcaContentSignerBuilder("SHA256withRSA").build(responderKey),
                null, new Date());

        OCSPRespBuilder builder = new OCSPRespBuilder();
        return builder.build(OCSPRespBuilder.SUCCESSFUL, resp);
    }

    public boolean isRevoked(String serialNumber) {
        return revokedCertificateRepository.findBySerialNumber(serialNumber).isPresent();
    }
}
