package in.nisb.nisbapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.UUID;

public class MessageService extends FirebaseMessagingService {

    public static String eventid="";

    public MessageService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("", "From:" + remoteMessage.getFrom());
        Log.d("", "Message Body:" + remoteMessage.getNotification().getBody());

        sendNotification(remoteMessage.getNotification(),remoteMessage.getData());
    }

    private void sendNotification(RemoteMessage.Notification notification,Map<String,String> data) {
        int color = getResources().getColor(R.color.colorPrimary);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        String context = data.get("context");

        Log.d("CONTEXT",context);
        eventid = data.get("id");

        int requestID = (int) System.currentTimeMillis();

        Intent intent = new Intent(this, SplashActivity.class);
        intent.putExtra("context",context);

        if (context.equals("event-single"))
            intent.putExtra("id",eventid);
        if (context.equals("blog-single"))
            intent.putExtra("url","blog.nisb.in");

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestID, intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.nisblogo)
                .setColor(color)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notification.getBody()))
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }
}
