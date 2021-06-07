package com.example.bolnicaServer.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Doctor extends User{
    public Doctor(){}

    public Doctor(
            int id,
            String username,
            String email,
            String password
    ) {
        this.id = id;
        this.username = username;
        this.emailAddress = email;
        this.password = password;
    }

    public Doctor(
            String username,
            String email,
            String password
    ) {
        this.username = username;
        this.emailAddress = email;
        this.password = password;
    }
}
