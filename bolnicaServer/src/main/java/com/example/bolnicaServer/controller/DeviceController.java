package com.example.bolnicaServer.controller;

import com.example.bolnicaServer.dto.request.DeviceDTO;
import com.example.bolnicaServer.dto.request.RuleDTO;
import com.example.bolnicaServer.model.Device;
import com.example.bolnicaServer.model.template.RuleTemplate;
import com.example.bolnicaServer.service.DeviceService;
import com.example.bolnicaServer.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/device")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DOCTOR')")
    @PostMapping
    public ResponseEntity<?> saveDeviceMessage(@RequestBody DeviceDTO dto) {

        Device device = new Device();
        device.setName(dto.getName());
        device.setUseFunction(dto.getUseFunction());
        device.setValue(dto.getValue());
        device.setAlarm(Device.Alarm.NO);

        deviceService.deviceMessage(device, dto.getPatientId());
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
