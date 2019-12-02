package com.kubatov.client.data.repository;


import com.kubatov.client.model.ClientUpload;
import com.kubatov.client.model.Trip;

import java.util.List;

public interface IClientRepository {

    void getTripsInfo(onClientCallback clientCallback);
    void getClientInfo(clientCallback clientCallback);

    interface onClientCallback {

        void onSuccess(List<Trip> tripList);

        void onFailure(Exception e);
    }

    interface clientCallback {

        void onSuccess(List<ClientUpload> clientUploads);

        void onFailure(Exception e);
    }
}
