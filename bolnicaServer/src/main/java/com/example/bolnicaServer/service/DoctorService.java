package com.example.bolnicaServer.service;

import com.example.bolnicaServer.model.Admin;
import com.example.bolnicaServer.model.Authority;
import com.example.bolnicaServer.model.Doctor;
import com.example.bolnicaServer.repository.AdminRepository;
import com.example.bolnicaServer.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AdminRepository adminRepository;

    public Doctor getDoctorByUsername(String username) {
        return doctorRepository.findByUsername(username);
    }

    public Doctor getDoctorById(Integer id) throws Exception {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));

        return doctor;
    }

    public Doctor createDoctor(Doctor doctor) throws Exception{

        Admin user = adminRepository.findByEmailAddress(doctor.getEmailAddress());
        if(user != null){
            throw new Exception("User with given email already exists");
        }

        user = adminRepository.findByUsername(doctor.getUsername());
        if(user != null){
            throw new Exception("User with given username already exists");
        }

        Doctor doctorExisting = doctorRepository.findByEmailAddress(doctor.getEmailAddress());
        if(doctorExisting != null){
            throw new Exception("Doctor with given email already exists");
        }

        doctorExisting = doctorRepository.findByUsername(doctor.getUsername());
        if(doctorExisting != null){
            throw new Exception("Doctor with given username already exists");
        }

        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        List<Authority> auth = authorityService.findByName("ROLE_DOCTOR");
        doctor.setAuthorities(auth);
        doctorRepository.save(doctor);

        return doctor;
    }

    public List<Doctor> allDoctors(){
        return doctorRepository.findAll();
    }

    public void deleteDoctor(Integer id) throws Exception{
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));

        doctorRepository.delete(doctor);
    }
}
