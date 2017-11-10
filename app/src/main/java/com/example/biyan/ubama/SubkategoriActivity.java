package com.example.biyan.ubama;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubkategoriActivity extends AppCompatActivity {

    @BindView(R.id.list_subkategori_kategori)
    ListView listSubkategoriKategori;

    List<Subkategori> subkategoriList;
    Adapter adapterSubkategori;
    private int idKategori;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        queue = Volley.newRequestQueue(this);
        idKategori = intent.getIntExtra("idKategori", 0);
        setTitle(intent.getStringExtra("namaKategori"));
        getSubkategoriKategori();
    }

    private void getSubkategoriKategori(){
        String url = UrlUbama.SubkateogirKategori+idKategori;
        JsonArrayRequest subkategoriRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                subkategoriList = new Gson().fromJson(response.toString(), new TypeToken<List<Subkategori>>() {
                }.getType());
                adapterSubkategori = new SubkategoriAdapter(getApplicationContext(),subkategoriList);
                listSubkategoriKategori.setAdapter((ListAdapter) adapterSubkategori);
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
        queue.add(subkategoriRequest);
    }

}
