package com.example.biyan.ubama.pesanan;

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

public class DetailPesananActivity extends AppCompatActivity {

    @BindView(R.id.nomor_pesanan)
    TextView nomorPesanan;
    @BindView(R.id.total_harga)
    TextView totalHarga;
    @BindView(R.id.tanggal_pesan)
    TextView tanggalPesan;
    @BindView(R.id.nama_toko)
    TextView namaToko;
    @BindView(R.id.recycler_item_detail_pesanan)
    RecyclerView recyclerItemDetailPesanan;
    @BindView(R.id.log_pesanan)
    TextView logPesanan;
    @BindView(R.id.alamat)
    TextView alamat;
    @BindView(R.id.telepon)
    LinearLayout telepon;
    @BindView(R.id.sms)
    LinearLayout sms;
    @BindView(R.id.email)
    LinearLayout email;
    @BindView(R.id.layout_hubungi)
    CardView layoutHubungi;
    @BindView(R.id.selesai)
    Button selesai;
    @BindView(R.id.beri_komentar)
    Button beriKomentar;
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

    String noTelpPenjual;
    String emailPenjual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pesanan);
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

    public void getDetailPesanan() {
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.setMessage("Mohon Menunggu");
        loading.setIndeterminate(true);
        loading.show();
        String url = UrlUbama.USER_DETAIL_PESANAN + idPesanan;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pesanan = new Gson().fromJson(response.toString(), Pesanan.class);
                nomorPesanan.setText(pesanan.id + "");
                NumberFormat currency = NumberFormat.getInstance(Locale.GERMANY);
                totalHarga.setText(String.valueOf("Rp. " + currency.format(pesanan.total_harga).toString()));
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy");
                Date date = null;
                try {
                    date = inputFormat.parse(pesanan.created_at);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                tanggalPesan.setText(outputFormat.format(date));
                namaToko.setText(pesanan.detail_pesanan.get(0).barang_jasa.toko.nama);
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
                adapter = new DetailPesananItemAdapter(pesanan.detail_pesanan);
                recyclerItemDetailPesanan.setAdapter(adapter);
                if (pesanan.status.equals("Selesai") || pesanan.status.equals("Ditolak")) {
                    layoutHubungi.setVisibility(View.GONE);
                } else {
                    layoutHubungi.setVisibility(View.VISIBLE);
                }
                if (pesanan.status.equals("Selesai")) {
                    layoutDitolak.setVisibility(View.GONE);
                    selesai.setVisibility(View.GONE);
                    beriKomentar.setVisibility(View.VISIBLE);
                } else if (pesanan.status.equals("Ditolak")) {
                    layoutDitolak.setVisibility(View.VISIBLE);
                    selesai.setVisibility(View.GONE);
                    beriKomentar.setVisibility(View.GONE);
                    layoutTombol.setVisibility(View.GONE);
                } else {
                    layoutDitolak.setVisibility(View.GONE);
                    beriKomentar.setVisibility(View.GONE);
                    selesai.setVisibility(View.VISIBLE);
                }
                alasanDitolak.setText(pesanan.alasan_ditolak.toString());
                noTelpPenjual = pesanan.detail_pesanan.get(0).barang_jasa.toko.pemilik.telepon;
                emailPenjual = pesanan.detail_pesanan.get(0).barang_jasa.toko.pemilik.user.email;
                loading.dismiss();
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

    @OnClick(R.id.telepon)
    public void onTeleponClicked() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", noTelpPenjual, null));
        startActivity(intent);
    }

    @OnClick(R.id.sms)
    public void onSmsClicked() {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + noTelpPenjual));
        intent.putExtra("sms_body", "UBAMA PESANAN ID " + idPesanan + "\n\n");
        startActivity(intent);
    }

    @OnClick(R.id.email)
    public void onEmailClicked() {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", emailPenjual, null));
        intent.putExtra(Intent.EXTRA_SUBJECT, "UBAMA PESANAN ID " + idPesanan);
        startActivity(Intent.createChooser(intent, "Send Email"));
    }

    @OnClick(R.id.selesai)
    public void onSelesaiClicked() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setMessage("Anda ingin menyelesaikan pesanan ini?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        pesananSelesai();
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

    public void pesananSelesai(){
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.setMessage("Mohon Menunggu");
        loading.setIndeterminate(true);
        loading.show();
        String url = UrlUbama.PESANAN_SELESAI + idPesanan;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.dismiss();
                try {
                    boolean selesai = response.getBoolean("selesai");
                    if (selesai) {
                        getDetailPesanan();
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

    @OnClick(R.id.beri_komentar)
    public void onBeriKomentarClicked() {
        Intent intent = new Intent(this, KomentarPesananActivity.class);
        intent.putExtra("idPesanan", idPesanan);
        startActivity(intent);
    }
}
