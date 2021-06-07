package com.example.bolnicaServer.service;

import com.example.bolnicaServer.config.HospitalKeyStore;
import com.example.bolnicaServer.config.RestTemplateConfiguration;
import com.example.bolnicaServer.dto.request.RevocationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class CertificateRevocationService {

    @Autowired
    private RestTemplateConfiguration restTemplateConfiguration;

    @Autowired
    private HospitalKeyStore hospitalKeyStore;

    public void revoke(RevocationDTO dto, String token) throws Exception{
        String alias = hospitalKeyStore.getAlias();
        String requestUrl = "https://localhost:8080/api/certificate";
        dto.setSerialNumber(alias);
        //token = token.substring(7);
        restTemplateConfiguration.setToken(token);
        RestTemplate restTemplate = restTemplateConfiguration.getRestTemplate();

        HttpEntity<RevocationDTO> request = new HttpEntity<>(dto);

        try {
            /*restTemplate.exchange(requestUrl,
                    HttpMethod.POST,
                    dto,
                    RevocationDTO.class);*/
            HttpStatus httpStatus = restTemplate.exchange(
                    requestUrl,
                    HttpMethod.PUT,
                    request,
                    RevocationDTO.class).getStatusCode();
        } catch (HttpClientErrorException exception) {
            exception.printStackTrace();
            //throw new InvalidAPIResponse("Invalid API response.");
        }
    }
}
