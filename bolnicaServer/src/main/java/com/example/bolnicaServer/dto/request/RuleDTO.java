package com.example.bolnicaServer.dto.request;

public class RuleDTO {
    private int minValue;
    private int maxValue;
    private String useFunction;

    public RuleDTO(){}

    public RuleDTO(int minValue, int maxValue, String useFunction) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.useFunction = useFunction;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public String getUseFunction() {
        return useFunction;
    }

    public void setUseFunction(String useFunction) {
        this.useFunction = useFunction;
    }
}
