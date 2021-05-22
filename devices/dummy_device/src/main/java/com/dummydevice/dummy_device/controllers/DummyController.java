package com.dummydevice.dummy_device.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/dummy")
public class DummyController {
    @GetMapping("/device")
    public String testAdmin() {
        return "Pristup uredjaju";
    }
}
