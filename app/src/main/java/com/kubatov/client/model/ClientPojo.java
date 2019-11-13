package com.kubatov.client.model;

public class ClientPojo {
    private String clientImageUrl;
    private String sex;
    private String age;
    private String name;

    public ClientPojo() {
    }

    public ClientPojo(String clientImageUrl, String sex, String age, String name) {
        this.clientImageUrl = clientImageUrl;
        this.sex = sex;
        this.age = age;
        this.name = name;
    }

    public String getClientImageUrl() {
        return clientImageUrl;
    }

    public String getSex() {
        return sex;
    }

    public String getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public void setClientImageUrl(String clientImageUrl) {
        this.clientImageUrl = clientImageUrl;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }
}
