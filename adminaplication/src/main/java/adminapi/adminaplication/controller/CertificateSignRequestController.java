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

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CertificateSignRequestDTO>> getAllPendingRequests() {
        List<CertificateSignRequest> certificateSignRequestList = certificateSignRequestService.findAllPendingRequest();

        return new ResponseEntity<>(mapper.toDtoList(certificateSignRequestList), HttpStatus.OK);
    }

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

    @RequestMapping(value="/accept/{id}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> acceptSignRequest(@PathVariable Integer id){

        CertificateSignRequest certificateSignRequest = certificateSignRequestService.acceptRequest(id);

        certificateService.generateCertificate(certificateSignRequest);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(value="/decline/{id}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> declineSignRequest(@PathVariable Integer id){

        certificateSignRequestService.declineRequest(id);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
