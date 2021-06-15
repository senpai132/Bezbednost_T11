package com.example.bolnicaServer.service;

import com.example.bolnicaServer.config.RestTemplateConfiguration;
import com.example.bolnicaServer.config.SessionConfig;
import com.example.bolnicaServer.dto.request.DeviceDTO;
import com.example.bolnicaServer.model.LogEntry;
import com.example.bolnicaServer.model.fact.BlockingFact;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Service
public class AlarmDosAttackService {
    @Autowired
    private LogEntryService logEntryService;

    @Autowired
    private SessionConfig sessionConfig;

    @Autowired
    private RestTemplateConfiguration restTemplateConfiguration;

    private String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public void dosAlarm(HttpServletRequest request) throws ServletException{
        //String ip = request.getRequestURL().toString();
        String ip = getClientIpAddr(request);

        KieSession session = sessionConfig.userLoginSession();

        BlockingFact blockingFact = new BlockingFact();
        session.insert(blockingFact);
        session.insert(ip);
        session.getAgenda().getAgendaGroup("Halt system").setFocus();
        session.getAgenda().getAgendaGroup("Block system").setFocus();
        session.getAgenda().getAgendaGroup("SystemLog").setFocus();
        session.fireUntilHalt();
        if(blockingFact.isBlocked()){
            System.out.println("DOS ATACKKKKKKKKK");

            restTemplateConfiguration.setToken("1234567");
            RestTemplate restTemplate = restTemplateConfiguration.getRestTemplate();//new RestTemplate();

            HttpEntity<String> loggerRequest = new HttpEntity<>(ip);

            try {
                ResponseEntity<LogEntry> logEntry = restTemplate.exchange(
                        "https://localhost:8085/logger/attack/dos",
                        HttpMethod.POST,
                        loggerRequest,
                        LogEntry.class);

                logEntryService.insertLog(logEntry.getBody());
            } catch (HttpClientErrorException exception) {
                exception.printStackTrace();
            }


            throw new ServletException("Blocked");
        }

    }
}
