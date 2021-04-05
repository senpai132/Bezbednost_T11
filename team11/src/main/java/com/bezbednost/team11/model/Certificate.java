package com.bezbednost.team11.model;

import javax.persistence.*;
import java.math.BigInteger;
import java.security.Timestamp;

public class Certificate {

    @Id
    private BigInteger serialNumber;

    @Column
    private String commonName;

    @Column
    private Timestamp startDate;

    @Column
    private Timestamp endDate;

    @Column
    private String certKeyStorePath;

    public Certificate(BigInteger serialNumber, String commonName, Timestamp startDate, Timestamp endDate, String certKeyStorePath) {
        this.serialNumber = serialNumber;
        this.commonName = commonName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.certKeyStorePath = certKeyStorePath;
    }

    public Certificate(){}

    public BigInteger getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(BigInteger serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public String getCertKeyStorePath() {
        return certKeyStorePath;
    }

    public void setCertKeyStorePath(String certKeyStorePath) {
        this.certKeyStorePath = certKeyStorePath;
    }
}
