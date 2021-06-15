package com.bzblogger.logger.controllers;

import com.bzblogger.logger.dto.DeviceDTO;
import com.bzblogger.logger.dto.LogEntryDTO;
import com.bzblogger.logger.dto.UserLoginDTO;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.Date;

@RestController
@CrossOrigin
@RequestMapping("/logger/device")
public class DeviceLoggerController {
    @PostMapping("/alarmed")
    public LogEntryDTO alarmHappened(@RequestBody DeviceDTO dto) {
        LogEntryDTO logEntryDTO = new LogEntryDTO();
        logEntryDTO.setType("DEVICE_ALARM");
        logEntryDTO.setCode(dto.getAlarm());
        logEntryDTO.setMessage("Device with name: " + dto.getName() + " and use function " + dto.getUseFunction() + " fired the "
                + dto.getAlarm() + " alarm. " + "Device value: " + dto.getValue());
        logEntryDTO.setOccurrenceDate(new Date());
        logEntryDTO.setOccurrenceTime(LocalTime.now());

        return logEntryDTO;
    }
}
