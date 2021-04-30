package adminapi.adminaplication.dto.response;

import java.util.Date;

public class RevokedCertificateDTO {
    private int id;
    private String serialNumber;
    private String revocationDate;
    private String revocationReason;

    public RevokedCertificateDTO(int id, String serialNumber, String revocationDate, String revocationReason) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.revocationDate = revocationDate;
        this.revocationReason = revocationReason;
    }

    public RevokedCertificateDTO(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getRevocationDate() {
        return revocationDate;
    }

    public void setRevocationDate(String revocationDate) {
        this.revocationDate = revocationDate;
    }

    public String getRevocationReason() {
        return revocationReason;
    }

    public void setRevocationReason(String revocationReason) {
        this.revocationReason = revocationReason;
    }
}
