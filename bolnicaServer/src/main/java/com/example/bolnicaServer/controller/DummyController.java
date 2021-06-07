package com.example.bolnicaServer.controller;

import com.example.bolnicaServer.config.RestTemplateConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin
@RequestMapping("/dummy")
public class DummyController {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String testAdmin() {
        return "Admin pristupa";
    }

    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @GetMapping("/doktor")
    public String testDoctor() {
        return "Doktor pristupa";
    }
    @Autowired
    private RestTemplateConfiguration restTemplateConfiguration;

    @GetMapping("/device")
    public String deviceTest() {
        RestTemplate restTemplate = restTemplateConfiguration.getOCSPRestTemplate();//.getRestTemplate();//new RestTemplate();

        try {
            //ResponseEntity<String> response
            //        = restTemplate.getForEntity("http://localhost:8082/dummy/device", String.class);
            //return response.toString();
            String result = restTemplate.getForObject("https://localhost:8082/dummy/device", String.class);
            return result;
        } catch (Exception exception) { //HttpClientErrorException
            //throw new InvalidAPIResponse("Invalid API response.");
            return "Fail";
        }
    }
}
