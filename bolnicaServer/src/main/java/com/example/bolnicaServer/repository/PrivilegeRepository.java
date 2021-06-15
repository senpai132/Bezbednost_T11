package com.example.bolnicaServer.repository;

import com.example.bolnicaServer.model.Authority;
import com.example.bolnicaServer.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Privilege findByName(String name);
}
