package com.example.bolnicaServer.controller;

import com.example.bolnicaServer.config.RestTemplateConfiguration;
import com.example.bolnicaServer.config.SessionConfig;
import com.example.bolnicaServer.dto.request.UserLoginDTO;
import com.example.bolnicaServer.dto.response.UserTokenStateDTO;
import com.example.bolnicaServer.model.LogEntry;
import com.example.bolnicaServer.model.User;
import com.example.bolnicaServer.model.fact.BlockingFact;
import com.example.bolnicaServer.security.TokenUtils;
import com.example.bolnicaServer.service.LogEntryService;
import com.example.bolnicaServer.service.UserDetailsServiceImpl;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    private SessionConfig sessionConfig;

    @Autowired
    private RestTemplateConfiguration restTemplateConfiguration;

    @Autowired
    private LogEntryService logEntryService;


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


    @PostMapping("/login")
    public ResponseEntity<UserTokenStateDTO> createAuthenticationToken(@RequestBody UserLoginDTO authenticationRequest,
                                                                       HttpServletResponse response, HttpServletRequest request) {

        KieSession session = sessionConfig.userLoginSession();
        String ip = getClientIpAddr(request);
        restTemplateConfiguration.setToken("1234567");
        RestTemplate restTemplate = restTemplateConfiguration.getRestTemplate();//new RestTemplate();
        HttpEntity<UserLoginDTO> loggerRequest = new HttpEntity<>(authenticationRequest);
        //String realIp = ip;
        try {
            BlockingFact blockingFact = new BlockingFact();
            session.insert(blockingFact);
            session.insert(ip);
            session.getAgenda().getAgendaGroup("Halt").setFocus();
            session.getAgenda().getAgendaGroup("Block").setFocus();
            session.fireUntilHalt();
            if(blockingFact.isBlocked() ){
                //#TODO blocked log
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = (User) authentication.getPrincipal();
            String jwt = tokenUtils.generateToken(user);
            int expiresIn = tokenUtils.getExpiredIn();

            try {
                ResponseEntity<LogEntry> logEntry = restTemplate.exchange(
                        "https://localhost:8085/logger/auth/ok",
                        HttpMethod.POST,
                        loggerRequest,
                        LogEntry.class);

                logEntryService.insertLog(logEntry.getBody());
            } catch (HttpClientErrorException exception) {
                exception.printStackTrace();
                //throw new InvalidAPIResponse("Invalid API response.");
            }

            return ResponseEntity.ok(new UserTokenStateDTO(jwt, expiresIn));
        } catch (Exception ex) {

            session.insert(ip);
            session.getAgenda().getAgendaGroup("Halt").setFocus();

            session.getAgenda().getAgendaGroup("FailLog").setFocus();
            session.fireUntilHalt();

            try {
                ResponseEntity<LogEntry> logEntry = restTemplate.exchange(
                        "https://localhost:8085/logger/auth/err",
                        HttpMethod.POST,
                        loggerRequest,
                        LogEntry.class);

                logEntryService.insertLog(logEntry.getBody());
            } catch (HttpClientErrorException exception) {
                exception.printStackTrace();
                //throw new InvalidAPIResponse("Invalid API response.");
            }

            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<UserTokenStateDTO> refreshAuthenticationToken(HttpServletRequest request) {

        String token = tokenUtils.getToken(request);
        Integer id = this.tokenUtils.getUserIdFromToken(token);
        if (id != null) {
            User user = (User) this.userDetailsService.loadUserById(id);

            if (this.tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
                String refreshedToken = tokenUtils.refreshToken(token, user.getUsername());
                int expiresIn = tokenUtils.getExpiredIn();

                return ResponseEntity.ok(new UserTokenStateDTO(refreshedToken, expiresIn));
            }
        }

        UserTokenStateDTO userTokenState = new UserTokenStateDTO();
        return ResponseEntity.badRequest().body(userTokenState);
    }

    public AuthController() {

    }
}
