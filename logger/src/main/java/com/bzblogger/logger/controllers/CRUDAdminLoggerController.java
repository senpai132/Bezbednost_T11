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
        logEntryDTO.setType("CRUD_ADMIN");
        logEntryDTO.setCode("ERR");
        logEntryDTO.setMessage("Admin with email: " + dto.getEmail() + " is unsuccessfully added.");
        logEntryDTO.setOccurrenceDate(new Date());
        logEntryDTO.setOccurrenceTime(LocalTime.now());

        return logEntryDTO;
    }

    @PostMapping("/adminadd/ok")
    public LogEntryDTO adminAddOk(@RequestBody AdminDTO dto) {
        LogEntryDTO logEntryDTO = new LogEntryDTO();
        logEntryDTO.setType("CRUD_ADMIN");
        logEntryDTO.setCode("OK");
        logEntryDTO.setMessage("Admin with email: " + dto.getEmail() + " is successfully added.");
        logEntryDTO.setOccurrenceDate(new Date());
        logEntryDTO.setOccurrenceTime(LocalTime.now());

        return logEntryDTO;
    }

    @PostMapping("/adminput/err")
    public LogEntryDTO adminUpdateError(@RequestBody AdminDTO dto) {
        LogEntryDTO logEntryDTO = new LogEntryDTO();
        logEntryDTO.setType("CRUD_ADMIN");
        logEntryDTO.setCode("ERR");
        logEntryDTO.setMessage("Admin with email: " + dto.getEmail() + " is unsuccessfully updated.");
        logEntryDTO.setOccurrenceDate(new Date());
        logEntryDTO.setOccurrenceTime(LocalTime.now());

        return logEntryDTO;
    }

    @PostMapping("/adminput/ok")
    public LogEntryDTO adminUpdateOk(@RequestBody AdminDTO dto) {
        LogEntryDTO logEntryDTO = new LogEntryDTO();
        logEntryDTO.setType("CRUD_ADMIN");
        logEntryDTO.setCode("OK");
        logEntryDTO.setMessage("Admin with email: " + dto.getEmail() + " is successfully updated.");
        logEntryDTO.setOccurrenceDate(new Date());
        logEntryDTO.setOccurrenceTime(LocalTime.now());

        return logEntryDTO;
    }

    @PostMapping("/admindel/err/{id}")
    public LogEntryDTO adminDeleteError(@PathVariable int id) {
        LogEntryDTO logEntryDTO = new LogEntryDTO();
        logEntryDTO.setType("CRUD_ADMIN");
        logEntryDTO.setCode("ERR");
        logEntryDTO.setMessage("Failed to delete admin with id: " + id + ".");
        logEntryDTO.setOccurrenceDate(new Date());
        logEntryDTO.setOccurrenceTime(LocalTime.now());

        return logEntryDTO;
    }

    @PostMapping("/admindel/ok/{id}")
    public LogEntryDTO adminDeleteOk(@PathVariable int id) {
        LogEntryDTO logEntryDTO = new LogEntryDTO();
        logEntryDTO.setType("CRUD_ADMIN");
        logEntryDTO.setCode("OK");
        logEntryDTO.setMessage("Admin with id: " + id + " is successfully deleted.");
        logEntryDTO.setOccurrenceDate(new Date());
        logEntryDTO.setOccurrenceTime(LocalTime.now());

        return logEntryDTO;
    }
}
