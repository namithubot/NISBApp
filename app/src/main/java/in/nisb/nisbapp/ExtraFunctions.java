package in.nisb.nisbapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.RemoteMessage;
import com.squareup.picasso.Picasso;

import java.util.Map;
import java.util.UUID;

import android.net.ConnectivityManager;
import android.widget.Toast;


/**
 * Created by mridul on 23/3/17.
 */

public class ExtraFunctions {

    public static void setSBColor(Window w,int c){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.setStatusBarColor(c);
        }
    }

    public static String fbUrlGenerator(String path,String fields[]){
        return "https://graph.facebook.com/v2.8/" + path + "?fields="+  strJoin("%2C",fields) +
               "&access_token=1327383467301154%7CYDfQ94wTelbffydG5XrnanHnqu0";
    }

    public static String strJoin(String s, String[] fields) {
        String val="";
        for (String a : fields){
            val+=a + "%2C";
        }
        return val.substring(0,val.length()-s.length());
    }

    public static void sendNotification(Context mcontext,String title,String message,Intent intent) {
        int color = Color.RED;
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        int requestID = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getActivity(mcontext, requestID, intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mcontext)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.nisblogo)
                .setColor(color)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) mcontext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }

    public static boolean isConnected(Context c){
        // get Connectivity Manager object to check connection
        ConnectivityManager connec =  
                       (ConnectivityManager) c.getSystemService(c.CONNECTIVITY_SERVICE);

        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                 connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                 connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                 connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
                
                // if connected with internet
                 
                //Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
                return true;
                 
            } else if ( 
              connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
              connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
               
                Toast.makeText(c, "No Internet.", Toast.LENGTH_LONG).show();
                return false;
            }
          return false;
        }

}
