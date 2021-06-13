package com.dummydevice.dummy_device.component;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DeviceComponent {

    @Scheduled(fixedRate = 10000)
    public void deviceData() {
        System.out.println("Radi");
    }

}
