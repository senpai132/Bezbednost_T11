package adminapi.adminaplication.controller;

import adminapi.adminaplication.dto.mapper.CertificateMapper;
import adminapi.adminaplication.dto.mapper.RevokedMapper;
import adminapi.adminaplication.dto.request.RevocationDTO;
import adminapi.adminaplication.dto.response.CertificateDTO;
import adminapi.adminaplication.dto.response.RevokedCertificateDTO;
import adminapi.adminaplication.model.RevokedCertificate;
import adminapi.adminaplication.service.CertificateService;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.cert.X509Certificate;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/certificate")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    private CertificateMapper mapper = new CertificateMapper();

    private RevokedMapper revokedMapper = new RevokedMapper();

    @Autowired
    private JavaMailSender mailSender;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CertificateDTO>> getAll() {
        List<X509Certificate> certificates = certificateService.findAllActive();

        return new ResponseEntity<>(mapper.toDtoList(certificates), HttpStatus.OK);
    }

    @RequestMapping(value="/dummy", method = RequestMethod.GET)
    public String getDummy() {
        sendMail();
        return "Heloo LLLLL";
    }

    private void sendMail(){
        StringBuilder stringBuilder = new StringBuilder();

        MimeMessage message = mailSender.createMimeMessage();


        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            //helper.setFrom("noreply@baeldung.com");
            helper.setTo("tehnodo98@gmail.com");
            helper.setSubject("Digital certificate");
            helper.setText("Certificate sent");

            FileSystemResource file
                    = new FileSystemResource(new File("src/main/resources/certificates/root.crt"));
            helper.addAttachment("Certificate", file);
            FileSystemResource fileKey
                    = new FileSystemResource(new File("src/main/resources/certificates/private.key"));
            helper.addAttachment("Private Key", fileKey);
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/removed", method = RequestMethod.GET)
    public ResponseEntity<List<RevokedCertificateDTO>> getRemoved() {
        List<RevokedCertificate> certificates = certificateService.findAllRemoved();

        return new ResponseEntity<>(revokedMapper.toDtoList(certificates), HttpStatus.OK);
    }
    //value="/{serialNumber}",
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> revokeCertificate(@RequestBody RevocationDTO revocationDTO) {
        certificateService.revokeCertificate(revocationDTO.getSerialNumber(), revocationDTO.getRevocationReason());

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    /*@RequestMapping(method=RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> revokeCertificate(@RequestBody RevokedCertificateDTO revokedCertificateDTO) {
        certificateService.revokeCertificate(revokedMapper.toEntity(revokedCertificateDTO));

        return new ResponseEntity<>(null, HttpStatus.OK);
    }*/
}
