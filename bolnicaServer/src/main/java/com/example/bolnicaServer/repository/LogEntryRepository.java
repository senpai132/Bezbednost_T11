package com.example.bolnicaServer.repository;

import com.example.bolnicaServer.model.Admin;
import com.example.bolnicaServer.model.LogEntry;
import org.apache.juli.logging.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface LogEntryRepository extends JpaRepository<LogEntry, Integer> {
    public List<LogEntry> findByCode(String code);
    public List<LogEntry> findByType(String type);
    public List<LogEntry> findByOccurrenceDate(Date date);
}
