package com.example.bolnicaServer.controller;

import com.example.bolnicaServer.dto.request.RevocationDTO;
import com.example.bolnicaServer.service.CertificateRevocationService;
import org.bouncycastle.operator.OperatorCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/api/cert/revocation")
public class CertificateRevocationController {

    @Autowired
    private CertificateRevocationService certificateRevocationService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> revokeCertificate(@RequestHeader("Authorization") String token, @RequestBody RevocationDTO dto) throws IOException, OperatorCreationException {

        try {
            certificateRevocationService.revoke(dto, token);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
