package com.example.bolnicaServer.dto.mapper;

import com.example.bolnicaServer.dto.request.AdminDTO;
import com.example.bolnicaServer.model.Admin;
import com.example.bolnicaServer.model.User;

import java.util.ArrayList;
import java.util.List;

public class AdminMapper implements  MapperInterface<Admin, AdminDTO>{
    @Override
    public Admin toEntity(AdminDTO dto) {
        Admin admin = new Admin(dto.getUsername(), dto.getEmail(), dto.getPassword());
        return admin;
    }

    @Override
    public List<Admin> toEntityList(List<AdminDTO> dtoList) {
        List<Admin> admins = new ArrayList<>();
        for(AdminDTO adminDTO : dtoList){
            admins.add(this.toEntity(adminDTO));
        }
        return admins;
    }

    @Override
    public AdminDTO toDto(Admin entity) {

        AdminDTO dto = new AdminDTO(entity.getId(), entity.getUsername(), entity.getEmailAddress(), "");
        return dto;
    }

    @Override
    public List<AdminDTO> toDtoList(List<Admin> entityList) {
        List<AdminDTO> adminDTOS = new ArrayList<>();
        for(Admin a : entityList){
            adminDTOS.add(this.toDto(a));
        }
        return adminDTOS;
    }
}
