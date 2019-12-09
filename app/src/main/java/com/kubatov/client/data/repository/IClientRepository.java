package com.kubatov.client.data.repository;


import com.kubatov.client.model.ClientUpload;
import com.kubatov.client.model.Trip;
import com.kubatov.client.ui.chat.model.Chat;

import java.util.List;
import java.util.Map;

public interface IClientRepository {

    void getTripsInfo(onClientCallback clientCallback);

    void getClientInfo(clientCallback clientCallback);

    void sendChatMessage(Map<String, Object> chatMap);

    void getChatMessage(chatCallback chatCallback);

    void getTripBookData(Map<String, Object> tripMap);


    interface onClientCallback {
        void onSuccess(List<Trip> tripList);

        void onFailure(Exception e);
    }

    interface clientCallback {
        void onSuccess(ClientUpload clientUploads);

        void onFailure(Exception e);
    }

    interface chatCallback {
        void onSuccess(List<Chat> chatList);

        void onFailure(Exception e);
    }
}
