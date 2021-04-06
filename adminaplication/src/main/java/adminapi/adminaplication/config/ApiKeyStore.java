package adminapi.adminaplication.config;

import adminapi.adminaplication.model.IssuerData;
import adminapi.adminaplication.model.SubjectData;
import adminapi.adminaplication.service.CertificateService;
import adminapi.adminaplication.service.GeneratorService;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.cert.Certificate;

@Configuration
public class ApiKeyStore {
    @Autowired
    private GeneratorService generatorService;

    @Autowired
    private CertificateService certificateService;

    @Value("${application.keystore.filepath}")
    private String KEYSTORE_FILE_PATH;

    @Value("${application.keystore.password}")
    private String KEYSTORE_PASSWORD;

    @Value("${application.certificate.directory}")
    private String certDirectory;

    @Bean(name = "setUpStore")
    public KeyStore setUpStore() {
        try {
            KeyStore keyStore = KeyStore.getInstance("JKS", "SUN");
            File f = new File(KEYSTORE_FILE_PATH);
            if (f.exists()){
                keyStore.load(new FileInputStream(f), KEYSTORE_PASSWORD.toCharArray());
            }else {
                keyStore.load(null, KEYSTORE_PASSWORD.toCharArray());


                X509Certificate rootCert = generateRoot(keyStore);

                certificateService.writeCertificateToFile(keyStore,
                        "root",
                        "1", certDirectory);

                keyStore.store(new FileOutputStream(KEYSTORE_FILE_PATH), KEYSTORE_PASSWORD.toCharArray());
            }
            return keyStore;
        } catch (IOException | CertificateException | NoSuchAlgorithmException |
                NoSuchProviderException | KeyStoreException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getKEYSTORE_FILE_PATH() {
        return KEYSTORE_FILE_PATH;
    }

    public String getKEYSTORE_PASSWORD() {
        return KEYSTORE_PASSWORD;
    }

    public String getCertDirectory() {
        return certDirectory;
    }

    private X509Certificate generateRoot(KeyStore keyStore) throws Exception {
        KeyPair kp = generatorService.generateKeyPair();

        X500NameBuilder builder = generateName("ROOT");

        IssuerData issuerData =
                generatorService.generateIssuerData(kp.getPrivate(), builder.build());
        SubjectData subjectData = generatorService.generateSubjectData
                (kp.getPublic(), builder.build(), "root", "1");

        subjectData.setSerialNumber("1");
        X509Certificate certificate = certificateService.generateCertificate
                (subjectData, issuerData, "root");

        keyStore.setKeyEntry("1", kp.getPrivate(),
                KEYSTORE_PASSWORD.toCharArray(), new Certificate[]{certificate});
        System.out.println(issuerData);
        System.out.println(subjectData);
        System.out.println(certificate);
        return (X509Certificate) certificate;
    }

    private X500NameBuilder generateName(String CN){
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, CN);
        builder.addRDN(BCStyle.O, "Bolnice-Srbije");
        builder.addRDN(BCStyle.OU, "COVID Bolnica");
        builder.addRDN(BCStyle.L, "Novi Sad");
        builder.addRDN(BCStyle.C, "RS");
        return builder;
    }
}
