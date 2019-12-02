package com.kubatov.client.model;

public class ClientUpload {
    private String profileImage;

    private String name;

    public ClientUpload(){}

    public ClientUpload(String clientImageUrl, String name) {
        this.profileImage = profileImage;
        this.name = name;
    }

    public String getProfileImage(){
        return profileImage;
    }

    public String getName() {
        return name;
    }
}
