package adminapi.adminaplication.dto.response;

public class CertificateSignRequestDTO {
    private Long id;
    private String commonName;
    private String lastName;
    private String firstName;
    private String organization;
    private String organizationUnit;
    private String country;
    private String email;
    private String locality;

    public CertificateSignRequestDTO(Long id, String commonName, String secondName, String firstName,
                                     String organization, String organizationUnit, String country, String locality, String email) {
        this.id = id;
        this.commonName = commonName;
        this.lastName = secondName;
        this.firstName = firstName;
        this.organization = organization;
        this.organizationUnit = organizationUnit;
        this.country = country;
        this.email = email;
        this.locality = locality;
    }

    public CertificateSignRequestDTO(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
