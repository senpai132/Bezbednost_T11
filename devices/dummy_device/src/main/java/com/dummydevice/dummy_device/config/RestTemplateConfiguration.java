package com.dummydevice.dummy_device.config;

import com.dummydevice.dummy_device.service.OCSPService;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.security.KeyStore;

@Configuration
public class RestTemplateConfiguration {
    @Autowired
    private DeviceKeyStore keyStore;

    @Autowired
    private OCSPService ocspService;
    @Bean(name = "NoOCSP")
    public RestTemplate getRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        HttpComponentsClientHttpRequestFactory requestFactory = null;
        KeyStore trustStore;
        try{
            trustStore = keyStore.getTruststore();

            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
                    new SSLContextBuilder()
                            .loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
                            .loadKeyMaterial(keyStore.setUpStore(), keyStore.getTRUSTSTORE_PASSWORD().toCharArray()).build(),
                    NoopHostnameVerifier.INSTANCE
            );

            CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory)
                    .setMaxConnTotal(Integer.valueOf(5)).setMaxConnPerRoute(Integer.valueOf(5)).build();

            requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
            requestFactory.setReadTimeout(Integer.valueOf(1000000000));
            requestFactory.setConnectTimeout(Integer.valueOf(1000000000));

            restTemplate.setRequestFactory(requestFactory);
        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            return restTemplate;
        }
        //return restTemplate;
    }

    //@Bean(name = "OCSP")
    public RestTemplate getOCSPRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        HttpComponentsClientHttpRequestFactory requestFactory = null;
        KeyStore trustStore;
        try{
            trustStore = keyStore.getTruststore();

            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
                    new SSLContextBuilder()
                            .loadTrustMaterial(null, new OCSPTrustStrategy(trustStore, ocspService, true))
                            .loadKeyMaterial(keyStore.setUpStore(), keyStore.getTRUSTSTORE_PASSWORD().toCharArray()).build(),
                    NoopHostnameVerifier.INSTANCE
            );

            CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory)
                    .setMaxConnTotal(Integer.valueOf(5)).setMaxConnPerRoute(Integer.valueOf(5)).build();

            requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
            requestFactory.setReadTimeout(Integer.valueOf(1000000000));
            requestFactory.setConnectTimeout(Integer.valueOf(1000000000));

            restTemplate.setRequestFactory(requestFactory);
        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            return restTemplate;
        }
        //return restTemplate;
    }
}
