package com.bzblogger.logger.controllers;

import com.bzblogger.logger.dto.LogEntryDTO;
import com.bzblogger.logger.dto.UserLoginDTO;
import org.springframework.web.bind.annotation.*;
import java.time.LocalTime;

import java.util.Date;

@RestController
@CrossOrigin
@RequestMapping("/logger/auth")
public class AuthLoggerController {

    @PostMapping("/err")
    public LogEntryDTO loginError(@RequestBody UserLoginDTO dto) {
        LogEntryDTO logEntryDTO = new LogEntryDTO();
        logEntryDTO.setType("AUTH");
        logEntryDTO.setCode("ERR");
        logEntryDTO.setMessage("User with username: " + dto.getUsername() + " tried to log in unsuccessfully.");
        logEntryDTO.setOccurrenceDate(new Date());
        logEntryDTO.setOccurrenceTime(LocalTime.now());

        return logEntryDTO;
    }

    @PostMapping("/ok")
    public LogEntryDTO loginOk(@RequestBody UserLoginDTO dto) {
        LogEntryDTO logEntryDTO = new LogEntryDTO();
        logEntryDTO.setType("AUTH");
        logEntryDTO.setCode("OK");
        logEntryDTO.setMessage("User with username: " + dto.getUsername() + " logged in successfully.");
        logEntryDTO.setOccurrenceDate(new Date());
        logEntryDTO.setOccurrenceTime(LocalTime.now());

        return logEntryDTO;
    }

    @PostMapping("/blocked")
    public LogEntryDTO loginBlocked(@RequestBody UserLoginDTO dto) {
        LogEntryDTO logEntryDTO = new LogEntryDTO();
        logEntryDTO.setType("AUTH");
        logEntryDTO.setCode("BLOCKED");
        logEntryDTO.setMessage("User with username: " + dto.getUsername() + " is blocked.");
        logEntryDTO.setOccurrenceDate(new Date());
        logEntryDTO.setOccurrenceTime(LocalTime.now());

        return logEntryDTO;
    }
}
