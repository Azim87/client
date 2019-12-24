package com.kubatov.client.model;

import java.io.Serializable;

public class Trip implements Serializable {
    private String date;
    private String from;
    private String price;
    private String seats;
    private String carSeats;
    private String to;
    private String carImage;
    private String carImage1;
    private String carImage2;
    private String name;
    private String carModel;
    private String carMark;
    private String carYear;
    private String phoneNumber;

    //region Constructor
    public Trip() {
    }

    public Trip(String date, String from, String price, String seats, String carSeats, String to, String carImage,
                String carImage1, String carImage2, String name, String carModel, String carMark,
                String carYear, String phoneNumber) {
        this.date = date;
        this.from = from;
        this.price = price;
        this.seats = seats;
        this.carSeats = carSeats;
        this.to = to;
        this.carImage = carImage;
        this.carImage1 = carImage1;
        this.carImage2 = carImage2;
        this.name = name;
        this.carModel = carModel;
        this.carMark = carMark;
        this.carYear = carYear;
        this.phoneNumber = phoneNumber;
    }
    //endregion
    //region Setters and Getters

    public String getCarSeats() {
        return carSeats;
    }

    public void setCarSeats(String carSeats) {
        this.carSeats = carSeats;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCarMark() {
        return carMark;
    }

    public void setCarMark(String carMark) {
        this.carMark = carMark;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
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

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCarImage() {
        return carImage;
    }

    public void setCarImage(String carImage) {
        this.carImage = carImage;
    }

    public String getCarImage1() {
        return carImage1;
    }

    public void setCarImage1(String carImage1) {
        this.carImage1 = carImage1;
    }

    public String getCarImage2() {
        return carImage2;
    }

    public void setCarImage2(String carImage2) {
        this.carImage2 = carImage2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarYear() {
        return carYear;
    }

    public void setCarYear(String carYear) {
        this.carYear = carYear;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    //endregion
}
