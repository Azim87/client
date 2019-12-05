package com.kubatov.client.data.RemoteDriversDataSource;

import com.kubatov.client.data.repository.IClientRepository;

import java.util.Map;

public interface IDriversRemoteData {
    void getDriversTrips(IClientRepository.onClientCallback callback);
    void getClientsInfo(IClientRepository.clientCallback clientCallback);
    void sendChatMessage(Map<String, Object> chat);
    void getChatMessage(IClientRepository.chatCallback chatCallback);
}
