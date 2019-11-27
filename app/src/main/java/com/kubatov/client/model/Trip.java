package com.kubatov.client.model;

public class Trip {
    private String date;
    private String from;
    private String price;
    private String seats;
    private String to;
    private String carImage;
    private String carImage2;
    private String carImage3;
    private String name;
    private String carModel;
    private String carMark;
    private String carYear;
    private String rating;
    private boolean homeTohome;

    //region Constructor
    public Trip() {
    }

    public Trip(String date, String from, String price, String seats, String to, String carImage,
                String carImage2, String carImage3, String name, String carModel,  String carMark,
                String carYear, String rating, boolean homeTohome) {
        this.date = date;
        this.from = from;
        this.price = price;
        this.seats = seats;
        this.to = to;
        this.carImage = carImage;
        this.carImage2 = carImage2;
        this.carImage3 = carImage3;
        this.name = name;
        this.carModel = carModel;
        this.carMark = carMark;
        this.carYear = carYear;
        this.rating = rating;
        this.homeTohome = homeTohome;
    }
    //endregion

    //region Setters and Getters

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

    public String getCarImage2() {
        return carImage2;
    }

    public void setCarImage2(String carImage2) {
        this.carImage2 = carImage2;
    }

    public String getCarImage3() {
        return carImage3;
    }

    public void setCarImage3(String carImage3) {
        this.carImage3 = carImage3;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public boolean isHomeTohome() {
        return homeTohome;
    }

    public void setHomeTohome(boolean homeTohome) {
        this.homeTohome = homeTohome;
    }

    //endregion

}
