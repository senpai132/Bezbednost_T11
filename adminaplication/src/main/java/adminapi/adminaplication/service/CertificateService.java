package adminapi.adminaplication.service;

import adminapi.adminaplication.config.ApiKeyStore;
import adminapi.adminaplication.model.CertificateSignRequest;
import adminapi.adminaplication.model.IssuerData;
import adminapi.adminaplication.model.SubjectData;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.asn1.x509.SubjectKeyIdentifier;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

@Service
public class CertificateService {

    @Autowired
    private ApiKeyStore apiKeyStore;

    public void generateCertificate(CertificateSignRequest certificateSignRequest) {
    }

    public List<X509Certificate> findAll() {
        return null;
    }


    public void removeCertificate(BigInteger serialNumber) {
    }

    public void writeCertificateToFile(KeyStore keyStore, String name, String alias, String certDirectory) throws Exception {
        java.security.cert.Certificate[] chain = keyStore.getCertificateChain(alias);

        StringWriter stringWriter = new StringWriter();
        JcaPEMWriter pm = new JcaPEMWriter(stringWriter);
        for(Certificate certificate : chain) {
            X509Certificate cert = (X509Certificate)certificate;
            pm.writeObject(cert);
        }
        pm.close();


        String fileName = name + ".crt";
        String path = certDirectory + "/" + fileName;

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(stringWriter.toString());
        }
    }

    /*public X509Certificate createCertificate(CertificateSignRequest csr, String templateType) throws Exception {

        PrivateKey issuerKey = readPrivateKey(apiKeyStore.getKEYSTORE_FILE_PATH(),
                apiKeyStore.getKEYSTORE_PASSWORD(), "1",
                apiKeyStore.getKEYSTORE_PASSWORD());
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, "ROOT");
        builder.addRDN(BCStyle.O, "MZ-Srbija");
        builder.addRDN(BCStyle.OU, "Klinicki centar");
        builder.addRDN(BCStyle.L, "Novi Sad");
        builder.addRDN(BCStyle.C, "RS");
        IssuerData issuerData = generateIssuerData(issuerKey, builder.build());
        X500NameBuilder subjectName = generateName(csr);
        SubjectData subjectData = generateSubjectData(
                certificateSigningRequestService.getPublicKeyFromCSR(csrId),
                subjectName.build(), TemplateTypes.LEAF_HOSPITAL, String.valueOf(csr.getId()));

        String keyStorePath = "pki/keystore/keyStore_" + csr.getId() + ".jks";
        char[] keyStorePass = csr.getId().toString().toCharArray();

        X509Certificate certificate = generateCertificate(subjectData, issuerData, "leaf");

        try {
            KeyStore keyStore = KeyStore.getInstance("JKS", "SUN");
            File f = new File(keyStorePath);
            if (f.exists()){
                keyStore.load(new FileInputStream(f), keyStorePass);
            }else {
                keyStore.load(null, keyStorePass);

                keyStore.setKeyEntry("1", issuerKey,
                        keyStorePass, new Certificate[]{certificate});

                keyStore.store(new FileOutputStream(keyStorePath), keyStorePass);

                return certificate;
            }
        } catch (IOException | CertificateException | NoSuchAlgorithmException |
                NoSuchProviderException | KeyStoreException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public X509Certificate generateCertificate(SubjectData subjectData,
                                               IssuerData issuerData,
                                               String templateTypes) throws Exception{
        try {
            JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
            builder = builder.setProvider("BC");

            ContentSigner contentSigner = builder.build(issuerData.getPrivateKey());

            X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(issuerData.getX500name(),
                    new BigInteger(subjectData.getSerialNumber()),
                    subjectData.getStartDate(),
                    subjectData.getEndDate(),
                    subjectData.getX500name(),
                    subjectData.getPublicKey());

            if(templateTypes == "root") {
                certGen.addExtension(Extension.keyUsage, false,
                        new KeyUsage(KeyUsage.digitalSignature | KeyUsage.keyCertSign | KeyUsage.cRLSign));

                certGen.addExtension(Extension.basicConstraints, false, new BasicConstraints(true));


                byte[] subjectKeyIdentifier = new JcaX509ExtensionUtils()
                        .createSubjectKeyIdentifier(subjectData.getPublicKey()).getKeyIdentifier();

                certGen.addExtension(Extension.subjectKeyIdentifier, false,
                        new SubjectKeyIdentifier(subjectKeyIdentifier));
            } else {
                certGen.addExtension(Extension.keyUsage, false,
                        new KeyUsage(KeyUsage.digitalSignature | KeyUsage.keyEncipherment));
                certGen.addExtension(Extension.basicConstraints, false, new BasicConstraints(false));
            }

            X509CertificateHolder certHolder = certGen.build(contentSigner);
            JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
            certConverter = certConverter.setProvider("BC");

            return certConverter.getCertificate(certHolder);
        } catch (IllegalArgumentException | IllegalStateException | OperatorCreationException |
                CertificateException | NoSuchAlgorithmException | CertIOException e) {
            e.printStackTrace();
        }
        return null;
    }*/
}
