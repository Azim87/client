package com.kubatov.client.model;

import java.io.Serializable;

public class ClientUpload implements Serializable {
    private String profileImage;

    private String name;

    public ClientUpload(){}

    public ClientUpload(String clientImageUrl, String name) {
        this.profileImage = profileImage;
        this.name = name;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImage(){
        return profileImage;
    }

    public String getName() {
        return name;
    }
}
