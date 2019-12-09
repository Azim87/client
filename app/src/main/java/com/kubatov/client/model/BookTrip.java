package com.kubatov.client.model;

public class BookTrip {
    private String clientNumber;
    private int carSeats;

    public BookTrip() {
    }

    public BookTrip(String clientNumber, int carSeats) {
        this.clientNumber = clientNumber;
        this.carSeats = carSeats;
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public int getCarSeats() {
        return carSeats;
    }
}
