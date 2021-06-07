package com.example.bolnicaServer.repository;

import com.example.bolnicaServer.model.Admin;
import com.example.bolnicaServer.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    Doctor findByUsername(String username);
    Doctor findById(int id);
    Doctor findByEmailAddress(String email);
}
