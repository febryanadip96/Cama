package com.example.biyan.ubama;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PesananActivity extends AppCompatActivity {
    private RecyclerView recyclerPesanan;
    private RecyclerView.Adapter adapterPesanan;
    private RecyclerView.LayoutManager layoutManagerPesanan;
    private List<Pesanan> pesananList;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan);
        queue = Volley.newRequestQueue(this);
        this.setTitle("Pesanan");

        recyclerPesanan = (RecyclerView) findViewById(R.id.recycler_pesanan);
        layoutManagerPesanan = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerPesanan.setLayoutManager(layoutManagerPesanan);

        getPesanan();
    }

    private void getPesanan() {
        String url = UrlUbama.UserPesanan;
        JsonArrayRequest feedRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                pesananList = new Gson().fromJson(response.toString(), new TypeToken<List<Pesanan>>() {
                }.getType());
                adapterPesanan = new PesananAdapter(pesananList);
                recyclerPesanan.setAdapter(adapterPesanan);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplication(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", UserToken.getToken(getApplication()));
                params.put("Accept", "application/json");
                return params;
            }
        };
        queue.add(feedRequest);
    }
}
