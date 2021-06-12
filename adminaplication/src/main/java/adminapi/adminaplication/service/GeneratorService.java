package adminapi.adminaplication.service;

import adminapi.adminaplication.model.CertificateSignRequest;
import adminapi.adminaplication.model.IssuerData;
import adminapi.adminaplication.model.SubjectData;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;

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


    public IssuerData generateIssuerData(PrivateKey issuerKey, X500Name name) {
        return new IssuerData(issuerKey, name);
    }

    public SubjectData generateSubjectData(PublicKey publicKey, X500Name name, String templateType, String serialNum) {
        Date endDate;

        if (templateType.equals("root")) {
            endDate = generateDate(24);
        } else if (templateType.equals("leaf")) {
            endDate = generateDate(12);
        }
        else {
            System.out.println("Usao kao device");
            endDate = generateDate(6);
        }

        GeneralName[] subjectAltNames = new GeneralName[]{
                new GeneralName(GeneralName.dNSName, "localhost")
        };

        /*Extension[] extensions = new Extension[] {
                Extension.create(Extension.subjectAlternativeName, true, new GeneralNames(subjectAltNames))
        };*/

        return new SubjectData(
                publicKey,
                name,
                serialNum,
                new Date(),
                endDate,
                new GeneralNames(subjectAltNames)
        );
    }

    private Date generateDate(int periodInMonths){
        Calendar calendarLater = Calendar.getInstance();
        calendarLater.setTime(new Date());

        calendarLater.add(Calendar.MONTH, periodInMonths);

        return calendarLater.getTime();
    }

    public X500NameBuilder generateName(CertificateSignRequest csr) {
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, csr.getCommonName());
        builder.addRDN(BCStyle.O, csr.getOrganization());
        builder.addRDN(BCStyle.OU, csr.getOrganizationUnit());
        builder.addRDN(BCStyle.C, csr.getCountry());
        builder.addRDN(BCStyle.EmailAddress, csr.getEmail());
        return builder;
    }
}
