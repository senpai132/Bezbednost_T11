package com.bzblogger.logger.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalTime;
import java.util.Date;

public class LogEntryDTO {
    
    public Integer id;
    public String type;
    public String code;
    public String message;
    public Date occurrenceDate;
    public LocalTime occurrenceTime;

    public LogEntryDTO(){}

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
