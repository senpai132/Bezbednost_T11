package com.bzblogger.logger.controllers;

import com.bzblogger.logger.dto.LogEntryDTO;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.Date;

@RestController
@CrossOrigin
@RequestMapping("/logger/attack")
public class XSSLoggerController {

    @PostMapping("/xss")
    public LogEntryDTO xssError(@RequestBody String ip) {
        LogEntryDTO logEntryDTO = new LogEntryDTO();
        logEntryDTO.setType("XSS");
        logEntryDTO.setCode("ERR");
        logEntryDTO.setMessage("XSS attack was made.");
        logEntryDTO.setOccurrenceDate(new Date());
        logEntryDTO.setOccurrenceTime(LocalTime.now());

        return logEntryDTO;
    }
}
