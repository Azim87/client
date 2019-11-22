package com.kubatov.client.data.repository;

import androidx.annotation.Nullable;

import com.kubatov.client.data.RemoteDriversDataSource.DriversRemoteData;

public class ClientRepository implements IClientRepository {
    @Nullable
    private DriversRemoteData mDriversRemoteData;


    public ClientRepository(@Nullable DriversRemoteData driversRemoteData) {
        mDriversRemoteData = driversRemoteData;


    }

    @Override
    public void getDriversInfo(onClientCallback clientCallback) {
        if (mDriversRemoteData != null) {
            mDriversRemoteData.getDriversData();
        }

    }
}
