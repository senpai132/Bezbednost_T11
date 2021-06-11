package com.dummydevice.dummy_device.config;

import com.dummydevice.dummy_device.service.OCSPService;
import org.apache.http.ssl.TrustStrategy;


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
import org.springframework.stereotype.Component;

import java.security.KeyStore;
import java.security.cert.*;
import java.util.Arrays;

@Component
public class OCSPTrustStrategy implements TrustStrategy {

    private KeyStore trustStore;

    private OCSPService ocspService;

    //private boolean doOCSP;

    public OCSPTrustStrategy(){}

    public  OCSPTrustStrategy(KeyStore keyStore, OCSPService ocspService) {
        this.trustStore = keyStore;
        this.ocspService = ocspService;
        //this.doOCSP = doOCSP;
    }

    @Override
    public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        boolean retVal = true;

        // samo da isprinta lanac
        for(X509Certificate c : x509Certificates) {
            System.out.println(c.getSerialNumber());
        }

        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            CertPath certPath = cf.generateCertPath(Arrays.asList(x509Certificates));
            CertPathValidator validator = CertPathValidator.getInstance("PKIX");
            PKIXParameters params = new PKIXParameters(this.trustStore);
            params.setRevocationEnabled(false);
            PKIXCertPathValidatorResult result = (PKIXCertPathValidatorResult) validator.validate(certPath, params);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            retVal = false;
        }finally {
            X509Certificate cert = x509Certificates[0];
            String name = cert.getSubjectX500Principal().getName();
            System.out.println(name);
            /*if (name.equals(Constants.PKI_COMMUNICATION_CERT_NAME))
            {
                return true;
            }*/
            //if(!true){return true;}
            try {
                OCSPReq request = ocspService.generateOCSPRequest(x509Certificates);
                OCSPResp response = ocspService.sendOCSPRequest(request);
                boolean val = ocspService.processOCSPResponse(request,response);
                retVal = val;
            }catch (Exception e) {
                e.printStackTrace();
                retVal = false;
            }
            finally {
                return retVal;
            }

        }
    }
}
