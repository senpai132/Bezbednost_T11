package com.example.bolnicaServer.model.template;


import com.example.bolnicaServer.model.Device;

import javax.persistence.*;

@Entity
public class RuleTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    @Column(unique = true, nullable = false)
    private String useFunction;
    @Column(nullable = false)
    private int minAllowedValue;
    @Column(nullable = false)
    private int maxAllowedValue;
    @Column(nullable = false)
    private Device.Alarm minDeviceAlarm;
    @Column(nullable = false)
    private Device.Alarm maxDeviceAlarm;

    public RuleTemplate(){}

    public RuleTemplate(String useFunction, int minAllowedValue, int maxAllowedValue,
                        Device.Alarm minDeviceAlarm, Device.Alarm maxDeviceAlarm) {
        this.useFunction = useFunction;
        this.minAllowedValue = minAllowedValue;
        this.maxAllowedValue = maxAllowedValue;
        this.minDeviceAlarm = minDeviceAlarm;
        this.maxDeviceAlarm = maxDeviceAlarm;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUseFunction() {
        return useFunction;
    }

    public void setUseFunction(String useFunction) {
        this.useFunction = useFunction;
    }

    public int getMinAllowedValue() {
        return minAllowedValue;
    }

    public void setMinAllowedValue(int minAllowedValue) {
        this.minAllowedValue = minAllowedValue;
    }

    public int getMaxAllowedValue() {
        return maxAllowedValue;
    }

    public void setMaxAllowedValue(int maxAllowedValue) {
        this.maxAllowedValue = maxAllowedValue;
    }

    public Device.Alarm getMinDeviceAlarm() {
        return minDeviceAlarm;
    }

    public void setMinDeviceAlarm(Device.Alarm minDeviceAlarm) {
        this.minDeviceAlarm = minDeviceAlarm;
    }

    public Device.Alarm getMaxDeviceAlarm() {
        return maxDeviceAlarm;
    }

    public void setMaxDeviceAlarm(Device.Alarm maxDeviceAlarm) {
        this.maxDeviceAlarm = maxDeviceAlarm;
    }
}
