package com.example.biyan.ubama.messaging;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.biyan.ubama.R;
import com.example.biyan.ubama.user.TanyaJawabUserActivity;
import com.example.biyan.ubama.produk.KomentarActivity;
import com.example.biyan.ubama.pesanan.DetailPesananActivity;
import com.example.biyan.ubama.toko.TokoDetailPesananActivity;
import com.example.biyan.ubama.toko.TokoJawabPertanyaanActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Biyan on 11/25/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    final static String TAG = "FirebaseMessage";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getData().toString());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        int type = Integer.parseInt(remoteMessage.getData().get("Type").toString());
        int id = Integer.parseInt(remoteMessage.getData().get("Id").toString());

        Intent resultIntent = getIntentByType(type,id);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setLights(Color.GREEN, 1000, 1000)
                        .setContentTitle( remoteMessage.getData().get("Title").toString())
                        .setContentText(remoteMessage.getData().get("Content").toString())
                        .setContentIntent(resultPendingIntent)
                        .setAutoCancel(true);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,mBuilder.build());
    }

    public Intent getIntentByType(int type, int id){
        Intent intent = new Intent();
        switch (type){
            case 1:
                intent = new Intent(getApplicationContext(), TokoDetailPesananActivity.class);
                intent.putExtra("idPesanan", id);
                break;
            case 2:
                intent = new Intent(getApplicationContext(), TokoJawabPertanyaanActivity.class);
                intent.putExtra("idTanyaJawab", id);
                break;
            case 3:
                intent = new Intent(getApplicationContext(), DetailPesananActivity.class);
                intent.putExtra("idPesanan", id);
                break;
            case 4:
                intent = new Intent(getApplicationContext(), KomentarActivity.class);
                intent.putExtra("idBarangJasa", id);
                break;
            case 5:
                intent = new Intent(getApplicationContext(), TanyaJawabUserActivity.class);
                intent.putExtra("idTanyaJawab", id);
                break;
        }
        return intent;
    }
}
