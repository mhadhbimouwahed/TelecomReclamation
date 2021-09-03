package com.example.telecomreclamation;

public class ClientNames {
    String ClientName;
    private boolean expanded;

    public ClientNames(String clientName) {
        ClientName = clientName;
        this.expanded=false;
    }
    public boolean isExpanded(){
        return expanded;
    }
    public void setIsExpanded(boolean expanded){
        this.expanded=expanded;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }
}
