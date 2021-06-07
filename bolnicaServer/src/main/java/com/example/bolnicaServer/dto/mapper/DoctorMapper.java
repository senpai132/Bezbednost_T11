package com.example.bolnicaServer.dto.mapper;

import com.example.bolnicaServer.dto.request.DoctorDTO;
import com.example.bolnicaServer.dto.request.DoctorDTO;
import com.example.bolnicaServer.model.Doctor;
import com.example.bolnicaServer.model.Doctor;

import java.util.ArrayList;
import java.util.List;

public class DoctorMapper implements  MapperInterface<Doctor, DoctorDTO>{
    @Override
    public Doctor toEntity(DoctorDTO dto) {
        Doctor doctor = new Doctor(dto.getUsername(), dto.getEmail(), dto.getPassword());
        return doctor;
    }

    @Override
    public List<Doctor> toEntityList(List<DoctorDTO> dtoList) {
        List<Doctor> doctors = new ArrayList<>();
        for(DoctorDTO doctorDTO : dtoList){
            doctors.add(this.toEntity(doctorDTO));
        }
        return doctors;
    }

    @Override
    public DoctorDTO toDto(Doctor entity) {

        DoctorDTO dto = new DoctorDTO(entity.getId(), entity.getUsername(), entity.getEmailAddress(), "");
        return dto;
    }

    @Override
    public List<DoctorDTO> toDtoList(List<Doctor> entityList) {
        List<DoctorDTO> doctorDTOS = new ArrayList<>();
        for(Doctor a : entityList){
            doctorDTOS.add(this.toDto(a));
        }
        return doctorDTOS;
    }
}
