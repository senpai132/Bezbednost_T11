package com.example.bolnicaServer.controller;

import com.example.bolnicaServer.service.RoleChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/rolechange")
public class RoleChangeController {

    @Autowired
    RoleChangeService roleChangeService;

    @PutMapping("admintodoctor/{id}")
    public ResponseEntity<?> adminToDoctor(@PathVariable int id) {
        try {
            roleChangeService.adminToDoctor(id);
        } catch (Exception e) {
            System.out.println(e);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("doctortoadmin/{id}")
    public ResponseEntity<?> doctorToAdmin(@PathVariable int id) {
        try {
            roleChangeService.doctorToAdmin(id);
        } catch (Exception e) {
            System.out.println(e);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
