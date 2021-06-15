package com.example.bolnicaServer.dto.request;

import javax.validation.constraints.NotBlank;

public class RevocationDTO {
    private String serialNumber;
    @NotBlank(message = "Content can't be blank")
    private String revocationReason;

    public RevocationDTO(String serialNumber, String revocationReason) {
        this.serialNumber = serialNumber;
        this.revocationReason = revocationReason;
    }

    public RevocationDTO(){}

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getRevocationReason() {
        return revocationReason;
    }

    public void setRevocationReason(String revocationReason) {
        this.revocationReason = revocationReason;
    }
}
