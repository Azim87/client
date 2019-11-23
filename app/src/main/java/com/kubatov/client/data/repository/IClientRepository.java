package com.kubatov.client.data.repository;


import com.kubatov.client.model.Trip;

import java.util.List;

public interface IClientRepository {

    void getTripsInfo(onClientCallback clientCallback);

    interface onClientCallback {

        void onSuccess(List<Trip> tripList);

        void onFailure(Exception e);
    }
}
