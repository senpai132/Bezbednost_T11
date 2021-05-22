package com.example.bolnicaServer.repository;

import com.example.bolnicaServer.model.User;
import com.example.bolnicaServer.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository
        extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);
}
