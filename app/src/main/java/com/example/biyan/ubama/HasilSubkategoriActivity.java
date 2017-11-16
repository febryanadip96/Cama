package com.example.biyan.ubama;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.biyan.ubama.adapters.BarangJasaAdapter;
import com.example.biyan.ubama.models.BarangJasa;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HasilSubkategoriActivity extends AppCompatActivity {

    @BindView(R.id.recycler_barang_jasa_subkategori)
    RecyclerView recyclerBarangJasaSubkategori;
    @BindView(R.id.empty)
    TextView empty;

    private RecyclerView.Adapter adapterBarangJasaSubkategori;
    private RecyclerView.LayoutManager layoutManagerBarangJasaSubkategori;
    private List<BarangJasa> barangJasaList;
    RequestQueue queue;
    private int idSubkategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_subkategori);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        idSubkategori = intent.getIntExtra("idSubkategori", 0);
        setTitle(intent.getStringExtra("namaSubkategori"));

        queue = Volley.newRequestQueue(this);

        layoutManagerBarangJasaSubkategori = new GridLayoutManager(this, 2);
        recyclerBarangJasaSubkategori.setLayoutManager(layoutManagerBarangJasaSubkategori);

        getBarangJasaSubkategori();
    }

    private void getBarangJasaSubkategori() {
        String url = UrlUbama.BARANG_JASA_SUBKATEGORI + idSubkategori;
        JsonArrayRequest barangJasaSubkategori = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                barangJasaList = new Gson().fromJson(response.toString(), new TypeToken<List<BarangJasa>>() {
                }.getType());
                adapterBarangJasaSubkategori = new BarangJasaAdapter(barangJasaList);
                recyclerBarangJasaSubkategori.setAdapter(adapterBarangJasaSubkategori);
                if (!(barangJasaList.size() > 0)) {
                    empty.setVisibility(View.VISIBLE);
                    recyclerBarangJasaSubkategori.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Volley HasilSubkategoriActivity", error.toString());
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
        queue.add(barangJasaSubkategori);
    }
}
