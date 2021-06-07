package adminapi.adminaplication.service;

import adminapi.adminaplication.model.CertificateSignRequest;
import adminapi.adminaplication.repository.CertificateSignRequestRepository;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Security;
import java.util.List;

@Service
public class CertificateSignRequestService {

    @Autowired
    private CertificateSignRequestRepository certificateSignRequestRepository;

    @Autowired
    private JavaMailSender mailSender;

    public void confirmCertificateRequest(Long id){
        CertificateSignRequest certificateSignRequest = certificateSignRequestRepository.findById(id);
        if(certificateSignRequest != null){
            certificateSignRequest.setStatus(0);
            certificateSignRequestRepository.save(certificateSignRequest);
        }
    }

    public List<CertificateSignRequest> findAllPendingRequest(){
        List<CertificateSignRequest> pendingRequest = certificateSignRequestRepository.findByStatus(0);
        //List<CertificateSignRequest> renewalRequest = certificateSignRequestRepository.findByStatus(1);

        //pendingRequest.addAll(renewalRequest);
        return pendingRequest;
    }

    private String getField(X500Name x500Name, ASN1ObjectIdentifier field) throws Exception {
        RDN[] rdn = x500Name.getRDNs(field);

        if (rdn.length == 0)
            return "";
        else if (rdn.length != 1)
            throw new Exception(
                    "CertificateSigningRequestService: RDN expected only one param. (given: " + rdn.length + ")");

        return IETFUtils.valueToString(rdn[0].getFirst().getValue());
    }

    public CertificateSignRequest createRequest(byte[] req) throws Exception{

        PKCS10CertificationRequest csr = this.extractCertificationRequest(req);

        X500Name x500Name = csr.getSubject();

        String commonName = getField(x500Name, BCStyle.CN);
        String lastName = getField(x500Name, BCStyle.SURNAME);
        String firstName = getField(x500Name, BCStyle.GIVENNAME);
        String organization = getField(x500Name, BCStyle.O);
        String organizationUnit = getField(x500Name, BCStyle.OU);
        String country = getField(x500Name, BCStyle.C);
        String email = getField(x500Name, BCStyle.E);
        String serialNumber = getField(x500Name, BCStyle.SERIALNUMBER);
        String locality = getField(x500Name, BCStyle.L);
        
        CertificateSignRequest csr_pom = new CertificateSignRequest(
                commonName,
                lastName,
                firstName,
                organization,
                organizationUnit,
                country,
                locality,
                email,
                1,
                serialNumber,
                req
        );

        CertificateSignRequest newRequest = certificateSignRequestRepository.save(
                csr_pom);
        sendConfirmationMail(newRequest);

        return newRequest;

    }

    private void sendConfirmationMail(CertificateSignRequest req){
        SimpleMailMessage message = new SimpleMailMessage();
        // message.setFrom("noreply@baeldung.com");
        message.setTo("pombolnica@gmail.com"); //treba da ide req.getEmail()
        message.setSubject("Confirm CSR");
        String messageTemplate =
                "<a target=\"_blank\" href=\"https://localhost:8080/api/certificate-sign-request/confirm/%d\">Confirm certificate creation request</a>";
        //int value = Integer.parseInt(req.getSerialNumber());
        message.setText(String.format(messageTemplate, req.getId()));
        try {
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*public PublicKey getPublicKeyFromCSR(Long id) {
        try {
            PKCS10CertificationRequest csr = extractCertificationRequest(certificateSignRequestRepository.findById(id).getFullCertificate());

            JcaPKCS10CertificationRequest jcaCertRequest =
                    new JcaPKCS10CertificationRequest(csr.getEncoded()).setProvider("BC");
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            return jcaCertRequest.getPublicKey();
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }

        return null;
    }*/

    private PKCS10CertificationRequest extractCertificationRequest(byte[] rawRequest) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(rawRequest);
        Reader pemReader = new BufferedReader(new InputStreamReader(bis));
        PEMParser pemParser = new PEMParser(pemReader);

        Object parsedObj = pemParser.readObject();
        if (parsedObj instanceof PKCS10CertificationRequest) {
            return (PKCS10CertificationRequest) parsedObj;
        }

        throw new IOException();
    }

    public void declineRequest(Long id) {
        CertificateSignRequest certificateSignRequest = certificateSignRequestRepository.findById(id);

        certificateSignRequest.setStatus(3);
        certificateSignRequestRepository.save(certificateSignRequest);
    }

    public CertificateSignRequest acceptRequest(Long id) {
        CertificateSignRequest certificateSignRequest = certificateSignRequestRepository.findById(id);

        certificateSignRequest.setStatus(2);
        certificateSignRequestRepository.save(certificateSignRequest);


        return certificateSignRequest;
    }
}
