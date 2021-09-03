package com.example.telecomreclamation;

public class ClientEmails {

    String email;
    String name;
    String ID;
    String password;

    public ClientEmails(Object email, Object name, Object ID, Object password) {
        this.email =(String) email;
        this.name = (String) name;
        this.ID =(String) ID;
        this.password =  (String) password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
