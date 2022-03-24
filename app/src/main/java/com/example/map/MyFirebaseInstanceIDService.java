package com.example.map;

import android.annotation.SuppressLint;
import android.app.Service;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseInstanceIDService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        getFirebaseMessage(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
    }
    @Override
    public void onNewToken(String token) {
        Log.d("TAG", "Refreshed token: " + token);
    }
    private void getFirebaseMessage(String title, String body) {
//        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"NotificationChannel").setSmallIcon(R.drawable.ic_baseline_notifications_24).setContentTitle(title)
//                .setContentText(body).setAutoCancel(true);
//        NotificationManagerCompat managerCompat=NotificationManagerCompat.from(this);
//        managerCompat.notify(1001,builder.build());
    }
}
