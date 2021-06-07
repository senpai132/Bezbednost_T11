package com.example.bolnicaServer.controller;

import com.example.bolnicaServer.dto.mapper.PatientMapper;
import com.example.bolnicaServer.dto.response.PatientDTO;
import com.example.bolnicaServer.model.Patient;
import com.example.bolnicaServer.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    private PatientMapper mapper = new PatientMapper();

    @GetMapping("/id/{id}")
    public PatientDTO findByUserId(@PathVariable int id)
    {
        PatientDTO patientDTO = mapper.toDto(patientService.findById(id));

        return patientDTO;
    }

    @GetMapping("/email/{email}")
    public PatientDTO findByUserEmail(@PathVariable String email)
    {
        PatientDTO patientDTO = mapper.toDto(patientService.findByEmail(email));

        return patientDTO;
    }

    @GetMapping
    public List<PatientDTO> findAll()
    {
        List<PatientDTO> patientDTOList = mapper.toDtoList(patientService.getAll());

        return patientDTOList;
    }
}
