package com.example.bolnicaServer.controller;

import com.example.bolnicaServer.config.RestTemplateConfiguration;
//import com.example.bolnicaServer.model.Person;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

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
        restTemplateConfiguration.setToken("tokenjevisak");
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

    /*@PostMapping(value = "/person")
    private ResponseEntity<String> savePerson(@RequestHeader Map<String, String> headers,
                                              @RequestBody Person body) {
        ObjectNode response = JsonNodeFactory.instance.objectNode();
        headers.forEach((key, value) -> response.put(key, value));
        response.put("firstName", body.getFirstName());
        response.put("lastName", body.getLastName());
        response.put("age", body.getAge());
        //response.put("param", param);
        return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
    }*/

}
