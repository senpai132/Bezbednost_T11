package com.example.bolnicaServer.service;

import com.example.bolnicaServer.model.Admin;
import com.example.bolnicaServer.model.Authority;
import com.example.bolnicaServer.model.Doctor;
import com.example.bolnicaServer.repository.AdminRepository;
import com.example.bolnicaServer.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleChangeService {
    @Autowired
    AdminRepository adminRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    private AuthorityService authorityService;

    public void adminToDoctor(int id){
        Admin admin = adminRepository.findById(id);
        Doctor doctor = new Doctor();
        doctor.setEmailAddress(admin.getEmailAddress());
        doctor.setUsername(admin.getUsername());
        doctor.setPassword(admin.getPassword());
        doctor.setLastPasswordResetDate(admin.getLastPasswordResetDate());
        List<Authority> auth = authorityService.findByName("ROLE_DOCTOR");
        doctor.setAuthorities(auth);
        doctorRepository.save(doctor);
        adminRepository.delete(admin);
    }

    public void doctorToAdmin(int id){
        Doctor doctor = doctorRepository.findById(id);
        Admin admin = new Admin();
        admin.setEmailAddress(doctor.getEmailAddress());
        admin.setUsername(doctor.getUsername());
        admin.setPassword(doctor.getPassword());
        admin.setLastPasswordResetDate(doctor.getLastPasswordResetDate());

        List<Authority> auth = authorityService.findByName("ROLE_ADMIN");
        admin.setAuthorities(auth);
        adminRepository.save(admin);
        doctorRepository.delete(doctor);
        System.out.println("Doktor je postao admin");
    }
}
