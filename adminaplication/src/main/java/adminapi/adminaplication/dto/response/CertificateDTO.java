package adminapi.adminaplication.dto.response;


import java.math.BigInteger;

import java.util.Date;

public class CertificateDTO {
    private BigInteger serialNumber;
    private Date startDate;
    private Date endDate;
    private String subjectName;
    private String issuerName;

    public CertificateDTO(BigInteger serialNumber, Date startDate, Date endDate, String subjectName, String issuerName) {
        this.serialNumber = serialNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.subjectName = subjectName;
        this.issuerName = issuerName;
    }

    public CertificateDTO(){}

    public BigInteger getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(BigInteger serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getIssuerName() {
        return issuerName;
    }

    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }
}
