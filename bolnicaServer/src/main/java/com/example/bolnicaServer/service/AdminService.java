package com.example.bolnicaServer.service;

import com.example.bolnicaServer.config.RestTemplateConfiguration;
import com.example.bolnicaServer.dto.request.AdminDTO;
import com.example.bolnicaServer.model.Admin;
import com.example.bolnicaServer.model.Authority;
import com.example.bolnicaServer.model.Doctor;
import com.example.bolnicaServer.model.Privilege;
import com.example.bolnicaServer.repository.AdminRepository;
import com.example.bolnicaServer.repository.DoctorRepository;
import com.example.bolnicaServer.repository.PrivilegeRepository;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

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

    @Autowired
    private PrivilegeRepository privilegeRepository;

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

        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/invalid_passwords.txt"));
        int i = 0;
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                /*sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();*/
                if(admin.getPassword().equals(line)){
                    System.out.println("Losa sifra exception");
                    break;
                }
                line = br.readLine();
                i++;
            }
            String everything = sb.toString();
        } finally {
            br.close();
        }

        if(i < 10000){
            System.out.println("Ulazim ovde");
            throw new Exception("Losa sifra");
        }

        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        List<Authority> auth = authorityService.findByName("ROLE_ADMIN");
        admin.setAuthorities(auth);

        List<Privilege> pivs = privilegeRepository.findAll();

        Set<Privilege> adminpivs = new HashSet<Privilege>();

        for(Privilege p : pivs)
        {
            if(p.getName().equals("CREATE_RULE") || p.getName().equals("CERT_REQ") || p.getName().equals("ALL_LOGS"))
                adminpivs.add(p);
        }

        admin.setPrivileges(adminpivs);

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
