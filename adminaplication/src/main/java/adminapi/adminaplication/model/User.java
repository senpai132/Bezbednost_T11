package adminapi.adminaplication.model;


import adminapi.adminaplication.model.enums.UserType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue
    private int id;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private UserType type;

    public User(int id, String email, String password, UserType type) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.type = type;
    }

    public User(String email, String password, UserType type) {
        this.email = email;
        this.password = password;
        this.type = type;
    }

    public User(){}

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }
}
