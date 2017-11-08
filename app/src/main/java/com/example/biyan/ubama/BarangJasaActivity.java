package com.example.biyan.ubama;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
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

    private int idBarangJasa;
    BarangJasa barangJasa;
    RequestQueue queue;

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

        getDetailBarangJasa();
    }

    private void getDetailBarangJasa() {
        queue = Volley.newRequestQueue(this);
        String url = UrlUbama.BarangJasa + idBarangJasa;
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
                kondisiBarang.setText(barangJasa.baruBekas);
                if (!barangJasa.toko.url_profile.isEmpty()) {
                    Picasso.with(getApplicationContext()).load(UrlUbama.URL_IMAGE + barangJasa.toko.url_profile).into(imageToko);
                }
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
                jumlahKomentar.setText(barangJasa.jumlah_komentar+" Komentar");
                jumlahFaq.setText(barangJasa.jumlah_faq+" FAQ");
                deskripsi.setText(barangJasa.deskripsi);
                catatanPenjual.setText(barangJasa.catatan_penjual);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            if (barangJasa.gambar.size() > 0) {
                Picasso.with(getApplicationContext()).load(UrlUbama.URL_IMAGE + barangJasa.gambar.get(position).url_gambar).fit().into(imageView);
            }
        }
    };


}
