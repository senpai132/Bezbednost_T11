package adminapi.adminaplication.model;


import javax.persistence.*;

@Entity
public class CertificateSignRequest {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String commonName;

    @Column
    private String lastName;

    @Column
    private String firstName;

    @Column
    private String organization;

    @Column
    private String organizationUnit;

    @Column
    private String country;

    @Column
    private String locality;

    @Column
    private String email;

    // status generisemo na back-u
    @Column
    private int status;

    @Column
    private String serialNumber;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] fullCertificate;

    public CertificateSignRequest(String commonName, String lastName, String firstName,
                                  String organization, String organizationUnit, String country, String locality,
                                  String email, int status, String serialNumber, byte[] fullCertificate) {
        this.commonName = commonName;
        this.lastName = lastName;
        this.firstName = firstName;
        this.organization = organization;
        this.organizationUnit = organizationUnit;
        this.country = country;
        this.locality = locality;
        this.email = email;
        this.status = status;
        this.serialNumber = serialNumber;
        this.fullCertificate = fullCertificate;
    }

    public CertificateSignRequest() {
    }

    public Long getId() {
        return id;
    }

    public String getCommonName() {
        return commonName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public byte[] getFullCertificate() {
        return fullCertificate;
    }

    public void setFullCertificate(byte[] fullCertificate) {
        this.fullCertificate = fullCertificate;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getOrganizationUnit() {
        return organizationUnit;
    }

    public void setOrganizationUnit(String organizationUnit) {
        this.organizationUnit = organizationUnit;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
