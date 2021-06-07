package adminapi.adminaplication.controller;


import adminapi.adminaplication.dto.mapper.CertificateSignRequestMapper;
import adminapi.adminaplication.dto.response.CertificateSignRequestDTO;
import adminapi.adminaplication.model.CertificateSignRequest;
import adminapi.adminaplication.service.CertificateService;
import adminapi.adminaplication.service.CertificateSignRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/certificate-sign-request")
public class CertificateSignRequestController {

    @Autowired
    private CertificateSignRequestService certificateSignRequestService;

    @Autowired
    private CertificateService certificateService;

    private CertificateSignRequestMapper mapper = new CertificateSignRequestMapper();

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CertificateSignRequestDTO>> getAllPendingRequests() {
        List<CertificateSignRequest> certificateSignRequestList = certificateSignRequestService.findAllPendingRequest();

        return new ResponseEntity<>(mapper.toDtoList(certificateSignRequestList), HttpStatus.OK);
    }

    @RequestMapping(value="/confirm/{id}", method = RequestMethod.GET)
    public String confirmRequest(@PathVariable Long id) {

        certificateSignRequestService.confirmCertificateRequest(id);
        return "Certificate creation request confirmed";
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CertificateSignRequestDTO> createSignRequest(@RequestBody byte[] request) {
        CertificateSignRequest certificateSignRequest = null;
        System.out.println("Usao");
        try {
            certificateSignRequest = certificateSignRequestService.createRequest(request);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(mapper.toDto(certificateSignRequest), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/accept/{id}", method=RequestMethod.PUT)
    public ResponseEntity<?> acceptSignRequest(@PathVariable Long id){

        CertificateSignRequest certificateSignRequest = certificateSignRequestService.acceptRequest(id);
        String template = "leaf";
        try {
            certificateService.createCertificate(certificateSignRequest, template);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/decline/{id}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> declineSignRequest(@PathVariable Long id){

        certificateSignRequestService.declineRequest(id);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
