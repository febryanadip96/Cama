package com.example.biyan.ubama;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
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

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.empty)
    TextView empty;

    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    List<BarangJasa> barangJasaList;
    RequestQueue queue;
    int idSubkategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_subkategori);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        idSubkategori = intent.getIntExtra("idSubkategori", 0);
        setTitle(intent.getStringExtra("namaSubkategori"));

        queue = Volley.newRequestQueue(this);

        layoutManager = new GridLayoutManager(this, 2);
        recycler.setLayoutManager(layoutManager);

        getBarangJasaSubkategori();
    }

    public void getBarangJasaSubkategori() {
        String url = UrlUbama.SUBKATEGORI_BARANG_JASA + idSubkategori;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                barangJasaList = new Gson().fromJson(response.toString(), new TypeToken<List<BarangJasa>>() {
                }.getType());
                adapter = new BarangJasaAdapter(barangJasaList);
                recycler.setAdapter(adapter);
                if (!(barangJasaList.size() > 0)) {
                    empty.setVisibility(View.VISIBLE);
                    recycler.setVisibility(View.GONE);
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
        queue.add(request);
    }
}
