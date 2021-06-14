package com.example.bolnicaServer.model;

import javax.persistence.*;

@Entity
public class PatientMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    @Column(nullable = false)
    private String message;
    @Column(nullable = false)
    private int patientId;

    public  PatientMessage(){}

    public PatientMessage(Integer id, String message, int patientId) {
        this.id = id;
        this.message = message;
        this.patientId = patientId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }
}
