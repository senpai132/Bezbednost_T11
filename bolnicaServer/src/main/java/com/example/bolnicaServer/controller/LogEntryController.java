package com.example.bolnicaServer.controller;

import com.example.bolnicaServer.dto.request.AdminDTO;
import com.example.bolnicaServer.dto.request.LogReportDTO;
import com.example.bolnicaServer.model.Admin;
import com.example.bolnicaServer.model.LogEntry;
import com.example.bolnicaServer.service.LogEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/logger")
public class LogEntryController {
    @Autowired
    LogEntryService logEntryService;

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN') && hasAuthority('ALL_LOGS')")
    public ResponseEntity<List<LogEntry>> getAllLogs() {
        List<LogEntry> logs = logEntryService.getAll();

        return new ResponseEntity<>(logs, HttpStatus.OK);
    }

    @GetMapping("code/{code}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DOCTOR')")
    public ResponseEntity<List<LogEntry>> getLogsByCode(@PathVariable String code) {
        List<LogEntry> logs = logEntryService.getByCode(code);

        return new ResponseEntity<>(logs, HttpStatus.OK);
    }

    @GetMapping(value="type/{type}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DOCTOR')")
    public ResponseEntity<List<LogEntry>> getLogsByType(@PathVariable String type) {
        List<LogEntry> logs = logEntryService.getByType(type);

        return new ResponseEntity<>(logs, HttpStatus.OK);
    }

    @GetMapping(value="date/{date}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DOCTOR')")
    public ResponseEntity<List<LogEntry>> getLogsByType(@PathVariable Date date) {
        List<LogEntry> logs = logEntryService.getByDate(date);

        return new ResponseEntity<>(logs, HttpStatus.OK);
    }

    @PutMapping(value="dateinterval")
    @PreAuthorize("hasRole('ROLE_ADMIN') && hasAuthority('ALL_LOGS')")
    public ResponseEntity<List<LogEntry>> getLogsByType(@Valid @RequestBody LogReportDTO dto) {
        List<LogEntry> logs = logEntryService.getForAPeriod(dto);

        return new ResponseEntity<>(logs, HttpStatus.OK);
    }
}
