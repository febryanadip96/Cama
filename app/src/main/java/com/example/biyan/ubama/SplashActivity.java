package com.example.biyan.ubama;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if(!UserToken.getToken(getApplicationContext()).equals("")){
                    Intent mainIntent =new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
                else{
                    Intent welcomeIntent = new Intent(SplashActivity.this, WelcomeActivity.class);
                    startActivity(welcomeIntent);
                    finish();
                }
            }
        }, 3000);
    }
}
