package com.example.bolnicaServer.repository;

import com.example.bolnicaServer.model.Admin;

public interface AdminRepository {
    Admin findByUsername(String username);
}
