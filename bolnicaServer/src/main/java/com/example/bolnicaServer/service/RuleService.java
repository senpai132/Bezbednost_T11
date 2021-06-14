package com.example.bolnicaServer.service;

import com.example.bolnicaServer.model.Device;
import com.example.bolnicaServer.model.template.RuleTemplate;
import com.example.bolnicaServer.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuleService {

    @Autowired
    private RuleRepository ruleRepository;

    public void generateRule(RuleTemplate ruleTemplate){
        // if min and max value both set but max smaller then min swap
        if(ruleTemplate.getMaxAllowedValue() < ruleTemplate.getMinAllowedValue()
        && ruleTemplate.getMaxAllowedValue() > 0){
            int pom = ruleTemplate.getMaxAllowedValue();
            ruleTemplate.setMaxAllowedValue(ruleTemplate.getMinAllowedValue());
            ruleTemplate.setMinAllowedValue(pom);
        }

        if(ruleTemplate.getMaxAllowedValue() > 0){
            ruleTemplate.setMaxDeviceAlarm(Device.Alarm.OVERVALUE);
        }
        else{
            ruleTemplate.setMaxDeviceAlarm(Device.Alarm.MALFUNCTION);
            ruleTemplate.setMaxAllowedValue(Integer.MAX_VALUE);
        }

        if (ruleTemplate.getMinAllowedValue() > 0){
            ruleTemplate.setMinDeviceAlarm(Device.Alarm.UNDERVALUE);
        }
        else{
            ruleTemplate.setMinDeviceAlarm(Device.Alarm.MALFUNCTION);
        }


        RuleTemplate oldRule = ruleRepository.findByUseFunction(ruleTemplate.getUseFunction());

        if(oldRule != null){
            updateRule(oldRule, ruleTemplate);
            return;
        }
        System.out.println("Novi rule");
        ruleRepository.save(ruleTemplate);
        return;
    }

    private void updateRule(RuleTemplate oldRule, RuleTemplate newRule){
        System.out.println("Update rule");
        oldRule.setUseFunction(newRule.getUseFunction());
        oldRule.setMinAllowedValue(newRule.getMinAllowedValue());
        oldRule.setMaxAllowedValue(newRule.getMaxAllowedValue());
        oldRule.setMinDeviceAlarm(newRule.getMinDeviceAlarm());
        oldRule.setMaxDeviceAlarm(newRule.getMaxDeviceAlarm());
        ruleRepository.save(oldRule);
        return;
    }


}
