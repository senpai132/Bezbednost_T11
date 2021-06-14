package com.example.bolnicaServer.controller;

import com.example.bolnicaServer.config.RestTemplateConfiguration;
import com.example.bolnicaServer.dto.mapper.DoctorMapper;
import com.example.bolnicaServer.dto.request.AdminDTO;
import com.example.bolnicaServer.dto.request.DoctorDTO;
import com.example.bolnicaServer.model.Doctor;
import com.example.bolnicaServer.model.LogEntry;
import com.example.bolnicaServer.service.DoctorService;
import com.example.bolnicaServer.service.LogEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @Autowired
    private LogEntryService logEntryService;

    @Autowired
    private RestTemplateConfiguration restTemplateConfiguration;

    private DoctorMapper mapper = new DoctorMapper();

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<DoctorDTO>> getAllDoctors() {
        List<Doctor> users = doctorService.allDoctors();

        return new ResponseEntity<>(mapper.toDtoList(users), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public DoctorDTO createDoctor(@RequestBody DoctorDTO dto) {
        restTemplateConfiguration.setToken("1234567");
        RestTemplate restTemplate = restTemplateConfiguration.getRestTemplate();//new RestTemplate();
        HttpEntity<DoctorDTO> loggerRequest = new HttpEntity<>(dto);

        try {
            Doctor doctor = mapper.toEntity(dto);
            DoctorDTO doctorDTO = mapper.toDto(doctorService.createDoctor(doctor));

            try {
                ResponseEntity<LogEntry> logEntry = restTemplate.exchange(
                        "https://localhost:8085/logger/crud/doctoradd/ok",
                        HttpMethod.POST,
                        loggerRequest,
                        LogEntry.class);

                logEntryService.insertLog(logEntry.getBody());
            } catch (HttpClientErrorException exception) {
                exception.printStackTrace();
                //throw new InvalidAPIResponse("Invalid API response.");
            }

            return doctorDTO;
        } catch (Exception e) {
            try {
                ResponseEntity<LogEntry> logEntry = restTemplate.exchange(
                        "https://localhost:8085/logger/crud/doctoradd/err",
                        HttpMethod.POST,
                        loggerRequest,
                        LogEntry.class);

                logEntryService.insertLog(logEntry.getBody());
            } catch (HttpClientErrorException exception) {
                exception.printStackTrace();
                //throw new InvalidAPIResponse("Invalid API response.");
            }
            return null;
        }
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeDoctor(@PathVariable int id) {
        restTemplateConfiguration.setToken("1234567");
        RestTemplate restTemplate = restTemplateConfiguration.getRestTemplate();//new RestTemplate();
        HttpEntity<Integer> loggerRequest = new HttpEntity<>(id);


        try {
            doctorService.deleteDoctor(id);

            try {
                ResponseEntity<LogEntry> logEntry = restTemplate.exchange(
                        "https://localhost:8085/logger/crud/doctordel/ok/" +id,
                        HttpMethod.POST,
                        loggerRequest,
                        LogEntry.class);

                logEntryService.insertLog(logEntry.getBody());
            } catch (HttpClientErrorException exception) {
                exception.printStackTrace();
                //throw new InvalidAPIResponse("Invalid API response.");
            }

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            try {
                ResponseEntity<LogEntry> logEntry = restTemplate.exchange(
                        "https://localhost:8085/logger/crud/doctordel/err/"+id,
                        HttpMethod.POST,
                        loggerRequest,
                        LogEntry.class);

                logEntryService.insertLog(logEntry.getBody());
            } catch (HttpClientErrorException exception) {
                exception.printStackTrace();
                //throw new InvalidAPIResponse("Invalid API response.");
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


    }
}
