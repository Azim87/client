package com.kubatov.client;

import android.app.Application;

import com.kubatov.client.data.RemoteDriversDataSource.DriversRemoteData;
import com.kubatov.client.data.repository.ClientRepository;
import com.kubatov.client.data.repository.IClientRepository;

public class App extends Application {

    public static App instance;
    public static IClientRepository clientRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        clientRepository = new ClientRepository(new DriversRemoteData());
    }
}
