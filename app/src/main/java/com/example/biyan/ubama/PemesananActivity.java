package com.example.biyan.ubama;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PemesananActivity extends AppCompatActivity {

    @BindView(R.id.image_barang)
    ImageView imageBarang;
    @BindView(R.id.nama_barang)
    TextView namaBarang;
    @BindView(R.id.harga_barang)
    TextView hargaBarang;
    @BindView(R.id.kurang)
    ImageButton kurang;
    @BindView(R.id.jumlah)
    EditText jumlah;
    @BindView(R.id.tambah)
    ImageButton tambah;
    @BindView(R.id.alamat_tujuan)
    TextView alamatTujuan;
    @BindView(R.id.total_harga)
    TextView totalHarga;
    @BindView(R.id.pesan)
    Button pesan;
    @BindView(R.id.catatan_pembeli)
    EditText catatanPembeli;
    private ProgressDialog loading;
    private int idBarangJasa;
    private int min_pesan;
    BarangJasa barangJasa;
    User user;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemesanan);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        idBarangJasa = intent.getIntExtra("idBarangJasa", 0);
        getDetailBarangJasa();
        catatanPembeli.hasFocus();
        jumlah.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    int tertulis = Integer.parseInt(jumlah.getText().toString()) + 0;
                    int harga = barangJasa.harga + 0;
                    int total = 0;
                    if (tertulis < min_pesan) {
                        tertulis = min_pesan;
                        jumlah.setText(tertulis + "");
                        Toast.makeText(PemesananActivity.this, "Pemesanan minimal " + min_pesan, Toast.LENGTH_LONG).show();
                    } else {
                        jumlah.setText(tertulis + "");
                    }
                    total = tertulis * harga;
                    NumberFormat currency = NumberFormat.getInstance(Locale.GERMANY);
                    totalHarga.setText("Rp. " + currency.format(total));
                }
            }
        });
    }

    private void getDetailBarangJasa() {
        loading = new ProgressDialog(this);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.setMessage("Mohon Menunggu");
        loading.setIndeterminate(true);
        loading.show();
        queue = Volley.newRequestQueue(this);
        String url = UrlUbama.UserDataPemesanan + idBarangJasa;
        JsonObjectRequest barangJasaRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    barangJasa = new Gson().fromJson(response.getJSONObject("barangJasa").toString(), BarangJasa.class);
                    user = new Gson().fromJson(response.getJSONObject("user").toString(), User.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                min_pesan = barangJasa.min_pesan;
                if (barangJasa.gambar.size() > 0) {
                    Picasso.with(getApplicationContext()).load(UrlUbama.URL_IMAGE + barangJasa.gambar.get(0).url_gambar).fit().error(R.drawable.ic_error_image).into(imageBarang);
                }
                namaBarang.setText(barangJasa.nama);
                NumberFormat currency = NumberFormat.getInstance(Locale.GERMANY);
                hargaBarang.setText("Rp. " + currency.format(barangJasa.harga));
                jumlah.setText(barangJasa.min_pesan + "");
                totalHarga.setText("Rp. " + currency.format(barangJasa.harga * barangJasa.min_pesan));
                alamatTujuan.setText(user.name + "\n" + user.pengguna.alamat + "\n" + user.pengguna.telepon + "\n");
                loading.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
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
        queue.add(barangJasaRequest);
    }

    @OnClick({R.id.kurang, R.id.tambah, R.id.pesan})
    public void onViewClicked(View view) {
        int tertulis = Integer.parseInt(jumlah.getText().toString()) + 0;
        int harga = barangJasa.harga + 0;
        int total = 0;
        switch (view.getId()) {
            case R.id.kurang:
                if (tertulis <= min_pesan) {
                    tertulis = min_pesan;
                    jumlah.setText(tertulis + "");
                    Toast.makeText(PemesananActivity.this, "Pemesanan minimal " + min_pesan, Toast.LENGTH_LONG).show();
                } else {
                    tertulis--;
                    jumlah.setText(tertulis + "");
                }
                break;
            case R.id.tambah:
                tertulis++;
                jumlah.setText(tertulis + "");
                break;
            case R.id.pesan:
                MasukkanKeranjang();
                break;
        }
        total = tertulis * harga;
        NumberFormat currency = NumberFormat.getInstance(Locale.GERMANY);
        totalHarga.setText("Rp. " + currency.format(total));
    }

    private void MasukkanKeranjang() {
        loading = new ProgressDialog(this);
        loading.setIndeterminate(true);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.setMessage("Mohon Tunggu");
        loading.show();
        Map<String, String> params = new HashMap<String, String>();
        params.put("jumlah", jumlah.getText().toString());
        params.put("catatan_pembeli", catatanPembeli.getText().toString());
        params.put("barang_jasa_id", idBarangJasa+"");
        params.put("alamat", alamatTujuan.getText().toString());
        String url = UrlUbama.UserMasukKeranjang;
        JsonObjectRequest masukkanKeranjang = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getBoolean("keranjang")){
                        loading.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(PemesananActivity.this);
                        builder.setMessage("Produk berhasil dimasukkan ke keranjang belanja")
                                .setPositiveButton("Lanjut Belanja", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                    }
                                })
                                .setNegativeButton("Lihat Keranjang", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent keranjang = new Intent(PemesananActivity.this, KeranjangActivity.class);
                                        startActivity(keranjang);
                                        finish();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
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
        queue.add(masukkanKeranjang);
    }
}
