package com.bzblogger.logger.controllers;

import com.bzblogger.logger.dto.DoctorDTO;
import com.bzblogger.logger.dto.LogEntryDTO;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.Date;

@RestController
@CrossOrigin
@RequestMapping("/logger/crud")
public class CRUDDoctorLoggerController {

    @PostMapping("/doctoradd/err")
    public LogEntryDTO doctorError(@RequestBody DoctorDTO dto) {
        LogEntryDTO logEntryDTO = new LogEntryDTO();
        logEntryDTO.setType("CRUD_DOC");
        logEntryDTO.setCode("ERR");
        logEntryDTO.setMessage("Doctor with email: " + dto.getEmail() + " is unsuccessfully added.");
        logEntryDTO.setOccurrenceDate(new Date());
        logEntryDTO.setOccurrenceTime(LocalTime.now());

        return logEntryDTO;
    }

    @PostMapping("/doctoradd/ok")
    public LogEntryDTO doctorAddOk(@RequestBody DoctorDTO dto) {
        LogEntryDTO logEntryDTO = new LogEntryDTO();
        logEntryDTO.setType("CRUD_DOC");
        logEntryDTO.setCode("OK");
        logEntryDTO.setMessage("Doctor with email: " + dto.getEmail() + " is successfully added.");
        logEntryDTO.setOccurrenceDate(new Date());
        logEntryDTO.setOccurrenceTime(LocalTime.now());

        return logEntryDTO;
    }

    @PostMapping("/doctorput/err")
    public LogEntryDTO doctorUpdateError(@RequestBody DoctorDTO dto) {
        LogEntryDTO logEntryDTO = new LogEntryDTO();
        logEntryDTO.setType("CRUD_DOC");
        logEntryDTO.setCode("ERR");
        logEntryDTO.setMessage("Doctor with email: " + dto.getEmail() + " is unsuccessfully updated.");
        logEntryDTO.setOccurrenceDate(new Date());
        logEntryDTO.setOccurrenceTime(LocalTime.now());

        return logEntryDTO;
    }

    @PostMapping("/doctorput/ok")
    public LogEntryDTO doctorUpdateOk(@RequestBody DoctorDTO dto) {
        LogEntryDTO logEntryDTO = new LogEntryDTO();
        logEntryDTO.setType("CRUD_DOC");
        logEntryDTO.setCode("OK");
        logEntryDTO.setMessage("Doctor with email: " + dto.getEmail() + " is successfully updated.");
        logEntryDTO.setOccurrenceDate(new Date());
        logEntryDTO.setOccurrenceTime(LocalTime.now());

        return logEntryDTO;
    }

    @PostMapping("/doctordel/err")
    public LogEntryDTO doctorDeleteError(@RequestBody DoctorDTO dto) {
        LogEntryDTO logEntryDTO = new LogEntryDTO();
        logEntryDTO.setType("CRUD_DOC");
        logEntryDTO.setCode("ERR");
        logEntryDTO.setMessage("Doctor with email: " + dto.getEmail() + " is unsuccessfully deleted.");
        logEntryDTO.setOccurrenceDate(new Date());
        logEntryDTO.setOccurrenceTime(LocalTime.now());

        return logEntryDTO;
    }

    @PostMapping("/doctordel/ok")
    public LogEntryDTO doctorDeleteOk(@RequestBody DoctorDTO dto) {
        LogEntryDTO logEntryDTO = new LogEntryDTO();
        logEntryDTO.setType("CRUD_DOC");
        logEntryDTO.setCode("OK");
        logEntryDTO.setMessage("Doctor with email: " + dto.getEmail() + " is successfully deleted.");
        logEntryDTO.setOccurrenceDate(new Date());
        logEntryDTO.setOccurrenceTime(LocalTime.now());

        return logEntryDTO;
    }
}
