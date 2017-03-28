package in.nisb.nisbapp;

import android.content.Intent;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;


public class MessageService extends FirebaseMessagingService {

    public static String eventid="";

    public MessageService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        try {
            JSONObject jo = new JSONObject();
            jo.put("context", remoteMessage.getData().get("context"));
            jo.put("body",remoteMessage.getData().get("body"));

            if (remoteMessage.getData().get("context").equals("event-single")) {
                intent = new Intent(getApplicationContext(), EventSingleActivity.class);
                intent.putExtra("id", remoteMessage.getData().get("id"));
                jo.put("id",remoteMessage.getData().get("id"));
            }


            if (remoteMessage.getData().get("context").equals("blog-single")) {
                intent = new Intent(getApplicationContext(), BlogSingleActivity.class);
                intent.putExtra("content", remoteMessage.getData().get("content"));
                intent.putExtra("title", remoteMessage.getData().get("title"));
                intent.putExtra("extra", remoteMessage.getData().get("extra"));
                jo.put("content",remoteMessage.getData().get("content"));
                jo.put("title",remoteMessage.getData().get("title"));
                jo.put("extra",remoteMessage.getData().get("extra"));
            }

            NisbUser.addNotification(getApplicationContext(),jo.toString());
        }
        catch (JSONException j){

        }
        ExtraFunctions.sendNotification(getApplicationContext(),
                "NISB",
                remoteMessage.getData().get("body"),intent);
    }

}
