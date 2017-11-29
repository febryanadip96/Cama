package com.example.biyan.ubama.toko;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.biyan.ubama.R;
import com.example.biyan.ubama.UrlUbama;
import com.example.biyan.ubama.UserToken;
import com.example.biyan.ubama.models.Pesanan;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TokoDetailPesananActivity extends AppCompatActivity {

    @BindView(R.id.nomor_pesanan)
    TextView nomorPesanan;
    @BindView(R.id.total_harga)
    TextView totalHarga;
    @BindView(R.id.tanggal_pesan)
    TextView tanggalPesan;
    @BindView(R.id.alamat)
    TextView alamat;
    @BindView(R.id.recycler_item_detail_pesanan)
    RecyclerView recyclerItemDetailPesanan;
    @BindView(R.id.log_pesanan)
    TextView logPesanan;
    @BindView(R.id.terima_pesanan)
    Button terimaPesanan;
    @BindView(R.id.tolak_pesanan)
    Button tolakPesanan;

    int idPesanan;
    Pesanan pesanan;
    RequestQueue queue;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toko_detail_pesanan);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        idPesanan = intent.getIntExtra("idPesanan", 0);
        layoutManager = new LinearLayoutManager(this);
        recyclerItemDetailPesanan.setLayoutManager(layoutManager);
        queue = Volley.newRequestQueue(this);

        getDetailPesanan();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDetailPesanan();
    }

    public void getDetailPesanan() {
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.setMessage("Mohon Menunggu");
        loading.setIndeterminate(true);
        loading.show();
        String url = UrlUbama.USER_TOKO_PESANAN_DETAIL + idPesanan;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.dismiss();
                pesanan = new Gson().fromJson(response.toString(), Pesanan.class);
                nomorPesanan.setText(pesanan.id + "");
                NumberFormat currency = NumberFormat.getInstance(Locale.GERMANY);
                totalHarga.setText(String.valueOf("Rp. " + currency.format(pesanan.total_harga).toString()));
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm");
                Date date = null;
                try {
                    date = inputFormat.parse(pesanan.created_at);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                tanggalPesan.setText(outputFormat.format(date));
                alamat.setText(pesanan.alamat);
                String isiLog = "";
                for (Pesanan.Log_pesanan log : pesanan.log_pesanan) {
                    try {
                        date = inputFormat.parse(log.created_at);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    isiLog += outputFormat.format(date) + "\n" + log.keterangan + "\n\n";
                }
                logPesanan.setText(isiLog);
                adapter = new TokoDetailPesananItemAdapter(pesanan.detail_pesanan);
                recyclerItemDetailPesanan.setAdapter(adapter);
                if(pesanan.status.equals("Diproses")){
                    terimaPesanan.setVisibility(View.GONE);
                }
                else if(pesanan.status.equals("Selesai") || pesanan.status.equals("Ditolak")){
                    terimaPesanan.setVisibility(View.GONE);
                    tolakPesanan.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Log.e("Error Volley DetailPesananActivity", error.toString());
                finish();
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

    @OnClick(R.id.terima_pesanan)
    public void onTerimaPesananClicked() {
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.setMessage("Mohon Menunggu");
        loading.setIndeterminate(true);
        loading.show();
        String url = UrlUbama.USER_TOKO_TERIMA_PESANAN + idPesanan;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.dismiss();
                try {
                    boolean terima = response.getBoolean("terima");
                    if(terima){
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Log.e("Error Volley", error.toString());
                finish();
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

    @OnClick(R.id.tolak_pesanan)
    public void onTolakPesananClicked() {
        Intent intent = new Intent(this, TokoAlasanDitolakActivity.class);
        intent.putExtra("idPesanan",idPesanan);
        startActivity(intent);
    }
}
