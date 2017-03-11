package com.example.mridul.nisb;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by mridul on 6/3/17.
 */

public class MessageService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Intent i = new Intent(this,MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent p =  PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notify = new NotificationCompat.Builder(this);
        notify.setContentTitle(remoteMessage.getNotification().getTitle());
        notify.setContentText(remoteMessage.getNotification().getBody());
        notify.setAutoCancel(true);
        notify.setSmallIcon(R.drawable.nisb);

        Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                R.drawable.nisb);

        notify.setLargeIcon(icon);

        notify.setContentIntent(p);

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(0,notify.build());
    }
}
