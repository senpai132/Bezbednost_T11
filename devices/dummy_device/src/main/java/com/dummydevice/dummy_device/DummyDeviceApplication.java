package com.dummydevice.dummy_device;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DummyDeviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DummyDeviceApplication.class, args);
	}

}
