package com.example.bolnicaServer.controller;

import com.example.bolnicaServer.dto.mapper.AdminMapper;
import com.example.bolnicaServer.dto.request.AdminDTO;
import com.example.bolnicaServer.model.Admin;
import com.example.bolnicaServer.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    private AdminMapper mapper = new AdminMapper();


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<AdminDTO>> getAllAdmins() {
        List<Admin> users = adminService.allAdmins();

        return new ResponseEntity<>(mapper.toDtoList(users), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public AdminDTO createAdmin(@RequestBody AdminDTO dto) {
        try {
            Admin admin = mapper.toEntity(dto);
            AdminDTO adminDTO = mapper.toDto(adminService.createAdmin(admin));
            return adminDTO;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeAdmin(@PathVariable int id) {
        try {
            adminService.deleteAdmin(id);
        } catch (Exception e) {
            System.out.println(e);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
