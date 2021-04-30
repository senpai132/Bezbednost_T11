package adminapi.adminaplication.dto.request;

public class RevocationDTO {
    private String serialNumber;
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
