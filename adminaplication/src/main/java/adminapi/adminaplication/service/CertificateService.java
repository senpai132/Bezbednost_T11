package adminapi.adminaplication.service;

import adminapi.adminaplication.config.ApiKeyStore;
import adminapi.adminaplication.model.CertificateSignRequest;
import adminapi.adminaplication.model.IssuerData;
import adminapi.adminaplication.model.RevokedCertificate;
import adminapi.adminaplication.model.SubjectData;
import adminapi.adminaplication.repository.RevokedCertificateRepository;
import org.bouncycastle.asn1.x500.X500Name;
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
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

@Service
public class CertificateService {

    @Autowired
    private ApiKeyStore apiKeyStore;

    @Autowired
    private GeneratorService generatorService;

    @Autowired
    private RevokedCertificateRepository revokedCertificateRepository;

    @Autowired
    private CertificateSignRequestService certificateSignRequestService;

    public List<X509Certificate> findAllActive() {

        KeyStore ks = apiKeyStore.setUpStore();
        try {
            List<X509Certificate> certificates = new ArrayList<>();
            Enumeration<String> aliases = ks.aliases();
            while (aliases.hasMoreElements()) {
                String alias = aliases.nextElement();
                String serialNumber = alias.replaceAll("^0", "");
                if(!revokedCertificateRepository.findBySerialNumber(serialNumber).isPresent())
                    certificates.add(readCertificate(alias));
            }
            return certificates;
        } catch (KeyStoreException e) {
            System.out.println(e);
        }
        return null;
    }

    public List<RevokedCertificate> findAllRemoved() {

        KeyStore ks = apiKeyStore.setUpStore();
        try {
            List<RevokedCertificate> certificates = new ArrayList<>();
            Enumeration<String> aliases = ks.aliases();
            while (aliases.hasMoreElements()) {
                String alias = aliases.nextElement();
                String serialNumber = alias.replaceAll("^0", "");
                RevokedCertificate revokedCertificate = revokedCertificateRepository.findBySerialNumber(serialNumber).orElse(null);
                if(revokedCertificate != null)
                    certificates.add(revokedCertificate);
            }
            return certificates;
        } catch (KeyStoreException e) {
            System.out.println(e);
        }
        return null;
    }

    public X509Certificate readCertificate(String alias) {
        KeyStore ks = apiKeyStore.setUpStore();
        Certificate cert = null;
        try {
            if (ks.isKeyEntry(alias)) {
                cert = ks.getCertificate(alias);
            }
            if (ks.isCertificateEntry(alias)) {
                cert = ks.getCertificate(alias);
            }
        } catch (KeyStoreException e) {
            throw new ResourceNotFoundException("Certificate doesn't exist");
        }
        return (X509Certificate) cert;
    }

    public void revokeCertificate(String serialNumber, String reason) {
        Date revocationDate = new Date();
        RevokedCertificate revokedCertificate = new RevokedCertificate(serialNumber, revocationDate, reason);

        revokedCertificateRepository.save(revokedCertificate);
    }

    /*public void revokeCertificate(RevokedCertificate revokedCertificate) {
        Date revocationDate = new Date();
        //RevokedCertificate revokedCertificate = new RevokedCertificate(serialNumber.toString(), revocationDate, reason);
        revokedCertificate.setRevocationDate(revocationDate);
        revokedCertificateRepository.save(revokedCertificate);
    }*/

