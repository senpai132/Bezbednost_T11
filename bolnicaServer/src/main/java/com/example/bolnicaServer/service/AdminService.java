package com.example.bolnicaServer.service;

import com.example.bolnicaServer.config.RestTemplateConfiguration;
import com.example.bolnicaServer.dto.request.AdminDTO;
import com.example.bolnicaServer.model.Admin;
import com.example.bolnicaServer.model.Authority;
import com.example.bolnicaServer.model.Doctor;
import com.example.bolnicaServer.repository.AdminRepository;
import com.example.bolnicaServer.repository.DoctorRepository;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Admin getAdminByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    public Admin getAdminById(Integer id) throws Exception {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));

        return admin;
    }

    public Admin createAdmin(Admin admin) throws Exception{

        Doctor user = doctorRepository.findByEmailAddress(admin.getEmailAddress());
        if(user != null){
            throw new Exception("User with given email already exists");
        }

        user = doctorRepository.findByUsername(admin.getUsername());
        if(user != null){
            throw new Exception("User with given username already exists");
        }

        Admin adminExisting = adminRepository.findByEmailAddress(admin.getEmailAddress());
        if(adminExisting != null){
            throw new Exception("Admin with given email already exists");
        }

        adminExisting = adminRepository.findByUsername(admin.getUsername());
        if(adminExisting != null){
            throw new Exception("Admin with given username already exists");
        }
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        List<Authority> auth = authorityService.findByName("ROLE_ADMIN");
        admin.setAuthorities(auth);
        adminRepository.save(admin);

        return admin;
    }

    public List<Admin> allAdmins(){
        return adminRepository.findAll();
    }

    public void deleteAdmin(Integer id) throws Exception{
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));

        adminRepository.delete(admin);
    }
}
