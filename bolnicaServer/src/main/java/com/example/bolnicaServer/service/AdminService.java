package com.example.bolnicaServer.service;

import com.example.bolnicaServer.dto.request.AdminDTO;
import com.example.bolnicaServer.model.Admin;
import com.example.bolnicaServer.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public Admin getAdminByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    public Admin getAdminById(Integer id) throws Exception {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));

        return admin;
    }

    public Admin createAdmin(Admin admin) throws Exception{

        Admin adminExisting = adminRepository.findByEmailAddress(admin.getEmailAddress());
        if(adminExisting != null){
            throw new Exception("Admin with given email already exists");
        }

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