    public void writeCertificateToFile(KeyStore keyStore, String name, String alias, String certDirectory) throws Exception {
        java.security.cert.Certificate[] chain = keyStore.getCertificateChain(alias);
        //Enumeration<String> enumeration = keyStore.aliases();
        //System.out.println(chain.length);
        StringWriter stringWriter = new StringWriter();
        JcaPEMWriter pm = new JcaPEMWriter(stringWriter);
        if(chain != null){
            for(Certificate certificate : chain) {
                X509Certificate cert = (X509Certificate)certificate;
                System.out.println(cert);
                pm.writeObject(cert);
            }
        }
        pm.close();


        String fileName = name + ".crt";
        String path = certDirectory + "/" + fileName;

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(stringWriter.toString());
        }
    }

    public IssuerData loadIssuer(String alias) throws KeyStoreException, CertificateException, NoSuchAlgorithmException,
            UnrecoverableKeyException {
        KeyStore keyStore = apiKeyStore.setUpStore();
        //Iscitava se sertifikat koji ima dati alias
        Certificate cert = keyStore.getCertificate(alias);
        //Iscitava se privatni kljuc vezan za javni kljuc koji se nalazi na sertifikatu sa datim aliasom
        PrivateKey privKey = (PrivateKey) keyStore.getKey(alias, apiKeyStore.getKEYSTORE_PASSWORD().toString().toCharArray());

        X500Name issuerName = new JcaX509CertificateHolder((X509Certificate) cert).getSubject();
        return new IssuerData(privKey, issuerName);
    }

    public X509Certificate createCertificate(CertificateSignRequest csr, String templateType) throws Exception {

        PrivateKey issuerKey = readPrivateKey(apiKeyStore.getKEYSTORE_FILE_PATH(),
                apiKeyStore.getKEYSTORE_PASSWORD(), "1",
                apiKeyStore.getKEYSTORE_PASSWORD());

        IssuerData issuerData = loadIssuer("1");
        X500NameBuilder subjectName = generatorService.generateName(csr);
        SubjectData subjectData = generatorService.generateSubjectData(
                certificateSignRequestService.getPublicKeyFromCSR(csr.getId()),
                subjectName.build(), "leaf", String.valueOf(csr.getSerialNumber()));

        String keyStorePath = "src\\main\\resources\\keystore\\apiKeyStore.jks";
        char[] keyStorePass = apiKeyStore.getKEYSTORE_PASSWORD().toString().toCharArray();

        X509Certificate certificate = generateCertificate(subjectData, issuerData, "leaf");

        KeyStore keyStore = apiKeyStore.setUpStore();

        Certificate[] certificates = this.createChain("1", certificate);

        this.writeCertificateToKeyStore(csr.getSerialNumber(), certificates,
                issuerData.getPrivateKey());
        this.writeCertificateToFile(keyStore, "leaf_" + csr.getCommonName(), csr.getSerialNumber(), apiKeyStore.getCertDirectory());
        return certificate;
    }


    private Certificate[] createChain(String issuerAlias,Certificate certificate){
        Certificate[] certificatesChainIssuer = this.getCertificateChainByAlias(issuerAlias);

        int size = certificatesChainIssuer.length;
        Certificate[] certificatesChain = new Certificate[size + 1];
        certificatesChain[0] = certificate;


        System.arraycopy(certificatesChainIssuer, 0, certificatesChain, 1, size);
        return certificatesChain;
    }

    private Certificate[] getCertificateChainByAlias(String alias){
        KeyStore ks = apiKeyStore.setUpStore();
        Certificate[] certificates = null;
        try {
            if (ks.isKeyEntry(alias)) {
                certificates = ks.getCertificateChain(alias);
            }
        } catch (KeyStoreException e) {
            throw new ResourceNotFoundException("Certificate doesn't exist");
        }
        return certificates;
    }

    private void  writeCertificateToKeyStore(String alias, Certificate[] certificates, PrivateKey pk) throws Exception{
        apiKeyStore.setUpStore().setKeyEntry(alias, pk,
                apiKeyStore.getKEYSTORE_PASSWORD().toString().toCharArray(), certificates);

        apiKeyStore.setUpStore().store(new FileOutputStream(apiKeyStore.getKEYSTORE_FILE_PATH()),
                apiKeyStore.getKEYSTORE_PASSWORD().toString().toCharArray());
    }


    public X509Certificate generateCertificate(SubjectData subjectData,
                                               IssuerData issuerData,
                                               String templateTypes) throws Exception{
        try {
            //System.out.println("USO1");
            JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");

            //System.out.println("USO@");
            builder = builder.setProvider("BC");
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            ContentSigner contentSigner = builder.build(issuerData.getPrivateKey());
            //System.out.println("Prosao");
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
    }

    public PrivateKey readPrivateKey(String keyStoreFile, String keyStorePass, String alias, String pass) {
        try {
            KeyStore ks = KeyStore.getInstance("JKS", "SUN");
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
            ks.load(in, keyStorePass.toCharArray());

            if (ks.isKeyEntry(alias)) {
                PrivateKey pk = (PrivateKey) ks.getKey(alias, pass.toCharArray());
                return pk;
            }
        } catch (KeyStoreException | NoSuchAlgorithmException | NoSuchProviderException | CertificateException
                | IOException | UnrecoverableKeyException e) {
            e.printStackTrace();
        }
        return null;
    }
}
