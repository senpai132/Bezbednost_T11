package com.example.bolnicaServer.controller;

import com.example.bolnicaServer.dto.request.RuleDTO;
import com.example.bolnicaServer.model.Device;
import com.example.bolnicaServer.model.template.RuleTemplate;
import com.example.bolnicaServer.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/rule")
public class RuleController {

    @Autowired
    private RuleService ruleService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DOCTOR')")
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createRule(@Valid @RequestBody RuleDTO dto) {
        RuleTemplate rule = new RuleTemplate(dto.getUseFunction(),
                dto.getMinValue(), dto.getMaxValue(), Device.Alarm.NO, Device.Alarm.NO);

        ruleService.generateRule(rule);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
