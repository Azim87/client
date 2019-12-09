package com.kubatov.client.model;

import java.io.Serializable;

public class ClientUpload implements Serializable {
    private String profileImage;

    private String name;

    private String registrationTime;

    private String familyName;

    private String age;

    public ClientUpload(){}

    public ClientUpload(String profileImage, String name, String registrationTime, String familyName, String age) {
        this.profileImage = profileImage;
        this.name = name;
        this.registrationTime = registrationTime;
        this.familyName = familyName;
        this.age = age;
    }

    public String getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(String registrationTime) {
        this.registrationTime = registrationTime;
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

    public String getFamilyName() {
        return familyName;
    }

    public String getAge() {
        return age;
    }
}
