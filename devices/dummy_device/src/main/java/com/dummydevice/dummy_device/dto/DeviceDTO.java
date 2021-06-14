package com.dummydevice.dummy_device.dto;

public class DeviceDTO {
    private String name;
    private String useFunction;
    private int value;
    private int patientId;

    public  DeviceDTO(){}

    public DeviceDTO(String name, String useFunction, int value, int patientId) {
        this.name = name;
        this.useFunction = useFunction;
        this.value = value;
        this.patientId = patientId;
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

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }
}
