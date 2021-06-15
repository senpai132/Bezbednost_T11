package com.bzblogger.logger.controllers;

import com.bzblogger.logger.config.RestTemplateConfiguration;
import com.bzblogger.logger.dto.LogEntryDTO;
import com.bzblogger.logger.dto.UserLoginDTO;
import com.bzblogger.logger.dto.UserTokenStateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalTime;
import java.util.Date;

@RestController
@CrossOrigin
@RequestMapping("/logger/simulate")
public class AttackController {

    @Autowired
    private RestTemplateConfiguration restTemplateConfiguration;

    @PostMapping("/login")
    public void loginError(@RequestBody UserLoginDTO dto) {
        restTemplateConfiguration.setToken("1234567");
        RestTemplate restTemplate = restTemplateConfiguration.getRestTemplate();//new RestTemplate();
        HttpEntity<UserLoginDTO> loggerRequest = new HttpEntity<>(dto);
        for(int i=0; i < 5; i++) {
            try {
                ResponseEntity<UserTokenStateDTO> logEntry = restTemplate.exchange(
                        "https://localhost:8081/auth/login",
                        HttpMethod.POST,
                        loggerRequest,
                        UserTokenStateDTO.class);
            } catch (HttpClientErrorException exception) {
                System.out.println("login timeout");
                //throw new InvalidAPIResponse("Invalid API response.");
            }
        }

    }

    @PostMapping("/ddos")
    public void loginError() {
        restTemplateConfiguration.setToken("1234567");
        RestTemplate restTemplate = restTemplateConfiguration.getRestTemplate();//new RestTemplate();
        for(int i=0; i < 70; i++) {
            try {
                String result = restTemplate.getForObject("https://localhost:8081/device/template", String.class);
            } catch (Exception exception) {
                System.out.println("ddos timeout");
                //throw new InvalidAPIResponse("Invalid API response.");
            }
        }

    }
}
