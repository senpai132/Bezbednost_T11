package com.example.bolnicaServer.config;

import com.example.bolnicaServer.dto.response.UserTokenStateDTO;
import com.example.bolnicaServer.model.Authority;
import com.example.bolnicaServer.security.TokenUtils;
import com.example.bolnicaServer.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;


public class RestInterceptor implements ClientHttpRequestInterceptor {

    private String jwt;

    public RestInterceptor(String token) {
        this.jwt = token.substring(7);
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes,
                                        ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {

        if (!httpRequest.getHeaders().containsKey("Authorization")) {
            httpRequest.getHeaders().add("Authorization", "Bearer " +
                    this.jwt);
        }

        ClientHttpResponse response = clientHttpRequestExecution.execute(httpRequest, bytes);
        return response;
    }
}
