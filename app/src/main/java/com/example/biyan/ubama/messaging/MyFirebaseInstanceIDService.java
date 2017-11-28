package com.example.biyan.ubama.messaging;

import android.util.Log;

import com.example.biyan.ubama.UserToken;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Biyan on 11/25/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    final static String TAG = "FIREBASE";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        if(!UserToken.getToken(this).equals("")){
            SendToken.sendRegistrationToServer(this, refreshedToken);
        }
    }
}
