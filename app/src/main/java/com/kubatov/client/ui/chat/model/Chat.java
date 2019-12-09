package com.kubatov.client.ui.chat.model;

import java.io.Serializable;

public class Chat implements Serializable {

    private String message;
    private String messageFrom;
    private String messageTo;
    private Long chatTime;

    public Chat(){}

    public Chat(String message, String messageFrom, String messageTo, Long chatTime) {
        this.message = message;
        this.messageFrom = messageFrom;
        this.messageTo = messageTo;
        this.chatTime = chatTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageFrom() {
        return messageFrom;
    }

    public void setMessageFrom(String messageFrom) {
        this.messageFrom = messageFrom;
    }

    public String getMessageTo() {
        return messageTo;
    }

    public void setMessageTo(String messageTo) {
        this.messageTo = messageTo;
    }

    public Long getChatTime() {
        return chatTime;
    }

    public void setChatTime(Long chatTime) {
        this.chatTime = chatTime;
    }
}
