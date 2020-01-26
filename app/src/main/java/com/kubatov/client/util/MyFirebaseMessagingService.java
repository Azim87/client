package com.kubatov.client.util;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.kubatov.client.R;
import com.kubatov.client.ui.chat.ChatActivity;
import com.kubatov.client.ui.main.MainActivity;

import static com.kubatov.client.util.Constants.CHANNEL_ID;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private NotificationManagerCompat notificationManager;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        sendNotification(remoteMessage.getNotification().getBody(),
                remoteMessage.getNotification().getTitle());
    }

    private void sendNotification(String messageBody, String title) {
        Intent intent = new Intent(this, MainActivity  .class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            NotificationCompat.Builder notification = new NotificationCompat.Builder(this, CHANNEL_ID);
            notification.setSmallIcon(R.drawable.ic_email_black_24dp)
                    .setContentTitle(title)
                    .setAutoCancel(true)
                    .setVibrate(new long[] { 100, 100, 100, 100, 100})
                    .setContentIntent(pendingIntent)
                    .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_SUMMARY)
                    .setContentText(messageBody);

            notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(0, notification.build());
        }
    }
}
