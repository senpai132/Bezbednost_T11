package com.example.bolnicaServer.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class CertificateSignRequestDTO {
    private Long id;
    @NotBlank(message = "Content can't be blank")
    private String commonName;
    @NotBlank(message = "Content can't be blank")
    private String lastName;
    @NotBlank(message = "Content can't be blank")
    private String firstName;
    @NotBlank(message = "Content can't be blank")
    private String organization;
    @NotBlank(message = "Content can't be blank")
    private String organizationUnit;
    @NotBlank(message = "Content can't be blank")
    private String country;
    @Email(message = "Email is not valid", regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
    @NotBlank(message = "Content can't be blank")
    private String email;
    @NotBlank(message = "Content can't be blank")
    private String locality;
    private String serialNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
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

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }
}
