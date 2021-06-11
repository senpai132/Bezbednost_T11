package com.example.bolnicaServer.controller;

import com.example.bolnicaServer.dto.request.AdminDTO;
import com.example.bolnicaServer.dto.response.UserDTO;
import com.example.bolnicaServer.model.Admin;
import com.example.bolnicaServer.model.Doctor;
import com.example.bolnicaServer.service.AdminService;
import com.example.bolnicaServer.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    AdminService adminService;

    @Autowired
    DoctorService doctorService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserDTO>> getAllAdmins() {
        List<Admin> admins = adminService.allAdmins();
        List<Doctor> doctors = doctorService.allDoctors();
        List<UserDTO> allUsers = new ArrayList<>();

        for(Admin a : admins)
        {
            UserDTO dto = new UserDTO(a.getId(), a.getUsername(), a.getEmailAddress(), "ADMIN");
            allUsers.add(dto);
        }

        for(Doctor d : doctors)
        {
            allUsers.add(new UserDTO(d.getId(), d.getUsername(), d.getEmailAddress(), "DOCTOR"));
        }

        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }
}
