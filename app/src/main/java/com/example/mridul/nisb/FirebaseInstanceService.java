package com.example.mridul.nisb;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by mridul on 6/3/17.
 */

public class FirebaseInstanceService extends FirebaseInstanceIdService{

    @Override
    public void onTokenRefresh() {

        String token = FirebaseInstanceId.getInstance().getToken();
        System.out.println("IID Token =" + token);
        Log.d("TOKEN",token);
    }
}
