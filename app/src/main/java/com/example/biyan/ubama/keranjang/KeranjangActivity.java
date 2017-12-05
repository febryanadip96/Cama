package com.example.biyan.ubama.keranjang;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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
import com.example.biyan.ubama.R;
import com.example.biyan.ubama.UrlUbama;
import com.example.biyan.ubama.UserToken;
import com.example.biyan.ubama.models.Keranjang;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class KeranjangActivity extends AppCompatActivity {
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.total_harga)
    TextView totalHarga;
    @BindView(R.id.proses)
    Button proses;
    @BindView(R.id.empty)
    TextView empty;
    @BindView(R.id.layout_lanjut_pesan)
    LinearLayout layoutLanjutPesan;

    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    RequestQueue queue;
    List<Keranjang> keranjangList;
    int tampungTotalHarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);
        ButterKnife.bind(this);
        queue = Volley.newRequestQueue(this);
        layoutManager = new GridLayoutManager(this, 1);
        recycler.setLayoutManager(layoutManager);
        getKeranjang();
    }

    public void getKeranjang() {
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.setMessage("Mohon Menunggu");
        loading.setIndeterminate(true);
        loading.show();
        String url = UrlUbama.USER_KERANJANG;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    keranjangList = new Gson().fromJson(response.getJSONArray("toko").toString(), new TypeToken<List<Keranjang>>() {
                    }.getType());
                    tampungTotalHarga = response.getInt("total");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (keranjangList.size() > 0) {
                    layoutLanjutPesan.setVisibility(View.VISIBLE);
                    adapter = new KeranjangAdapter(keranjangList);
                    recycler.setAdapter(adapter);
                    NumberFormat currency = NumberFormat.getInstance(Locale.GERMANY);
                    totalHarga.setText("Rp. " + currency.format(tampungTotalHarga).toString());
                } else {
                    layoutLanjutPesan.setVisibility(View.GONE);
                    empty.setVisibility(View.VISIBLE);
                    recycler.setVisibility(View.GONE);
                }

                loading.dismiss();
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

    @OnClick(R.id.proses)
    public void onProsesClicked() {
        AlertDialog.Builder builder = new AlertDialog.Builder(KeranjangActivity.this);
        builder.setMessage("Anda ingin memulai proses pemesanan sekarang?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        prosesPesanan();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void prosesPesanan(){
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.setMessage("Mohon Menunggu");
        loading.setIndeterminate(true);
        loading.show();
        String url = UrlUbama.USER_PROSES_PESANAN;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("proses")) {
                        loading.dismiss();
                        getKeranjang();
                        pesanInfo();
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

    public void pesanInfo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(KeranjangActivity.this);
        builder.setMessage("Pesanan Anda sudah diteruskan ke penjual. Silahkan melihat status pesanan Anda di menu pesanan.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
