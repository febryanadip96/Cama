package com.example.biyan.ubama;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BarangJasaActivity extends AppCompatActivity {

    private int idBarangJasa;
    BarangJasa barangJasa;
    CarouselView carouselGambar;
    TextView namaBarang;
    TextView hargaBarang;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang_jasa);
        Intent intent = getIntent();
        setTitle(intent.getStringExtra("namaBarangJasa"));
        idBarangJasa = intent.getIntExtra("idBarangJasa",0);
        barangJasa = new BarangJasa();
        carouselGambar = (CarouselView) findViewById(R.id.carousel_gambar);
        carouselGambar.setPageCount(0);
        carouselGambar.setImageListener(imageListener);
        namaBarang = (TextView) findViewById(R.id.nama_barang);
        hargaBarang = (TextView) findViewById(R.id.harga_barang);
        getDetailBarangJasa();
    }

    private void getDetailBarangJasa(){
        queue = Volley.newRequestQueue(this);
        String url = UrlUbama.BarangJasa+idBarangJasa;
        JsonObjectRequest barangJasaRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                barangJasa = new Gson().fromJson(response.toString(), BarangJasa.class);
                carouselGambar = (CarouselView) findViewById(R.id.carousel_gambar);
                carouselGambar.setPageCount((barangJasa.gambar.size()));
                carouselGambar.setImageListener(imageListener);
                namaBarang.setText(barangJasa.nama);
                Locale localeID = new Locale("in", "ID");
                NumberFormat currency  = NumberFormat.getCurrencyInstance(localeID);
                hargaBarang.setText(currency.format(barangJasa.harga).toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", UserToken.getToken(getApplicationContext()));
                params.put("Accept", "application/json");
                return params;
            }
        };
        queue.add(barangJasaRequest);
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            if(barangJasa.gambar.size()>0){
                Picasso.with(getApplicationContext()).load(UrlUbama.URL_IMAGE + barangJasa.gambar.get(position).url_gambar).fit().into(imageView);
            }
        }
    };
}
