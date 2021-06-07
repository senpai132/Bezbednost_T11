package com.example.bolnicaServer.service;

import com.example.bolnicaServer.model.Doctor;
import com.example.bolnicaServer.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    public Doctor getDoctorByUsername(String username) {
        return doctorRepository.findByUsername(username);
    }

    public Doctor getDoctorById(Integer id) throws Exception {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));

        return doctor;
    }

    public Doctor createDoctor(Doctor doctor) throws Exception{

        Doctor doctorExisting = doctorRepository.findByEmailAddress(doctor.getEmailAddress());
        if(doctorExisting != null){
            throw new Exception("Doctor with given email already exists");
        }

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
