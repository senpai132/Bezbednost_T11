package com.example.bolnicaServer.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(nullable = false)
    protected String firstName;

    @Column(nullable = false)
    protected String lastName;

    @Column(unique = true, nullable = false)
    protected String emailAddress;

    @Column
    protected Date dateOfBirth;

    public Patient() {}

    public Patient(Integer id, String firstName, String lastName, String emailAddress, Date dateOfBirth) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.dateOfBirth = dateOfBirth;
    }

    public Patient(String firstName, String lastName, String emailAddress, Date dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
