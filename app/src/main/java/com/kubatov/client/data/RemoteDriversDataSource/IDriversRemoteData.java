package com.kubatov.client.data.RemoteDriversDataSource;

import com.kubatov.client.data.repository.IClientRepository;

public interface IDriversRemoteData {

    void getDriversData(IClientRepository.onClientCallback callback);
}
