package com.example.bolnicaServer.config;

import com.example.bolnicaServer.service.AlarmAttackService;
import com.example.bolnicaServer.wrapper.XSSRequestWrapper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class XSSFilter implements Filter {

    @Autowired
    private AlarmAttackService service;

    /*@Autowired
    private RestTemplateConfiguration restTemplateConfiguration;*/

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        service.dosAlarm((HttpServletRequest) request);

        XSSRequestWrapper wrappedRequest = new XSSRequestWrapper((HttpServletRequest) request, service);

        String body = IOUtils.toString(wrappedRequest.getReader());
        if (!StringUtils.isBlank(body)) {
            body = XSSRequestWrapper.stripXSS(body, service);
            wrappedRequest.resetInputStream(body.getBytes());
        }

        chain.doFilter(wrappedRequest, response);
    }

}
