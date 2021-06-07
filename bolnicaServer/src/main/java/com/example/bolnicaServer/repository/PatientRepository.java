package com.example.bolnicaServer.repository;

import com.example.bolnicaServer.model.Admin;
import com.example.bolnicaServer.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
    Patient findById(int id);
    Patient findByEmailAddress(String email);
}
