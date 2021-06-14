package com.example.bolnicaServer.controller;

import com.example.bolnicaServer.config.RestTemplateConfiguration;
import com.example.bolnicaServer.dto.mapper.AdminMapper;
import com.example.bolnicaServer.dto.request.AdminDTO;
import com.example.bolnicaServer.dto.request.UserLoginDTO;
import com.example.bolnicaServer.model.Admin;
import com.example.bolnicaServer.model.LogEntry;
import com.example.bolnicaServer.service.AdminService;
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
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private LogEntryService logEntryService;

    @Autowired
    private RestTemplateConfiguration restTemplateConfiguration;

    private AdminMapper mapper = new AdminMapper();


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<AdminDTO>> getAllAdmins() {
        List<Admin> users = adminService.allAdmins();

        return new ResponseEntity<>(mapper.toDtoList(users), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public AdminDTO createAdmin(@RequestBody AdminDTO dto) {
        restTemplateConfiguration.setToken("1234567");
        RestTemplate restTemplate = restTemplateConfiguration.getRestTemplate();//new RestTemplate();
        HttpEntity<AdminDTO> loggerRequest = new HttpEntity<>(dto);

        try {
            Admin admin = mapper.toEntity(dto);
            AdminDTO adminDTO = mapper.toDto(adminService.createAdmin(admin));

            try {
                ResponseEntity<LogEntry> logEntry = restTemplate.exchange(
                        "https://localhost:8085/logger/crud/adminadd/ok",
                        HttpMethod.POST,
                        loggerRequest,
                        LogEntry.class);

                logEntryService.insertLog(logEntry.getBody());
            } catch (HttpClientErrorException exception) {
                exception.printStackTrace();
                //throw new InvalidAPIResponse("Invalid API response.");
            }

            return adminDTO;
        } catch (Exception e) {
            System.out.println(e);

            try {
                ResponseEntity<LogEntry> logEntry = restTemplate.exchange(
                        "https://localhost:8085/logger/crud/adminadd/err",
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
    public ResponseEntity<?> removeAdmin(@PathVariable int id) {
        restTemplateConfiguration.setToken("1234567");
        RestTemplate restTemplate = restTemplateConfiguration.getRestTemplate();//new RestTemplate();
        HttpEntity<Integer> loggerRequest = new HttpEntity<>(id);

        try {
            adminService.deleteAdmin(id);

            try {
                ResponseEntity<LogEntry> logEntry = restTemplate.exchange(
                        "https://localhost:8085/logger/crud/admindel/ok/"+id,
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
            //System.out.println(e);
            try {
                ResponseEntity<LogEntry> logEntry = restTemplate.exchange(
                        "https://localhost:8085/logger/crud/admindel/err/"+id,
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
