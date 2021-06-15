package com.bzblogger.logger.controllers;

import com.bzblogger.logger.dto.LogEntryDTO;
import com.bzblogger.logger.dto.UserLoginDTO;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.Date;

@RestController
@CrossOrigin
@RequestMapping("/logger/attack")
public class DOSAttackLogger {

    @PostMapping("/dos")
    public LogEntryDTO loginError(@RequestBody String ip) {
        LogEntryDTO logEntryDTO = new LogEntryDTO();
        logEntryDTO.setType("DOS");
        logEntryDTO.setCode("ERR");
        logEntryDTO.setMessage("DOS attack from ip " + ip + " address.");
        logEntryDTO.setOccurrenceDate(new Date());
        logEntryDTO.setOccurrenceTime(LocalTime.now());

        return logEntryDTO;
    }
}
