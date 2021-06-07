package com.example.bolnicaServer.repository;

import com.example.bolnicaServer.model.Admin;
import com.example.bolnicaServer.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin findByUsername(String username);
    Admin findById(int id);
    Admin findByEmailAddress(String email);
}
