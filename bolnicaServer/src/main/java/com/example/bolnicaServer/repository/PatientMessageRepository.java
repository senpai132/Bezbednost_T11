package com.example.bolnicaServer.repository;

import com.example.bolnicaServer.model.PatientMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientMessageRepository extends JpaRepository<PatientMessage, Integer> {
    List<PatientMessage> findByPatientId(int patientId);
}
