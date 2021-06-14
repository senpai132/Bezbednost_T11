package com.example.bolnicaServer.model;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Date;

@Entity
public class LogEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(nullable = false)
    protected String type;

    @Column(nullable = false)
    protected String code;

    @Column(nullable = false)
    protected String message;

    @Column(nullable = false)
    protected Date occurrenceDate;

    @Column(nullable = false)
    protected LocalTime occurrenceTime;

    public LogEntry(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getOccurrenceDate() {
        return occurrenceDate;
    }

    public void setOccurrenceDate(Date occurenceTime) {
        this.occurrenceDate = occurenceTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalTime getOccurrenceTime() {
        return occurrenceTime;
    }

    public void setOccurrenceTime(LocalTime occurrenceTime) {
        this.occurrenceTime = occurrenceTime;
    }
}
