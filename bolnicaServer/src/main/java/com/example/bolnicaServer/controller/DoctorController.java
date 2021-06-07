package com.example.bolnicaServer.controller;

import com.example.bolnicaServer.dto.mapper.DoctorMapper;
import com.example.bolnicaServer.dto.request.DoctorDTO;
import com.example.bolnicaServer.model.Doctor;
import com.example.bolnicaServer.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    private DoctorMapper mapper = new DoctorMapper();


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<DoctorDTO>> getAllDoctors() {
        List<Doctor> users = doctorService.allDoctors();

        return new ResponseEntity<>(mapper.toDtoList(users), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public DoctorDTO createDoctor(@RequestBody DoctorDTO dto) {
        try {
            Doctor doctor = mapper.toEntity(dto);
            DoctorDTO doctorDTO = mapper.toDto(doctorService.createDoctor(doctor));
            return doctorDTO;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeDoctor(@PathVariable int id) {
        try {
            doctorService.deleteDoctor(id);
        } catch (Exception e) {
            System.out.println(e);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
