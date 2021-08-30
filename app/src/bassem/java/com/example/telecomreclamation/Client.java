package com.example.telecomreclamation;

public class Client {
    String ClientEmail;
    String ClientID;
    String ClientPassword;
    String ClientName;
    private boolean expanded;


    public Client(Object ClientName,Object clientEmail, Object clientID, Object clientPassword) {
        this.ClientName=(String) ClientName;
        this.ClientEmail = (String) clientEmail;
        this.ClientID = (String) clientID;
        this.ClientPassword = (String) clientPassword;
        this.expanded = false;
    }

    public String getClientEmail() {
        return ClientEmail;
    }

    public void setClientEmail(String clientEmail) {
        ClientEmail = clientEmail;
    }

    public String getClientID() {
        return ClientID;
    }

    public void setClientID(String clientID) {
        ClientID = clientID;
    }

    public String getClientPassword() {
        return ClientPassword;
    }

    public void setClientPassword(String clientPassword) {
        ClientPassword = clientPassword;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
