package com.example.bolnicaServer.dto.mapper;


import com.example.bolnicaServer.dto.response.PatientDTO;
import com.example.bolnicaServer.model.Patient;
import java.util.ArrayList;
import java.util.List;

public class PatientMapper implements  MapperInterface<Patient, PatientDTO>{
    @Override
    public Patient toEntity(PatientDTO dto) {
        Patient patient = new Patient(dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getDateOfBirth());
        return patient;
    }

    @Override
    public List<Patient> toEntityList(List<PatientDTO> dtoList) {
        List<Patient> patients = new ArrayList<>();
        for(PatientDTO patientDTO : dtoList){
            patients.add(this.toEntity(patientDTO));
        }
        return patients;
    }

    @Override
    public PatientDTO toDto(Patient entity) {

        PatientDTO dto = new PatientDTO(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getEmailAddress(),
                entity.getDateOfBirth());
        return dto;
    }

    @Override
    public List<PatientDTO> toDtoList(List<Patient> entityList) {
        List<PatientDTO> patientDTOS = new ArrayList<>();
        for(Patient a : entityList){
            patientDTOS.add(this.toDto(a));
        }
        return patientDTOS;
    }
}
