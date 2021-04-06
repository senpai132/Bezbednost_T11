package adminapi.adminaplication.config;

import adminapi.adminaplication.service.CertificateService;
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
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;

@Configuration
public class ApiKeyStore {
    /*@Autowired
    private DigitalCertificateService digitalCertificateService;*/

    @Autowired
    private CertificateService certificateService;

    @Value("${application.keystore.filepath}")
    private String KEYSTORE_FILE_PATH;

    @Value("${application.keystore.password}")
    private String KEYSTORE_PASSWORD;

    @Value("${application.certificate.directory}")
    private String certDirectory;

    @Bean(name = "setUpPKI")
    public KeyStore setUpPKI() {
        try {
            KeyStore keyStore = KeyStore.getInstance("JKS", "SUN");
            File f = new File(KEYSTORE_FILE_PATH);
            if (f.exists()){
                keyStore.load(new FileInputStream(f), KEYSTORE_PASSWORD.toCharArray());
            }else {
                keyStore.load(null, KEYSTORE_PASSWORD.toCharArray());

                /*DigitalCertificate root_cert = generateRoot(keyStore);
                digitalCertificateService.writeCertificateToFile(keyStore,
                        TemplateTypes.ROOT.toString().toLowerCase(),
                        CryptConstants.ROOT_ALIAS, certDirectory);

                digitalCertificateService.save(root_cert);*/

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

    /*private DigitalCertificate generateRoot(KeyStore keyStore) throws KeyStoreException {
        KeyPair kp = digitalCertificateService.generateKeyPair();

        X500NameBuilder builder = generateName("ROOT");

        IssuerData issuerData =
                digitalCertificateService.generateIssuerData(kp.getPrivate(), builder.build());
        SubjectData subjectData = digitalCertificateService.generateSubjectData
                (kp.getPublic(), builder.build(), TemplateTypes.ROOT, CryptConstants.ROOT_ALIAS);

        subjectData.setSerialNumber(CryptConstants.ROOT_ALIAS);
        X509Certificate certificate = digitalCertificateService.generateCertificate
                (subjectData, issuerData, TemplateTypes.ROOT);

        keyStore.setKeyEntry(CryptConstants.ROOT_ALIAS, kp.getPrivate(),
                KEYSTORE_PASSWORD.toCharArray(), new Certificate[]{certificate});

        DigitalCertificate digitalCertificate = new DigitalCertificate(
                new BigInteger(subjectData.getSerialNumber()));
        digitalCertificate.setStartDate(new java.sql.Timestamp(subjectData.getStartDate().getTime()));
        digitalCertificate.setEndDate(new java.sql.Timestamp(subjectData.getEndDate().getTime()));
        digitalCertificate.setCommonName(TemplateTypes.ROOT.toString());
        digitalCertificate.setCertKeyStorePath(certDirectory + "/root.crt");

        return digitalCertificate;
    }*/

    private X500NameBuilder generateName(String CN){
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, CN);
        builder.addRDN(BCStyle.O, "MZ-Srbija");
        builder.addRDN(BCStyle.OU, "Klinicki centar");
        builder.addRDN(BCStyle.L, "Novi Sad");
        builder.addRDN(BCStyle.C, "RS");
        return builder;
    }
}
