package com.example.bolnicaServer.service;

import com.example.bolnicaServer.model.Patient;
import com.example.bolnicaServer.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    @Autowired
    PatientRepository patientRepository;

    public Patient findById(int id) {
        Patient patient = patientRepository.findById(id);

        return patient;
    }

    public Patient findByEmail(String email) {
        Patient patient = patientRepository.findByEmailAddress(email);

        return patient;
    }

    public List<Patient> getAll() {
        return patientRepository.findAll();
    }
}
