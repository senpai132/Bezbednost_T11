package com.dummydevice.dummy_device.component;

import com.dummydevice.dummy_device.config.RestTemplateConfiguration;
import com.dummydevice.dummy_device.dto.DeviceDTO;
import com.dummydevice.dummy_device.model.Authority;
import com.dummydevice.dummy_device.model.User;
import com.dummydevice.dummy_device.security.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class DeviceComponent {
    @Autowired
    private RestTemplateConfiguration restTemplateConfiguration;

    @Autowired
    private TokenUtils tokenUtils;

    private String generateDeviceToken(){
        String jwt = "";
        User user = new User();
        user.setId(1);
        user.setUsername("admin");
        Authority authority = new Authority();
        authority.setId(1L);
        authority.setName("ROLE_ADMIN");
        List<Authority> authoritys = new ArrayList<>();
        authoritys.add(authority);
        user.setAuthorities(authoritys);
        jwt = tokenUtils.generateToken(user);
        return "Bearer " + jwt;
    }

    @Scheduled(fixedRate = 10000)
    public void deviceData() {
        System.out.println("Radi");
        restTemplateConfiguration.setToken(generateDeviceToken());
        //restTemplateConfiguration.setToken("12345678");
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
