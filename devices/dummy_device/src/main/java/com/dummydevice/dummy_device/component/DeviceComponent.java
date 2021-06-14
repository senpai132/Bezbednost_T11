package com.dummydevice.dummy_device.component;

import com.dummydevice.dummy_device.config.RestTemplateConfiguration;
import com.dummydevice.dummy_device.dto.DeviceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DeviceComponent {
    @Autowired
    private RestTemplateConfiguration restTemplateConfiguration;

    @Scheduled(fixedRate = 10000)
    public void deviceData() {
        System.out.println("Radi");
        restTemplateConfiguration.setToken("tokenjevisak");
        RestTemplate restTemplate = restTemplateConfiguration.getOCSPRestTemplate();

        try {

            //restTemplate.getForObject("https://localhost:8081/dummy/template", Void.class);
            DeviceDTO dto = new DeviceDTO();
            dto.setName("device");
            dto.setValue(-110);
            dto.setUseFunction("Pritisak");
            dto.setPatientId(1021);
            HttpEntity<DeviceDTO> request = new HttpEntity<>(dto);
            HttpStatus httpStatus = restTemplate.exchange(
                    "https://localhost:8081/api/device",
                    HttpMethod.POST,
                    request,
                    DeviceDTO.class).getStatusCode();
            System.out.println(httpStatus);
            return;
        } catch (Exception exception) { //HttpClientErrorException
            //throw new InvalidAPIResponse("Invalid API response.");
            System.out.println("Fail");
            return;
        }

    }

}
