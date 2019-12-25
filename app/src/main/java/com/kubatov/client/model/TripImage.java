package com.kubatov.client.model;

public class TripImage {
    private String carImage;
    private String carImage1;
    private String carImage2;

    public TripImage() {
    }

    public TripImage(String carImage, String carImage1, String carImage2) {
        this.carImage = carImage;
        this.carImage1 = carImage1;
        this.carImage2 = carImage2;
    }

    public String getCarImage() {
        return carImage;
    }

    public String getCarImage1() {
        return carImage1;
    }

    public String getCarImage2() {
        return carImage2;
    }
}
