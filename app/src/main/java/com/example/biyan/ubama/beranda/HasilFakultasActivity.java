package com.example.biyan.ubama.beranda;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biyan.ubama.produk.BarangJasaAdapter;
import com.example.biyan.ubama.R;
import com.example.biyan.ubama.UrlCama;
import com.example.biyan.ubama.UserToken;
import com.example.biyan.ubama.models.BarangJasa;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HasilFakultasActivity extends AppCompatActivity {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.sort)
    LinearLayout sort;
    @BindView(R.id.filter)
    LinearLayout filter;
    @BindView(R.id.empty)
    TextView empty;

    int idFakultas;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    List<BarangJasa> barangJasaList;
    RequestQueue queue;

    String orderQuery, sortQuery = "asc", filterQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_fakultas);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        idFakultas = intent.getIntExtra("idFakultas", 0);
        setTitle(intent.getStringExtra("namaFakultas"));

        queue = Volley.newRequestQueue(this);
        layoutManager = new GridLayoutManager(this, 2);
        recycler.setLayoutManager(layoutManager);

        getBarangJasaFakultas();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void getBarangJasaFakultas() {
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.setMessage("Mohon Menunggu");
        loading.setIndeterminate(true);
        loading.show();
        String url = UrlCama.FAKULTAS_BARANG_JASA + idFakultas;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                barangJasaList = new Gson().fromJson(response, new TypeToken<List<BarangJasa>>() {
                }.getType());
                adapter = new BarangJasaAdapter(barangJasaList);
                recycler.setAdapter(adapter);
                if(!(barangJasaList.size()>0)){
                    recycler.setVisibility(View.GONE);
                    empty.setVisibility(View.VISIBLE);
                }
                else{
                    recycler.setVisibility(View.VISIBLE);
                    empty.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Log.e("Error Volley", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", UserToken.getToken(getApplicationContext()));
                params.put("Accept", "application/json");
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("order", orderQuery);
                params.put("sort", sortQuery);
                params.put("filter", filterQuery);
                return params;
            }
        };
        request.setShouldCache(false);
        queue.add(request);
    }

    @OnClick(R.id.sort)
    public void onSortClicked() {
        final String[] sortArray = {"Tidak Ada", "Terbaru", "Harga Tertinggi", "Harga Terendah"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Urutkan")
                .setItems(sortArray, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                orderQuery = null;
                                sortQuery = "asc";
                                break;
                            case 1:
                                orderQuery = "created_at";
                                sortQuery = "desc";
                                break;
                            case 2:
                                orderQuery = "harga";
                                sortQuery = "desc";
                                break;
                            case 3:
                                orderQuery = "harga";
                                sortQuery = "asc";
                                break;
                        }
                        getBarangJasaFakultas();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @OnClick(R.id.filter)
    public void onFilterClicked() {
        final String[] filterArray = {"Semua", "Baru", "Bekas"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Filter")
                .setItems(filterArray, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                filterQuery = null;
                                break;
                            case 1:
                                filterQuery = "Baru";
                                break;
                            case 2:
                                filterQuery = "Bekas";
                                break;
                        }
                        getBarangJasaFakultas();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
