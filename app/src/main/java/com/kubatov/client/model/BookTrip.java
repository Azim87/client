package com.kubatov.client.model;

public class BookTrip {
    private String driversNumber;
    private int carseats;
    private boolean accept;

    public BookTrip() {
    }

    public BookTrip(String driversNumber, int carSeats, boolean accept) {
        this.driversNumber = driversNumber;
        this.carseats = carSeats;
        this.accept = accept;
    }

    public String getDriversNumber() {
        return driversNumber;
    }

    public int getCarSeats() {
        return carseats;
    }

    public boolean isAccept() {
        return accept;
    }
}
