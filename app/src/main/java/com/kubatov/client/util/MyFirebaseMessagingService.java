package com.kubatov.client.util;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.kubatov.client.R;
import com.kubatov.client.ui.chat.ChatActivity;

import static com.kubatov.client.util.Constants.CHANNEL_ID;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private NotificationManagerCompat notificationManager;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        sendNotification(remoteMessage.getNotification().getBody());
        Log.d("ololo", "onMessageReceived: " + remoteMessage.getNotification().getBody() );
    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            NotificationCompat.Builder notification = new NotificationCompat.Builder(this, CHANNEL_ID);
            notification.setSmallIcon(R.drawable.ic_email_black_24dp)
                    .setContentTitle("Автобекет")
                    .setAutoCancel(true)
                    .setVibrate(new long[] { 100, 100, 100, 100, 100})
                    .setContentIntent(pendingIntent)
                    .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_SUMMARY)
                    .setContentText(messageBody);

            notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(0, notification.build());
        }
        /*if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(
                    "channel_id",
                    "channel_name",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("channel description");
            channel.setShowBadge(true);
            channel.canShowBadge();
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});
            notificationManager.createNotificationChannel(channel);
        }*/
    }

}
