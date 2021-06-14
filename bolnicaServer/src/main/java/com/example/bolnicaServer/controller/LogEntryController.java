package com.example.bolnicaServer.controller;

import com.example.bolnicaServer.dto.request.AdminDTO;
import com.example.bolnicaServer.model.Admin;
import com.example.bolnicaServer.model.LogEntry;
import com.example.bolnicaServer.service.LogEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/logger")
public class LogEntryController {
    @Autowired
    LogEntryService logEntryService;

    @GetMapping()
    public ResponseEntity<List<LogEntry>> getAllLogs() {
        List<LogEntry> logs = logEntryService.getAll();

        return new ResponseEntity<>(logs, HttpStatus.OK);
    }

    @GetMapping("code/{code}")
    public ResponseEntity<List<LogEntry>> getLogsByCode(@PathVariable String code) {
        List<LogEntry> logs = logEntryService.getByCode(code);

        return new ResponseEntity<>(logs, HttpStatus.OK);
    }

    @GetMapping(value="type/{type}")
    public ResponseEntity<List<LogEntry>> getLogsByType(@PathVariable String type) {
        List<LogEntry> logs = logEntryService.getByType(type);

        return new ResponseEntity<>(logs, HttpStatus.OK);
    }

    @GetMapping(value="date/{date}")
    public ResponseEntity<List<LogEntry>> getLogsByType(@PathVariable Date date) {
        List<LogEntry> logs = logEntryService.getByDate(date);

        return new ResponseEntity<>(logs, HttpStatus.OK);
    }
}
