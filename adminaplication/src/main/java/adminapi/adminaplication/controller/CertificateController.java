package adminapi.adminaplication.controller;

import adminapi.adminaplication.dto.mapper.CertificateMapper;
import adminapi.adminaplication.dto.mapper.RevokedMapper;
import adminapi.adminaplication.dto.request.RevocationDTO;
import adminapi.adminaplication.dto.response.CertificateDTO;
import adminapi.adminaplication.dto.response.RevokedCertificateDTO;
import adminapi.adminaplication.model.RevokedCertificate;
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

    private RevokedMapper revokedMapper = new RevokedMapper();

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CertificateDTO>> getAll() {
        List<X509Certificate> certificates = certificateService.findAllActive();

        return new ResponseEntity<>(mapper.toDtoList(certificates), HttpStatus.OK);
    }

    @RequestMapping(value="/removed", method = RequestMethod.GET)
    public ResponseEntity<List<RevokedCertificateDTO>> getRemoved() {
        List<RevokedCertificate> certificates = certificateService.findAllRemoved();

        return new ResponseEntity<>(revokedMapper.toDtoList(certificates), HttpStatus.OK);
    }
    //value="/{serialNumber}",
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
