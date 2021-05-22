package com.example.bolnicaServer.repository;

import com.example.bolnicaServer.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    User findByEmailAddress(String emailAddress);

    Page<User> findAll(Pageable pageable);
}
