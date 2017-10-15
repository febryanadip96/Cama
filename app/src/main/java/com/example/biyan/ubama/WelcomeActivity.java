package com.example.biyan.ubama;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {

    private Button btnMasuk;
    private Button btnDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        btnMasuk = (Button) findViewById(R.id.btnMasuk);
        btnDaftar = (Button) findViewById(R.id.btnDaftar);

        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent masukActivity = new Intent(WelcomeActivity.this,LoginActivity.class);
                startActivity(masukActivity);
            }
        });

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent daftarActivity = new Intent(WelcomeActivity.this, RegisterActivity.class);
                startActivity(daftarActivity);
            }
        });
    }
}
