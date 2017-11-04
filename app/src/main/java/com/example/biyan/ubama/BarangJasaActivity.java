package com.example.biyan.ubama;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class BarangJasaActivity extends AppCompatActivity {

    private int idBarangJasa;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang_jasa);
        Intent intent = getIntent();
        setTitle(intent.getStringExtra("namaBarangJasa"));
        idBarangJasa = intent.getIntExtra("idBarangJasa",0);
        getDetailBarangJasa();
    }

    private void getDetailBarangJasa(){
        queue = Volley.newRequestQueue(this);
        String url = UrlUbama.BarangJasa+idBarangJasa;
        Toast.makeText(this, url, Toast.LENGTH_LONG).show();
    }
}
