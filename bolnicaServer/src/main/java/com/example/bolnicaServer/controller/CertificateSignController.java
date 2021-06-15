package com.example.bolnicaServer.controller;

import com.example.bolnicaServer.dto.request.CertificateSignRequestDTO;
import com.example.bolnicaServer.service.CertificateSignService;
import org.bouncycastle.operator.OperatorCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/api/certificate")
public class CertificateSignController {

    @Autowired
    private CertificateSignService certificateService;

    @PreAuthorize("hasRole('ROLE_ADMIN') && hasAuthority('CERT_REQ')")
    @RequestMapping(value = "/signingrequest", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendSertificateRequest(@RequestHeader("Authorization") String token, @Valid @RequestBody CertificateSignRequestDTO csr) throws IOException, OperatorCreationException {

        certificateService.sendRequest(csr, token);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(value="/dummy", method = RequestMethod.GET)
    public String getDummy() {
        return "Heloo HOSPITAL";
    }
}
