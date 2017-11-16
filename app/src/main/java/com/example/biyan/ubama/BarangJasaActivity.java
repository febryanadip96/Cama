package com.example.biyan.ubama;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.biyan.ubama.models.BarangJasa;
import com.google.gson.Gson;
import com.ms.square.android.expandabletextview.ExpandableTextView;
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
import de.hdodenhof.circleimageview.CircleImageView;

public class BarangJasaActivity extends AppCompatActivity {

    @BindView(R.id.carousel_gambar)
    CarouselView carouselGambar;
    @BindView(R.id.nama_barang)
    TextView namaBarang;
    @BindView(R.id.harga_barang)
    TextView hargaBarang;
    @BindView(R.id.favorit_barang)
    ImageView favoritBarang;
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
    @BindView(R.id.jumlah_komentar)
    TextView jumlahKomentar;
    @BindView(R.id.jumlah_faq)
    TextView jumlahFaq;
    @BindView(R.id.kondisi_barang)
    TextView kondisiBarang;
    @BindView(R.id.image_toko)
    CircleImageView imageToko;
    @BindView(R.id.nama_toko)
    TextView namaToko;
    @BindView(R.id.deskripsi)
    ExpandableTextView deskripsi;
    @BindView(R.id.catatan_penjual)
    ExpandableTextView catatanPenjual;
    @BindView(R.id.pesan)
    Button pesan;

    private ProgressDialog loading;
    private int idBarangJasa;
    private BarangJasa barangJasa;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang_jasa);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        setTitle(intent.getStringExtra("namaBarangJasa"));
        idBarangJasa = intent.getIntExtra("idBarangJasa", 0);

        barangJasa = new BarangJasa();
        carouselGambar = (CarouselView) findViewById(R.id.carousel_gambar);
        carouselGambar.setPageCount(0);
        carouselGambar.setImageListener(imageListener);
        queue = Volley.newRequestQueue(this);

        getDetailBarangJasa();
    }

    private void getDetailBarangJasa() {
        loading = new ProgressDialog(this);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.setMessage("Mohon Menunggu");
        loading.setIndeterminate(true);
        loading.show();
        String url = UrlUbama.BARANG_JASA + idBarangJasa;
        JsonObjectRequest barangJasaRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                barangJasa = new Gson().fromJson(response.toString(), BarangJasa.class);
                carouselGambar = (CarouselView) findViewById(R.id.carousel_gambar);
                carouselGambar.setPageCount((barangJasa.gambar.size()));
                carouselGambar.setImageListener(imageListener);
                namaBarang.setText(barangJasa.nama);
                NumberFormat currency = NumberFormat.getInstance(Locale.GERMANY);
                hargaBarang.setText("Rp. " + currency.format(barangJasa.harga).toString());
                if (barangJasa.favorit) {
                    favoritBarang.setImageResource(R.drawable.ic_favorite_red);
                } else {
                    favoritBarang.setImageResource(R.drawable.ic_favorite_border_grey);
                }
                kondisiBarang.setText(barangJasa.baruBekas);
                if (!barangJasa.toko.url_profile.isEmpty()) {
                    Picasso.with(getApplicationContext()).load(UrlUbama.URL_IMAGE + barangJasa.toko.url_profile).into(imageToko);
                }
                namaToko.setText(barangJasa.toko.nama);
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
                jumlahKomentar.setText(barangJasa.jumlah_komentar + " komentar");
                jumlahFaq.setText(barangJasa.jumlah_faq + " FAQ");
                deskripsi.setText(barangJasa.deskripsi);
                catatanPenjual.setText(barangJasa.catatan_penjual);

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
        queue.add(barangJasaRequest);
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            if (barangJasa.gambar.size() > 0) {
                Picasso.with(getApplicationContext()).load(UrlUbama.URL_IMAGE + barangJasa.gambar.get(position).url_gambar).fit().into(imageView);
            }
        }
    };

    @OnClick({R.id.favorit_barang, R.id.pesan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.favorit_barang:
                ubahFavorit();
                break;
            case R.id.pesan:
                masukkanPesan();
                break;
        }
    }

    private void masukkanPesan() {
        Intent pesan = new Intent(this, PemesananActivity.class);
        pesan.putExtra("idBarangJasa", idBarangJasa);
        startActivity(pesan);
    }

    private void ubahFavorit() {
        String url = UrlUbama.USER_UBAH_FAVORIT_BARANG_JADA + idBarangJasa;
        JsonObjectRequest ubahFavoritRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("favorit")) {
                        favoritBarang.setImageResource(R.drawable.ic_favorite_red);
                    } else {
                        favoritBarang.setImageResource(R.drawable.ic_favorite_border_grey);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Volley Ubah Favorit", error.toString());
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
        queue.add(ubahFavoritRequest);
    }
}
