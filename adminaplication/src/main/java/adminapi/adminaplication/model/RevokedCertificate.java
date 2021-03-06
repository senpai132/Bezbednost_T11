package adminapi.adminaplication.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class RevokedCertificate {
    @Id
    @GeneratedValue
    private int id;

    @Column
    private String serialNumber;

    @Column
    private Date revocationDate;

    @Column
    private String revocationReason;


    public RevokedCertificate(int id, String serialNumber, Date revocationDate, String revocationReason) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.revocationDate = revocationDate;
        this.revocationReason = revocationReason;
    }

    public RevokedCertificate(String serialNumber, Date revocationDate, String revocationReason) {
        this.serialNumber = serialNumber;
        this.revocationDate = revocationDate;
        this.revocationReason = revocationReason;
    }

    public RevokedCertificate(){}

    public int getId() {
        return id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Date getRevocationDate() {
        return revocationDate;
    }

    public void setRevocationDate(Date revocationDate) {
        this.revocationDate = revocationDate;
    }

    public String getRevocationReason() {
        return revocationReason;
    }

    public void setRevocationReason(String revocationReason) {
        this.revocationReason = revocationReason;
    }
}
