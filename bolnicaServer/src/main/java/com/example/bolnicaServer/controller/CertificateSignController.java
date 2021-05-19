package com.example.bolnicaServer.controller;

import com.example.bolnicaServer.dto.CertificateSignRequestDTO;
import com.example.bolnicaServer.service.CertificateSignService;
import org.bouncycastle.operator.OperatorCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/certificate")
public class CertificateSignController {

    @Autowired
    private CertificateSignService certificateService;

    @RequestMapping(value = "/signingrequest", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendSertificateRequest(@RequestBody CertificateSignRequestDTO csr) throws IOException, OperatorCreationException {

        certificateService.sendRequest(csr);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
