package com.example.bolnicaServer.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/dummy")
public class DummyController {

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String testAdmin() {
        return "Admin pristupa";
    }

    //@PreAuthorize("hasRole('ROLE_DOCTOR')")
    @GetMapping("/doktor")
    public String testDoctor() {
        return "Doktor pristupa";
    }
}
