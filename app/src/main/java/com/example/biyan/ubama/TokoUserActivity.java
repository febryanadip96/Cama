package com.example.biyan.ubama;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.biyan.ubama.models.Toko;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class TokoUserActivity extends AppCompatActivity {

    @BindView(R.id.image_toko)
    CircleImageView imageToko;
    Toko toko;
    @BindView(R.id.nama_toko)
    TextView namaToko;
    @BindView(R.id.nama_pemilik)
    TextView namaPemilik;
    @BindView(R.id.jual_produk)
    Button jualProduk;
    @BindView(R.id.produk)
    LinearLayout produk;
    @BindView(R.id.pesanan)
    LinearLayout pesanan;
    @BindView(R.id.tanya_jawab)
    LinearLayout tanyaJawab;
    @BindView(R.id.pengaturan)
    LinearLayout pengaturan;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toko_user);
        ButterKnife.bind(this);
        queue = Volley.newRequestQueue(this);

        getTokoUser();
    }

    public void getTokoUser() {
        String url = UrlUbama.USER_TOKO;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                toko = new Gson().fromJson(response.toString(), Toko.class);
                Picasso.with(TokoUserActivity.this).load(UrlUbama.URL_IMAGE + toko.url_profile).into(imageToko);
                namaToko.setText(toko.nama);
                namaPemilik.setText(toko.pemilik.user.name);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Volley Ubah Favorit", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", UserToken.getToken(getApplicationContext()));
                params.put("Accept", "application/json");
                return params;
            }
        };
        request.setShouldCache(false);
        queue.add(request);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getTokoUser();
    }

    @OnClick(R.id.jual_produk)
    public void onJualProdukClicked() {
        Intent jualProduk = new Intent(TokoUserActivity.this, JualProdukActivity.class);
        startActivity(jualProduk);
    }

    @OnClick(R.id.produk)
    public void onProdukClicked() {
        Intent produk = new Intent(TokoUserActivity.this, TokoProdukActivity.class);
        startActivity(produk);
    }

    @OnClick(R.id.pesanan)
    public void onPesananClicked() {
        Intent pesanan = new Intent(TokoUserActivity.this, TokoPesananActivity.class);
        startActivity(pesanan);
    }

    @OnClick(R.id.tanya_jawab)
    public void onTanyaJawabClicked() {
        Intent tanyaJawab = new Intent(TokoUserActivity.this, TokoTanyaJawabActivity.class);
        startActivity(tanyaJawab);
    }

    @OnClick(R.id.pengaturan)
    public void onPengaturanClicked() {
        Intent pengaturan = new Intent(TokoUserActivity.this, PengaturanTokoActivity.class);
        startActivity(pengaturan);
    }
}
