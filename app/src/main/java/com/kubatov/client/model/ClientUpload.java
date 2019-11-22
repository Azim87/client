package com.kubatov.client.model;

public class ClientUpload {
    private String clientImageUrl;

    private String name;



    public ClientUpload(String clientImageUrl, String name) {
        this.clientImageUrl = clientImageUrl;
        this.name = name;
    }

    public String getClientImageUrl() {
        return clientImageUrl;
    }

    public String getName() {
        return name;
    }

    public void setClientImageUrl(String clientImageUrl) {
        this.clientImageUrl = clientImageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }
}
