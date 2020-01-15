package com.kubatov.client;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;

import com.kubatov.client.data.RemoteDriversDataSource.DriversRemoteData;
import com.kubatov.client.data.repository.ClientRepository;
import com.kubatov.client.data.repository.IClientRepository;

import static com.kubatov.client.util.Constants.CHANNEL_ID;

public class App extends Application {
    public static App instance;
    public static IClientRepository clientRepository;
    private static String NAME = "Автобекет";
    private NotificationManager notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        clientRepository = new ClientRepository(new DriversRemoteData());
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            NotificationChannel mChannel = new NotificationChannel(
                    CHANNEL_ID,
                    NAME,
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(mChannel);
            }
        }
    }
}
