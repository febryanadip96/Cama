package com.example.biyan.ubama.toko;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.example.biyan.ubama.gps.RouteActivity;
import com.example.biyan.ubama.UrlCama;
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
    @BindView(R.id.map)
    ImageView map;
    @BindView(R.id.recycler_item_detail_pesanan)
    RecyclerView recyclerItemDetailPesanan;
    @BindView(R.id.log_pesanan)
    TextView logPesanan;
    @BindView(R.id.terima_pesanan)
    Button terimaPesanan;
    @BindView(R.id.tolak_pesanan)
    Button tolakPesanan;
    @BindView(R.id.telepon)
    LinearLayout telepon;
    @BindView(R.id.sms)
    LinearLayout sms;
    @BindView(R.id.email)
    LinearLayout email;
    @BindView(R.id.layout_hubungi)
    CardView layoutHubungi;
    @BindView(R.id.alasan_ditolak)
    TextView alasanDitolak;
    @BindView(R.id.layout_ditolak)
    CardView layoutDitolak;
    @BindView(R.id.layout_tombol)
    CardView layoutTombol;

    int idPesanan;
    Pesanan pesanan;
    RequestQueue queue;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    String noTelpPembeli;
    String emailPembeli;

    double latitudePembeli = 0, longitudePembeli = 0;

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
        String url = UrlCama.USER_TOKO_PESANAN_DETAIL + idPesanan;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.dismiss();
                Log.d("hasil", response.toString());
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
                latitudePembeli = pesanan.latitude;
                longitudePembeli = pesanan.longitude;
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
                alasanDitolak.setText(pesanan.alasan_ditolak);
                noTelpPembeli = pesanan.pemesan.telepon;
                emailPembeli = pesanan.pemesan.user.email;

                if (pesanan.status.equals("Diproses")) {
                    layoutDitolak.setVisibility(View.GONE);
                    terimaPesanan.setVisibility(View.GONE);
                    tolakPesanan.setVisibility(View.VISIBLE);
                    layoutHubungi.setVisibility(View.VISIBLE);
                    layoutTombol.setVisibility(View.VISIBLE);
                    map.setVisibility(View.VISIBLE);
                } else if (pesanan.status.equals("Selesai")) {
                    layoutDitolak.setVisibility(View.GONE);
                    terimaPesanan.setVisibility(View.GONE);
                    tolakPesanan.setVisibility(View.GONE);
                    layoutHubungi.setVisibility(View.VISIBLE);
                    layoutTombol.setVisibility(View.GONE);
                    map.setVisibility(View.GONE);
                } else if (pesanan.status.equals("Ditolak")) {
                    layoutDitolak.setVisibility(View.VISIBLE);
                    terimaPesanan.setVisibility(View.GONE);
                    tolakPesanan.setVisibility(View.GONE);
                    layoutHubungi.setVisibility(View.GONE);
                    layoutTombol.setVisibility(View.GONE);
                    map.setVisibility(View.GONE);
                } else if(pesanan.status.equals("Batal")){
                    layoutDitolak.setVisibility(View.GONE);
                    terimaPesanan.setVisibility(View.GONE);
                    tolakPesanan.setVisibility(View.GONE);
                    layoutHubungi.setVisibility(View.GONE);
                    layoutTombol.setVisibility(View.GONE);
                    map.setVisibility(View.GONE);
                } else {
                    layoutDitolak.setVisibility(View.GONE);
                    terimaPesanan.setVisibility(View.VISIBLE);
                    tolakPesanan.setVisibility(View.VISIBLE);
                    layoutHubungi.setVisibility(View.VISIBLE);
                    layoutTombol.setVisibility(View.VISIBLE);
                    map.setVisibility(View.VISIBLE);
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

    @OnClick(R.id.telepon)
    public void onTeleponClicked() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", noTelpPembeli, null));
        startActivity(intent);
    }

    @OnClick(R.id.sms)
    public void onSmsClicked() {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + noTelpPembeli));
        intent.putExtra("sms_body", "UBAMA PESANAN ANDA ID " + idPesanan + "\n\n");
        startActivity(intent);
    }

    @OnClick(R.id.email)
    public void onEmailClicked() {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", emailPembeli, null));
        intent.putExtra(Intent.EXTRA_SUBJECT, "UBAMA PESANAN ANDA ID " + idPesanan);
        startActivity(Intent.createChooser(intent, "Send Email"));
    }

    @OnClick(R.id.terima_pesanan)
    public void onTerimaPesananClicked() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TokoDetailPesananActivity.this);
        builder.setMessage("Anda akan menerima pesanan ini?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        terimaPesanan();
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


    public void terimaPesanan() {
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.setMessage("Mohon Menunggu");
        loading.setIndeterminate(true);
        loading.show();
        String url = UrlCama.USER_TOKO_TERIMA_PESANAN + idPesanan;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.dismiss();
                try {
                    boolean terima = response.getBoolean("terima");
                    if (terima) {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(TokoDetailPesananActivity.this);
        builder.setMessage("Anda ingin menolak pesanan ini?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        tolakPesanan();
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

    public void tolakPesanan() {
        Intent intent = new Intent(this, TokoAlasanDitolakActivity.class);
        intent.putExtra("idPesanan", idPesanan);
        startActivity(intent);
    }

    @OnClick(R.id.map)
    public void onMapClicked() {
        Intent intent = new Intent(this, RouteActivity.class);
        intent.putExtra("latitude", latitudePembeli);
        intent.putExtra("longitude", longitudePembeli);
        startActivity(intent);
    }
}
