package adminapi.adminaplication.controller;

import adminapi.adminaplication.service.OCSPService;
import org.bouncycastle.cert.ocsp.OCSPReq;
import org.bouncycastle.cert.ocsp.OCSPResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/ocsp")
public class OCSPController {

    @Autowired
    private OCSPService service;

    @PostMapping(value = "/check")
    public ResponseEntity<byte[]> checkIsCertificateRevoked(@RequestBody byte[] request) throws Exception{
        OCSPReq ocspReq = new OCSPReq(request);
        OCSPResp ocspResp = service.generateOCSPResponse(ocspReq);
        byte[] response = ocspResp.getEncoded();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
