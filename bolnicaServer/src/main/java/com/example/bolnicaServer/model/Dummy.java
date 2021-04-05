package com.example.bolnicaServer.model;

import javax.persistence.*;

public class Dummy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(unique = true, nullable = false)
    protected String username;

    @Column(nullable = false)
    protected String password;

    @Column(name = "last_password_reset_date")
    private Timestamp lastPasswordResetDate;
}
