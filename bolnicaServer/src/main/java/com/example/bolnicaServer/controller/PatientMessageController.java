package com.example.bolnicaServer.controller;

import com.example.bolnicaServer.dto.request.RuleDTO;
import com.example.bolnicaServer.model.Device;
import com.example.bolnicaServer.model.PatientMessage;
import com.example.bolnicaServer.model.template.RuleTemplate;
import com.example.bolnicaServer.service.PatientMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/message")
public class PatientMessageController {
    @Autowired
    private PatientMessageService patientMessageService;

    @PreAuthorize("hasRole('ROLE_DOCTOR') && hasAuthority('PATIENT_ACCESS')")
    @GetMapping
    public ResponseEntity<List<PatientMessage>> getAllMessages() {

        return new ResponseEntity<>(patientMessageService.getAllMessages(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_DOCTOR') && hasAuthority('PATIENT_ACCESS')")
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<PatientMessage>> getPatientMessages(@PathVariable int id) {

        return new ResponseEntity<>(patientMessageService.getAllPatientMessages(id), HttpStatus.OK);
    }
}
