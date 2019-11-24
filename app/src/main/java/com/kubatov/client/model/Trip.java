package com.kubatov.client.model;

public class Trip {

    private String id;
    private String date;
    private String from;
    private String fromIndex;
    private String index;
    private String price;
    private String seats;
    private String seatsIndex;
    private String to;
    private String toIndex;

    public Trip() {

    }

    public Trip(String id, String date, String from, String fromIndex, String index, String price, String seats, String seatsIndex, String to, String toIndex) {
        this.id = id;
        this.date = date;
        this.from = from;
        this.fromIndex = fromIndex;
        this.index = index;
        this.price = price;
        this.seats = seats;
        this.seatsIndex = seatsIndex;
        this.to = to;
        this.toIndex = toIndex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromIndex() {
        return fromIndex;
    }

    public void setFromIndex(String fromIndex) {
        this.fromIndex = fromIndex;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getSeatsIndex() {
        return seatsIndex;
    }

    public void setSeatsIndex(String seatsIndex) {
        this.seatsIndex = seatsIndex;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getToIndex() {
        return toIndex;
    }

    public void setToIndex(String toIndex) {
        this.toIndex = toIndex;
    }
}
