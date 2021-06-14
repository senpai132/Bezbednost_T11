package com.example.bolnicaServer.service;

import com.example.bolnicaServer.BolnicaServerApplication;
import com.example.bolnicaServer.model.Device;
import com.example.bolnicaServer.model.template.RuleTemplate;
import org.drools.template.ObjectDataCompiler;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceService {


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
