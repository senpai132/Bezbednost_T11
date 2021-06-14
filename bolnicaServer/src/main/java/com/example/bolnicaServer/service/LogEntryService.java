package com.example.bolnicaServer.service;

import com.example.bolnicaServer.dto.request.LogReportDTO;
import com.example.bolnicaServer.model.LogEntry;
import com.example.bolnicaServer.repository.LogEntryRepository;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<LogEntry> getForAPeriod(LogReportDTO dto) {
        List<LogEntry> entries = logEntryRepository.findAll();
        List<LogEntry> correctList = new ArrayList<>();

        for(LogEntry le : entries) {
            if(le.getOccurrenceDate().after(dto.getStartDate()) && le.getOccurrenceDate().before(dto.getEndDate()))
                correctList.add(le);
        }

        return correctList;
    }
}
