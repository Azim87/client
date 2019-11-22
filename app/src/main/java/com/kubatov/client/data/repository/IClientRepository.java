package com.kubatov.client.data.repository;

public interface IClientRepository {

    void getDriversInfo(onClientCallback clientCallback);

    interface onClientCallback {

        void onSuccess();

        void inFailure(Exception e);
    }
}
