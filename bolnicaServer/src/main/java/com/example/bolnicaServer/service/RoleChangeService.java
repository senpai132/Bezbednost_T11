package com.example.bolnicaServer.service;

import com.example.bolnicaServer.model.Admin;
import com.example.bolnicaServer.model.Authority;
import com.example.bolnicaServer.model.Doctor;
import com.example.bolnicaServer.model.Privilege;
import com.example.bolnicaServer.repository.AdminRepository;
import com.example.bolnicaServer.repository.DoctorRepository;
import com.example.bolnicaServer.repository.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleChangeService {
    @Autowired
    AdminRepository adminRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    public void adminToDoctor(int id){
        Admin admin = adminRepository.findById(id);
        Doctor doctor = new Doctor();
        doctor.setEmailAddress(admin.getEmailAddress());
        doctor.setUsername(admin.getUsername());
        doctor.setPassword(admin.getPassword());
        doctor.setLastPasswordResetDate(admin.getLastPasswordResetDate());
        List<Authority> auth = authorityService.findByName("ROLE_DOCTOR");
        doctor.setAuthorities(auth);

        List<Privilege> pivs = privilegeRepository.findAll();

        Set<Privilege> docpivs = new HashSet<Privilege>();

        for(Privilege p : pivs)
        {
            if(p.getName().equals("CREATE_RULE") || p.getName().equals("PATIENT_ACCESS"))
                docpivs.add(p);
        }

        doctor.setPrivileges(docpivs);


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

        List<Privilege> pivs = privilegeRepository.findAll();

        Set<Privilege> adminpivs = new HashSet<Privilege>();

        for(Privilege p : pivs)
        {
            if(p.getName().equals("CREATE_RULE") || p.getName().equals("CERT_REQ") || p.getName().equals("ALL_LOGS"))
                adminpivs.add(p);
        }

        admin.setPrivileges(adminpivs);

        adminRepository.save(admin);
        doctorRepository.delete(doctor);
        System.out.println("Doktor je postao admin");
    }
}
