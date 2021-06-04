package com.example.bolnicaServer.config;

import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

@Configuration
public class HospitalKeyStore {

    @Value("${application.keystore.filepath}")
    private String KEYSTORE_FILE_PATH;

    @Value("${application.keystore.password}")
    private String KEYSTORE_PASSWORD;

    @Value("${application.certificate.directory}")
    private String certDirectory;

    @Value("${application.my.certificate}")
    private String cert;

    @Value("${application.my.key}")
    private String myKey;

    @Value("${application.alias}")
    private String alias;

    @Value("${application.truststore.password}")
    private String TRUSTSTORE_PASSWORD;

    @Value("${application.truststore.filepath}")
    private String TRUSTSTORE_FILE_PATH;

    @Bean(name = "setUpStore")
    public KeyStore setUpStore() {
        try {
            KeyStore keyStore = KeyStore.getInstance("JKS", "SUN");
            File f = new File(KEYSTORE_FILE_PATH);
            if (f.exists()){
                keyStore.load(new FileInputStream(f), KEYSTORE_PASSWORD.toCharArray());
                /*PrivateKey k = (PrivateKey) keyStore.getKey("1", KEYSTORE_PASSWORD.toCharArray());
                PrivateKey k1 = loadKey();*/

            }else {
                keyStore.load(null, KEYSTORE_PASSWORD.toCharArray());
                CertificateFactory fac = CertificateFactory.getInstance("X509");
                FileInputStream is = new FileInputStream(certDirectory + "/" + cert);
                X509Certificate cert = (X509Certificate) fac.generateCertificate(is);

                is = new FileInputStream(certDirectory + "/root.crt");
                X509Certificate root = (X509Certificate) fac.generateCertificate(is);
                keyStore.setKeyEntry(alias, loadKey(),
                        KEYSTORE_PASSWORD.toCharArray(), new Certificate[]{cert, root});

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

    @Bean(name = "trustStore")
    public KeyStore getTruststore(){
        try {
            KeyStore keyStore = KeyStore.getInstance("JKS", "SUN");

            File f = new File(TRUSTSTORE_FILE_PATH);

            if (f.exists()) {
                keyStore.load(new FileInputStream(f), TRUSTSTORE_PASSWORD.toCharArray());
            } else {
                keyStore.load(null, TRUSTSTORE_PASSWORD.toCharArray());

                CertificateFactory fac = CertificateFactory.getInstance("X509");
                FileInputStream is;

                is = new FileInputStream(certDirectory + "/root.crt");
                X509Certificate root = (X509Certificate) fac.generateCertificate(is);

                keyStore.setCertificateEntry("root", root);
                keyStore.store(new FileOutputStream(TRUSTSTORE_FILE_PATH), TRUSTSTORE_PASSWORD.toCharArray());
            }
            return keyStore;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }

    public PrivateKey loadKey()
            throws IOException, NoSuchAlgorithmException,
            InvalidKeySpecException, InvalidKeySpecException {


        FileInputStream fis;
        // Read Private Key.
        File filePrivateKey = new File(certDirectory + "/" + myKey);
        fis = new FileInputStream(certDirectory + "/" + myKey);
        byte[] encodedPrivateKey = new byte[(int) filePrivateKey.length()];
        fis.read(encodedPrivateKey);
        fis.close();


        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(
                encodedPrivateKey);
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

        return privateKey;
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


    public String getCert() {
        return cert;
    }

    public String getMyKey() {
        return myKey;
    }


    public String getAlias() {
        return alias;
    }


    public String getTRUSTSTORE_PASSWORD() {
        return TRUSTSTORE_PASSWORD;
    }


    public String getTRUSTSTORE_FILE_PATH() {
        return TRUSTSTORE_FILE_PATH;
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
