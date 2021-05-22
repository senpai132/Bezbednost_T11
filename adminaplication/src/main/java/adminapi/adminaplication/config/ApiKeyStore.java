package adminapi.adminaplication.config;

import adminapi.adminaplication.model.IssuerData;
import adminapi.adminaplication.model.SubjectData;
import adminapi.adminaplication.service.CertificateService;
import adminapi.adminaplication.service.GeneratorService;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.cert.Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

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

    @Value("${application.truststore.password}")
    private String TRUSTSTORE_PASSWORD;

    @Value("${application.truststore.filepath}")
    private String TRUSTSTORE_FILE_PATH;

    @Value("${application.certificate.directory}")
    private String certDirectory;

    private KeyStore apiTrustStore;

    @Bean(name = "setUpStore")
    public KeyStore setUpStore() {
        try {
            KeyStore keyStore = KeyStore.getInstance("JKS", "SUN");
            KeyStore trustStore = KeyStore.getInstance("JKS", "SUN");
            File f = new File(KEYSTORE_FILE_PATH);
            if (f.exists()){
                keyStore.load(new FileInputStream(f), KEYSTORE_PASSWORD.toCharArray());
                /*PrivateKey k = (PrivateKey) keyStore.getKey("1", KEYSTORE_PASSWORD.toCharArray());
                PrivateKey k1 = loadKey();*/
                File f1 = new File(KEYSTORE_FILE_PATH);
                trustStore.load(new FileInputStream(f1), TRUSTSTORE_PASSWORD.toCharArray());
                apiTrustStore = trustStore;
            }else {
                keyStore.load(null, KEYSTORE_PASSWORD.toCharArray());
                trustStore.load(null, TRUSTSTORE_PASSWORD.toCharArray());

                X509Certificate rootCert = generateRoot(keyStore);

                certificateService.writeCertificateToFile(keyStore,
                        "root",
                        "1", certDirectory);

                trustStore.setCertificateEntry("1", rootCert);

                X509Certificate pki_cert = generatePKICert(keyStore, rootCert);
                certificateService.writeCertificateToFile(keyStore,"pki", "2", certDirectory);
                apiTrustStore = trustStore;
                trustStore.store(new FileOutputStream(TRUSTSTORE_FILE_PATH), TRUSTSTORE_PASSWORD.toCharArray());
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

    public KeyStore getApiTrustStore() {
        return apiTrustStore;
    }

    private X509Certificate generatePKICert(KeyStore keyStore, X509Certificate root) throws Exception{
        KeyPair kp = generatorService.generateKeyPair();

        X500NameBuilder builder = generateName("PKI");
        X500NameBuilder issuerBuilder = generateName("ROOT");

        PrivateKey issuerPrivateKey = (PrivateKey) keyStore.getKey("1", KEYSTORE_PASSWORD.toCharArray());

        IssuerData issuerData =
                generatorService.generateIssuerData(issuerPrivateKey, issuerBuilder.build());


        SubjectData subjectData = generatorService.generateSubjectData
                (kp.getPublic(), builder.build(), "leaf", "2");


        subjectData.setSerialNumber("2");
        Certificate certificate = certificateService.generateCertificate
                (subjectData, issuerData, "leaf");

        keyStore.setKeyEntry("2", kp.getPrivate(),
                KEYSTORE_PASSWORD.toCharArray(), new Certificate[]{certificate, root});

        this.savePrivateKey(kp, "/private.key");
        RSAPrivateKey priv = (RSAPrivateKey) kp.getPrivate();

        writePemFile(priv, "RSA PRIVATE KEY", "/pki_key.key");

        return (X509Certificate) certificate;
    }

    private void writePemFile(Key key, String description, String filename)
            throws FileNotFoundException, IOException {
        File file = new File(certDirectory + filename);
        JcaPEMWriter writer = new JcaPEMWriter(new PrintWriter(file));
        writer.writeObject(key);
        writer.close();

    }


    public PrivateKey loadKey()
            throws IOException, NoSuchAlgorithmException,
            InvalidKeySpecException, InvalidKeySpecException {

        FileInputStream fis;
        // Read Private Key.
        File filePrivateKey = new File(certDirectory + "/private.key");
        fis = new FileInputStream(certDirectory + "/private.key");
        byte[] encodedPrivateKey = new byte[(int) filePrivateKey.length()];
        fis.read(encodedPrivateKey);
        fis.close();

        // Generate KeyPair.
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(
                encodedPrivateKey);
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

        return privateKey;
    }


    public void savePrivateKey(KeyPair keyPair, String name) throws IOException {
        PrivateKey privateKey = keyPair.getPrivate();
        //PublicKey publicKey = keyPair.getPublic();
        FileOutputStream fos;


        // Store Private Key.
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
                privateKey.getEncoded());
        fos = new FileOutputStream(certDirectory + name);
        fos.write(pkcs8EncodedKeySpec.getEncoded());
        //fos.write(privateKey.getEncoded());
        fos.close();
    }

    public String getTRUSTSTORE_PASSWORD() {
        return TRUSTSTORE_PASSWORD;
    }

    public String getTRUSTSTORE_FILE_PATH() {
        return TRUSTSTORE_FILE_PATH;
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
