package com.example.bolnicaServer.service;

import com.example.bolnicaServer.model.PatientMessage;
import com.example.bolnicaServer.repository.PatientMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientMessageService {
    @Autowired
    private PatientMessageRepository patientMessageRepository;

    public List<PatientMessage> getAllMessages(){
        List<PatientMessage> patientMessages = patientMessageRepository.findAll();
        return patientMessages;
    }

    public List<PatientMessage> getAllPatientMessages(int patientId){
        List<PatientMessage> patientMessages = patientMessageRepository.findByPatientId(patientId);
        return patientMessages;
    }
}
