package com.dummydevice.dummy_device.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

public class User {
    protected Integer id;

    protected String username;

    protected String emailAddress;

//    protected String password;


    //private Timestamp lastPasswordResetDate;

    private List<Authority> authorities;

    public User() {}

    public User(String username, String emailAddress) {
        this.username = username;
        //this.password = password;
        this.emailAddress = emailAddress;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }
}
