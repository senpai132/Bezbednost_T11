package com.example.bolnicaServer.model;

public class Device {
    public enum Alarm {
        NO, OVERVALUE, UNDERVALUE, MALFUNCTION
    };

    private String name;
    private String useFunction;
    private int value;
    private Alarm alarm = Alarm.NO;

    public Device(){}

    public Device(String name, String useFunction, int value) {
        this.name = name;
        this.useFunction = useFunction;
        this.value = value;
        this.alarm = Alarm.NO;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUseFunction() {
        return useFunction;
    }

    public void setUseFunction(String useFunction) {
        this.useFunction = useFunction;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Alarm getAlarm() {
        return alarm;
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }
}
