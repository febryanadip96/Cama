package com.example.biyan.ubama;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class HasilFakultasActivity extends AppCompatActivity {

    @BindView(R.id.recycler_barang_jasa_fakultas)
    RecyclerView recyclerBarangJasaFakultas;

    private int idFakultas;
    private RecyclerView.Adapter adapterBarangJasaFakultas;
    private RecyclerView.LayoutManager layoutManagerBarangJasaFakultas;
    private List<BarangJasa> barangJasaList;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fakultas);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        idFakultas = intent.getIntExtra("idFakultas", 0);
        setTitle(intent.getStringExtra("namaFakultas"));

        queue = Volley.newRequestQueue(this);

        layoutManagerBarangJasaFakultas = new GridLayoutManager(this, 2);
        recyclerBarangJasaFakultas.setLayoutManager(layoutManagerBarangJasaFakultas);

        getBarangJasaFakultas();
    }

    private void getBarangJasaFakultas(){
        String url = UrlUbama.BarangJasaFakultas+idFakultas;
        JsonArrayRequest barangJasaFakultas = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                barangJasaList = new Gson().fromJson(response.toString(), new TypeToken<List<BarangJasa>>() {
                }.getType());
                adapterBarangJasaFakultas = new BarangJasaAdapter(barangJasaList);
                recyclerBarangJasaFakultas.setAdapter(adapterBarangJasaFakultas);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
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
        queue.add(barangJasaFakultas);
    }
}