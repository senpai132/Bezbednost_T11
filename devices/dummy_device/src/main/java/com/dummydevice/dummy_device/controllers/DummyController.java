package com.dummydevice.dummy_device.controllers;

import com.dummydevice.dummy_device.config.RestTemplateConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin
@RequestMapping("/dummy")
public class DummyController {

    @Autowired
    private RestTemplateConfiguration restTemplateConfiguration;

    @GetMapping("/device")
    public String testAdmin() {
        return "Pristup uredjaju";
    }

    @GetMapping("/call")
    public String testCalling() {
        restTemplateConfiguration.setToken("tokenjevisak");
        RestTemplate restTemplate = restTemplateConfiguration.getOCSPRestTemplate();//.getRestTemplate();//new RestTemplate();

        try {
            //ResponseEntity<String> response
            //        = restTemplate.getForEntity("http://localhost:8082/dummy/device", String.class);
            //return response.toString();

            String result = restTemplate.getForObject("https://localhost:8081/dummy/template", String.class);
            return result;
        } catch (Exception exception) { //HttpClientErrorException
            //throw new InvalidAPIResponse("Invalid API response.");
            return "Fail";
        }
        //return "Pristup uredjaju";
    }
}
