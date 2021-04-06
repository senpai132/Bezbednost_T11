package adminapi.adminaplication.controller;

import adminapi.adminaplication.dto.mapper.CertificateMapper;
import adminapi.adminaplication.dto.response.CertificateDTO;
import adminapi.adminaplication.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CertificateDTO>> getAll() {
        List<X509Certificate> certificates = certificateService.findAll();

        return new ResponseEntity<>(mapper.toDtoList(certificates), HttpStatus.OK);
    }

    @RequestMapping(value="/{serialNumber}", method=RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> removeCertificate(@PathVariable BigInteger serialNumber) {
        certificateService.removeCertificate(serialNumber);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
