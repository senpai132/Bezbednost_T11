package com.example.bolnicaServer.wrapper;

import com.example.bolnicaServer.service.AlarmAttackService;
import com.google.common.base.Charsets;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.owasp.esapi.ESAPI;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class XSSRequestWrapper extends HttpServletRequestWrapper {

    private byte[] rawData;
    private HttpServletRequest request;
    private ResettableServletInputStream servletStream;
    private AlarmAttackService service;

    public XSSRequestWrapper(HttpServletRequest request, AlarmAttackService service) {
        super(request);
        this.request = request;
        this.servletStream = new ResettableServletInputStream();
        this.service = service;
    }

    public void resetInputStream(byte[] newRawData) {
        rawData = newRawData;
        servletStream.stream = new ByteArrayInputStream(newRawData);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (rawData == null) {
            rawData = IOUtils.toByteArray(this.request.getReader(), Charsets.UTF_8);
            servletStream.stream = new ByteArrayInputStream(rawData);
        }
        return servletStream;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        if (rawData == null) {
            rawData = IOUtils.toByteArray(this.request.getReader(), Charsets.UTF_8);
            servletStream.stream = new ByteArrayInputStream(rawData);
        }
        return new BufferedReader(new InputStreamReader(servletStream));
    }

    private class ResettableServletInputStream extends ServletInputStream {

        private InputStream stream;

        @Override
        public int read() throws IOException {
            return stream.read();
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener readListener) {

        }
    }

    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = stripXSS(values[i], service);
        }
        return encodedValues;
    }

    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        return stripXSS(value, service);
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return stripXSS(value, service);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        List<String> result = new ArrayList<>();
        Enumeration<String> headers = super.getHeaders(name);
        while (headers.hasMoreElements()) {
            String header = headers.nextElement();
            String[] tokens = header.split(",");
            for (String token : tokens) {
                result.add(stripXSS(token, service));
            }
        }
        return Collections.enumeration(result);
    }

    public static String stripXSS(String value, AlarmAttackService service) {
        if (value == null) {
            return null;
        }
        value = ESAPI.encoder()
                .canonicalize(value)
                .replaceAll("\0", "");

        String cleanValue = Jsoup.clean(value, "",Whitelist.none(), new Document.OutputSettings().prettyPrint(false));

        if(!cleanValue.equals(value)){
            service.xssAlarm();
        }

        return cleanValue;//Jsoup.clean(value, Whitelist.none());
    }


}
