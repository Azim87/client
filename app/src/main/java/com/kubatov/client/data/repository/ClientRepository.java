package com.kubatov.client.data.repository;

import androidx.annotation.Nullable;

import com.kubatov.client.data.RemoteDriversDataSource.DriversRemoteData;
import com.kubatov.client.model.ClientUpload;
import com.kubatov.client.model.Trip;
import com.kubatov.client.ui.chat.model.Chat;

import java.util.List;
import java.util.Map;

public class ClientRepository implements IClientRepository {
    @Nullable
    private DriversRemoteData mDriversRemoteData;

    public ClientRepository(@Nullable DriversRemoteData driversRemoteData) {
        mDriversRemoteData = driversRemoteData;
    }

    @Override
    public void getTripsInfo(onClientCallback clientCallback) {
        if (mDriversRemoteData != null) {
            mDriversRemoteData.getDriversTrips(new onClientCallback() {
                @Override
                public void onSuccess(List<Trip> tripList) {
                    clientCallback.onSuccess(tripList);
                }

                @Override
                public void onFailure(Exception e) {
                    clientCallback.onFailure(new Exception("error"));
                }
            });
        }
    }

    @Override
    public void getClientInfo(clientCallback clientCallback) {
        mDriversRemoteData.getClientsInfo(new clientCallback() {
            @Override
            public void onSuccess(ClientUpload clientUploads) {
                clientCallback.onSuccess(clientUploads);
            }

            @Override
            public void onFailure(Exception e) {
                clientCallback.onFailure(new Exception("error"));
            }
        });
    }

    @Override
    public void sendChatMessage(Map<String, Object> chatMap) {
        mDriversRemoteData.sendChatMessage(chatMap);
    }

    @Override
    public void getChatMessage(chatCallback chatCallback) {
        mDriversRemoteData.getChatMessage(new chatCallback() {
            @Override
            public void onSuccess(List<Chat> chatList) {
                chatCallback.onSuccess(chatList);
            }

            @Override
            public void onFailure(Exception e) {
                chatCallback.onFailure(e);
            }
        });
    }

    @Override
    public void getTripBookData(Map<String, Object> tripMap) {
        mDriversRemoteData.getTripBookData(tripMap);
    }
}
