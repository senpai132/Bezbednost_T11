package com.example.bolnicaServer.service;

import com.example.bolnicaServer.model.LogEntry;
import com.example.bolnicaServer.repository.LogEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LogEntryService {

    @Autowired
    LogEntryRepository logEntryRepository;

    public List<LogEntry> getAll() {
        return logEntryRepository.findAll();
    }

    public List<LogEntry> getByType(String type) {
        return logEntryRepository.findByType(type);
    }

    public List<LogEntry> getByDate(Date date) {
        return logEntryRepository.findByOccurrenceDate(date);
    }

    public List<LogEntry> getByCode(String code) {
        return logEntryRepository.findByCode(code);
    }

    public void insertLog(LogEntry entry) {
        logEntryRepository.save(entry);
    }
}
