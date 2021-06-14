package com.bzblogger.logger.controllers;

import com.bzblogger.logger.dto.AdminDTO;
import com.bzblogger.logger.dto.LogEntryDTO;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.Date;

@RestController
@CrossOrigin
@RequestMapping("/logger/crud")
public class CRUDAdminLoggerController {

    @PostMapping("/adminadd/err")
    public LogEntryDTO adminError(@RequestBody AdminDTO dto) {
        LogEntryDTO logEntryDTO = new LogEntryDTO();
        logEntryDTO.setType("CRUD_DOC");
        logEntryDTO.setCode("ERR");
        logEntryDTO.setMessage("Admin with email: " + dto.getEmail() + " is unsuccessfully added.");
        logEntryDTO.setOccurrenceDate(new Date());
        logEntryDTO.setOccurrenceTime(LocalTime.now());

        return logEntryDTO;
    }

    @PostMapping("/adminadd/ok")
    public LogEntryDTO adminAddOk(@RequestBody AdminDTO dto) {
        LogEntryDTO logEntryDTO = new LogEntryDTO();
        logEntryDTO.setType("CRUD_DOC");
        logEntryDTO.setCode("OK");
        logEntryDTO.setMessage("Admin with email: " + dto.getEmail() + " is successfully added.");
        logEntryDTO.setOccurrenceDate(new Date());
        logEntryDTO.setOccurrenceTime(LocalTime.now());

        return logEntryDTO;
    }

    @PostMapping("/adminput/err")
    public LogEntryDTO adminUpdateError(@RequestBody AdminDTO dto) {
        LogEntryDTO logEntryDTO = new LogEntryDTO();
        logEntryDTO.setType("CRUD_DOC");
        logEntryDTO.setCode("ERR");
        logEntryDTO.setMessage("Admin with email: " + dto.getEmail() + " is unsuccessfully updated.");
        logEntryDTO.setOccurrenceDate(new Date());
        logEntryDTO.setOccurrenceTime(LocalTime.now());

        return logEntryDTO;
    }

    @PostMapping("/adminput/ok")
    public LogEntryDTO adminUpdateOk(@RequestBody AdminDTO dto) {
        LogEntryDTO logEntryDTO = new LogEntryDTO();
        logEntryDTO.setType("CRUD_DOC");
        logEntryDTO.setCode("OK");
        logEntryDTO.setMessage("Admin with email: " + dto.getEmail() + " is successfully updated.");
        logEntryDTO.setOccurrenceDate(new Date());
        logEntryDTO.setOccurrenceTime(LocalTime.now());

        return logEntryDTO;
    }

    @PostMapping("/admindel/err")
    public LogEntryDTO adminDeleteError(@RequestBody AdminDTO dto) {
        LogEntryDTO logEntryDTO = new LogEntryDTO();
        logEntryDTO.setType("CRUD_DOC");
        logEntryDTO.setCode("ERR");
        logEntryDTO.setMessage("Admin with email: " + dto.getEmail() + " is unsuccessfully deleted.");
        logEntryDTO.setOccurrenceDate(new Date());
        logEntryDTO.setOccurrenceTime(LocalTime.now());

        return logEntryDTO;
    }

    @PostMapping("/admindel/ok")
    public LogEntryDTO adminDeleteOk(@RequestBody AdminDTO dto) {
        LogEntryDTO logEntryDTO = new LogEntryDTO();
        logEntryDTO.setType("CRUD_DOC");
        logEntryDTO.setCode("OK");
        logEntryDTO.setMessage("Admin with email: " + dto.getEmail() + " is successfully deleted.");
        logEntryDTO.setOccurrenceDate(new Date());
        logEntryDTO.setOccurrenceTime(LocalTime.now());

        return logEntryDTO;
    }
}
