package com.example.bolnicaServer.service;

import com.example.bolnicaServer.BolnicaServerApplication;
import com.example.bolnicaServer.config.RestTemplateConfiguration;
import com.example.bolnicaServer.dto.request.DeviceDTO;
import com.example.bolnicaServer.dto.request.UserLoginDTO;
import com.example.bolnicaServer.model.Device;
import com.example.bolnicaServer.model.LogEntry;
import com.example.bolnicaServer.model.Patient;
import com.example.bolnicaServer.model.PatientMessage;
import com.example.bolnicaServer.model.template.RuleTemplate;
import com.example.bolnicaServer.repository.PatientMessageRepository;
import com.example.bolnicaServer.repository.PatientRepository;
import com.example.bolnicaServer.repository.RuleRepository;
import org.drools.template.ObjectDataCompiler;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceService {

    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    private LogEntryService logEntryService;

    @Autowired
    private RestTemplateConfiguration restTemplateConfiguration;

    @Autowired
    private PatientMessageRepository patientMessageRepository;
    @Autowired
    private PatientRepository patientRepository;

    public void dummy(){
        //Device device;

        InputStream template = BolnicaServerApplication.class.getResourceAsStream("/device-template/DeviceAlarm.drt");

        List<RuleTemplate> data = new ArrayList<RuleTemplate>();

        data.add(new RuleTemplate("pritisak", 80, 120, Device.Alarm.UNDERVALUE, Device.Alarm.OVERVALUE));
        data.add(new RuleTemplate("puls", 0, 30, Device.Alarm.MALFUNCTION, Device.Alarm.OVERVALUE));

        ObjectDataCompiler converter = new ObjectDataCompiler();
        String drl = converter.compile(data, template);

        System.out.println(drl);

        KieSession ksession = createKieSessionFromDRL(drl);

        doTest(ksession);
    }

    private void doTest(KieSession ksession){
        Device device1 = new Device("nov device", "pritisak", 3);

        ksession.insert(device1);

        ksession.fireAllRules();

        System.out.println(device1.getAlarm());

    }

    public void deviceMessage(Device device, int patientId){
        InputStream template = BolnicaServerApplication.class.getResourceAsStream("/device-template/DeviceAlarm.drt");
        List<RuleTemplate> data = ruleRepository.findAll();

        ObjectDataCompiler converter = new ObjectDataCompiler();
        String drl = converter.compile(data, template);

        System.out.println(drl);

        KieSession ksession = createKieSessionFromDRL(drl);
        ksession.insert(device);
        ksession.fireAllRules();

        System.out.println(device.getAlarm());
        Patient patient = patientRepository.findById(patientId);
        if(patient != null) {
            PatientMessage message = new PatientMessage();
            message.setPatientId(patientId);
            message.setMessage("Patient " + patient.getFirstName() + " " + patient.getLastName()
                    + " reading from device " + device.getName()
                    + " with use function " + device.getUseFunction() +" was " + device.getValue());
            patientMessageRepository.save(message);
        }

        if(device.getAlarm() != Device.Alarm.NO) {
            DeviceDTO devDTO = new DeviceDTO();
            devDTO.setName(device.getName());
            devDTO.setUseFunction(device.getUseFunction());
            devDTO.setValue(device.getValue());
            devDTO.setAlarm(device.getAlarm().toString());

            restTemplateConfiguration.setToken("1234567");
            RestTemplate restTemplate = restTemplateConfiguration.getRestTemplate();//new RestTemplate();

            HttpEntity<DeviceDTO> loggerRequest = new HttpEntity<>(devDTO);

            try {
                ResponseEntity<LogEntry> logEntry = restTemplate.exchange(
                        "https://localhost:8085/logger/device/alarmed",
                        HttpMethod.POST,
                        loggerRequest,
                        LogEntry.class);

                logEntryService.insertLog(logEntry.getBody());
            } catch (HttpClientErrorException exception) {
                exception.printStackTrace();
                //throw new InvalidAPIResponse("Invalid API response.");
            }
        }
    }

    private KieSession createKieSessionFromDRL(String drl){
        KieHelper kieHelper = new KieHelper();
        kieHelper.addContent(drl, ResourceType.DRL);

        Results results = kieHelper.verify();

        if (results.hasMessages(Message.Level.WARNING, Message.Level.ERROR)){
            List<Message> messages = results.getMessages(Message.Level.WARNING, Message.Level.ERROR);
            for (Message message : messages) {
                System.out.println("Error: "+message.getText());
            }

            throw new IllegalStateException("Compilation errors were found. Check the logs.");
        }

        return kieHelper.build().newKieSession();
    }
}
