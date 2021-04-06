package adminapi.adminaplication.service;

import adminapi.adminaplication.model.IssuerData;
import adminapi.adminaplication.model.SubjectData;
import org.bouncycastle.asn1.x500.X500Name;
import org.springframework.stereotype.Service;

import java.security.*;
import java.security.cert.X509Certificate;

@Service
public class GeneratorService {
    public KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(2048, random);
            return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }


    public IssuerData generateIssuerData(PrivateKey aPrivate, X500Name build) {
        return null;
    }

    public SubjectData generateSubjectData(PublicKey aPublic, X500Name build, String root, String i) {
        return null;
    }

    public X509Certificate generateCertificate(SubjectData subjectData, IssuerData issuerData, String root) {
        return null;
    }
}
