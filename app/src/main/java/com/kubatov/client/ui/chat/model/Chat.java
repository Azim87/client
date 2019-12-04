package com.kubatov.client.ui.chat.model;

public class Chat {

    private String message;
    private String messageFrom;
    private String messageTo;
    private long chatTime;

    public Chat(String message, String messageFrom, String messageTo, long chatTime) {
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

    public long getChatTime() {
        return chatTime;
    }

    public void setChatTime(long chatTime) {
        this.chatTime = chatTime;
    }
}
