package adminapi.adminaplication.config;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class RestIntercepter implements ClientHttpRequestInterceptor {

    private String jwt;

    public RestIntercepter(String token) {
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

