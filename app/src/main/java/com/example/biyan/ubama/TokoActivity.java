package com.example.biyan.ubama;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.biyan.ubama.adapters.TokoBarangJasaAdapter;
import com.example.biyan.ubama.models.Toko;
import com.google.gson.Gson;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TokoActivity extends AppCompatActivity {

    @BindView(R.id.nama_toko)
    TextView namaToko;
    @BindView(R.id.nama_pemilik)
    TextView namaPemilik;
    @BindView(R.id.alamat_toko)
    TextView alamatToko;
    @BindView(R.id.favorit_toko)
    ImageView favoritToko;
    @BindView(R.id.deskripsi_toko)
    ExpandableTextView deskripsiToko;
    @BindView(R.id.catatan_toko)
    ExpandableTextView catatanToko;
    @BindView(R.id.recycler_barang_jasa_toko)
    RecyclerView recyclerBarangJasaToko;

    private RequestQueue queue;
    private RecyclerView.Adapter adapterBarangJasaToko;
    private RecyclerView.LayoutManager layoutManagerBarangJasaToko;

    private int idToko;
    private Toko toko;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toko);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        idToko = intent.getIntExtra("idToko", 0);
        queue = Volley.newRequestQueue(this);
        layoutManagerBarangJasaToko = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerBarangJasaToko.setLayoutManager(layoutManagerBarangJasaToko);

        getDataToko();
    }

    private void getDataToko(){
        String url = UrlUbama.DATA_TOKO+idToko;
        JsonObjectRequest getDataToko = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                toko = new Gson().fromJson(response.toString(), Toko.class);
                namaToko.setText(toko.nama);
                namaPemilik.setText(toko.pemilik.user.name);
                alamatToko.setText(toko.alamat);
                if(toko.favorit){
                    favoritToko.setImageResource(R.drawable.ic_favorite_red);
                }
                else{
                    favoritToko.setImageResource(R.drawable.ic_favorite_border_grey);
                }
                deskripsiToko.setText(toko.deskripsi);
                catatanToko.setText(toko.catatan_toko);
                adapterBarangJasaToko = new TokoBarangJasaAdapter(toko.barang_jasa);
                recyclerBarangJasaToko.setAdapter(adapterBarangJasaToko);
                favoritToko.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UbahFavoritToko();
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Volley Toko Activity", error.toString());
                return;
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", UserToken.getToken(getApplicationContext()));
                params.put("Accept", "application/json");
                return params;
            }
        };
        queue.add(getDataToko);
    }

    private void UbahFavoritToko(){
        String url = UrlUbama.UBAH_FAVORIT_TOKO + idToko;
        JsonObjectRequest ubahFavoritTokoRequest = new JsonObjectRequest(Request.Method.PUT, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("favorit")) {
                        favoritToko.setImageResource(R.drawable.ic_favorite_red);
                    } else {
                        favoritToko.setImageResource(R.drawable.ic_favorite_border_grey);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
        queue.add(ubahFavoritTokoRequest);
    }
}
