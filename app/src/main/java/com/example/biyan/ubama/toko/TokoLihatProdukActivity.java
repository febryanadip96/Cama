package com.example.biyan.ubama.toko;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biyan.ubama.R;
import com.example.biyan.ubama.UrlUbama;
import com.example.biyan.ubama.UserToken;
import com.example.biyan.ubama.produk.KomentarActivity;
import com.example.biyan.ubama.models.BarangJasa;
import com.google.gson.Gson;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TokoLihatProdukActivity extends AppCompatActivity {

    @BindView(R.id.carousel_gambar)
    CarouselView carouselGambar;
    @BindView(R.id.nama_barang)
    TextView namaBarang;
    @BindView(R.id.harga_barang)
    TextView hargaBarang;
    @BindView(R.id.jenis_barang)
    TextView jenisBarang;
    @BindView(R.id.star_barang_1)
    ImageView starBarang1;
    @BindView(R.id.star_barang_2)
    ImageView starBarang2;
    @BindView(R.id.star_barang_3)
    ImageView starBarang3;
    @BindView(R.id.star_barang_4)
    ImageView starBarang4;
    @BindView(R.id.star_barang_5)
    ImageView starBarang5;
    @BindView(R.id.layout_komentar)
    LinearLayout layoutKomentar;
    @BindView(R.id.jumlah_komentar)
    TextView jumlahKomentar;
    @BindView(R.id.jumlah_faq)
    TextView jumlahFaq;
    @BindView(R.id.layout_tanya_jawab)
    LinearLayout layoutTanyaJawab;
    @BindView(R.id.kondisi_barang)
    TextView kondisiBarang;
    @BindView(R.id.deskripsi)
    ExpandableTextView deskripsi;
    @BindView(R.id.catatan_penjual)
    ExpandableTextView catatanPenjual;
    @BindView(R.id.edit)
    Button edit;
    @BindView(R.id.hapus)
    Button hapus;
    @BindView(R.id.jumlah_dilihat)
    TextView jumlahDilihat;
    @BindView(R.id.jumlah_terjual)
    TextView jumlahTerjual;

    BarangJasa barangJasa;
    int idBarangJasa;
    RequestQueue queue;
    int[] sampleImages = {R.drawable.ic_error_image};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toko_lihat_produk);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        idBarangJasa = intent.getIntExtra("idBarangJasa", 0);
        queue = Volley.newRequestQueue(this);
        carouselGambar = (CarouselView) findViewById(R.id.carousel_gambar);
        carouselGambar.setPageCount(0);
        carouselGambar.setImageListener(imageListener);
        getDetailBarangJasa();
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
        getDetailBarangJasa();
    }

    public void getDetailBarangJasa() {
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.setMessage("Mohon Menunggu");
        loading.setIndeterminate(true);
        loading.show();
        String url = UrlUbama.BARANG_JASA + idBarangJasa;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                barangJasa = new Gson().fromJson(response.toString(), BarangJasa.class);
                if(barangJasa.gambar.size()>0){
                    carouselGambar.setPageCount((barangJasa.gambar.size()));
                }else{
                    carouselGambar.setPageCount(sampleImages.length);
                }
                carouselGambar.setImageListener(imageListener);
                namaBarang.setText(barangJasa.nama);
                NumberFormat currency = NumberFormat.getInstance(Locale.GERMANY);
                hargaBarang.setText("Rp. " + currency.format(barangJasa.harga).toString());
                jenisBarang.setText(barangJasa.jenis);
                kondisiBarang.setText(barangJasa.baruBekas);
                int rating = (int) Math.floor(barangJasa.total_rating);
                switch (rating) {
                    case 1:
                        starBarang1.setImageResource(R.drawable.ic_star_yellow);
                        break;
                    case 2:
                        starBarang1.setImageResource(R.drawable.ic_star_yellow);
                        starBarang2.setImageResource(R.drawable.ic_star_yellow);
                        break;
                    case 3:
                        starBarang1.setImageResource(R.drawable.ic_star_yellow);
                        starBarang2.setImageResource(R.drawable.ic_star_yellow);
                        starBarang3.setImageResource(R.drawable.ic_star_yellow);
                        break;
                    case 4:
                        starBarang1.setImageResource(R.drawable.ic_star_yellow);
                        starBarang2.setImageResource(R.drawable.ic_star_yellow);
                        starBarang3.setImageResource(R.drawable.ic_star_yellow);
                        starBarang4.setImageResource(R.drawable.ic_star_yellow);
                        break;
                    case 5:
                        starBarang1.setImageResource(R.drawable.ic_star_yellow);
                        starBarang2.setImageResource(R.drawable.ic_star_yellow);
                        starBarang3.setImageResource(R.drawable.ic_star_yellow);
                        starBarang4.setImageResource(R.drawable.ic_star_yellow);
                        starBarang5.setImageResource(R.drawable.ic_star_yellow);
                        break;
                    default:
                        break;
                }
                jumlahKomentar.setText(currency.format(barangJasa.jumlah_komentar).toString());
                jumlahFaq.setText(currency.format(barangJasa.jumlah_faq).toString());
                jumlahDilihat.setText(currency.format(barangJasa.jumlah_dilihat).toString());
                jumlahTerjual.setText(currency.format(barangJasa.jumlah_terjual).toString());
                deskripsi.setText(barangJasa.deskripsi);
                catatanPenjual.setText(barangJasa.catatan_penjual);
                layoutKomentar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent komentar = new Intent(TokoLihatProdukActivity.this, KomentarActivity.class);
                        komentar.putExtra("idBarangJasa", idBarangJasa);
                        startActivity(komentar);
                    }
                });
                loading.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Log.e("Error Volley BarangJasaAcitivty", error.toString());
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

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            if (barangJasa.gambar.size() > 0) {
                Picasso.with(getApplicationContext()).load(UrlUbama.URL_IMAGE + barangJasa.gambar.get(position).url_gambar).fit().memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(imageView);
            }else{
                imageView.setImageResource(sampleImages[position]);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
        }
    };

    @OnClick(R.id.edit)
    public void onEditClicked() {
        Intent intent = new Intent(TokoLihatProdukActivity.this, TokoEditProdukActivity.class);
        intent.putExtra("idBarangJasa", idBarangJasa);
        startActivity(intent);
    }

    @OnClick(R.id.hapus)
    public void onHapusClicked() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TokoLihatProdukActivity.this);
        builder.setMessage("Anda yakin ingin menghapus produk ini?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        hapusBarangJasa();
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

    public void hapusBarangJasa() {
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.setMessage("Mohon Menunggu");
        loading.setIndeterminate(true);
        loading.show();
        String url = UrlUbama.USER_TOKO_HAPUS_PRODUK + idBarangJasa;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                try {
                    JSONObject hasil = new JSONObject(response);
                    Boolean hapus = hasil.getBoolean("hapus");
                    if (hapus) {
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
                Log.e("Error Volley BarangJasaAcitivty", error.toString());
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
}
