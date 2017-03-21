package in.nisb.nisbapp;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by mridul on 21/3/17.
 */

public class TokenService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String fcmToken = FirebaseInstanceId.getInstance().getToken();

        Log.d("", "FCM Device Token:" + fcmToken);
        //Save or send FCM registration token

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("guest_tokens");
        ref.push().setValue(fcmToken);
    }

}
