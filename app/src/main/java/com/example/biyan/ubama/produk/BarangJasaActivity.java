package com.example.biyan.ubama.produk;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
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
import com.example.biyan.ubama.keranjang.KeranjangActivity;
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
import de.hdodenhof.circleimageview.CircleImageView;

public class BarangJasaActivity extends AppCompatActivity {

    @BindView(R.id.carousel_gambar)
    CarouselView carouselGambar;
    @BindView(R.id.nama_barang)
    TextView namaBarang;
    @BindView(R.id.harga_barang)
    TextView hargaBarang;
    @BindView(R.id.jenis_barang)
    TextView jenisBarang;
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
    @BindView(R.id.layout_toko)
    LinearLayout layoutToko;
    @BindView(R.id.layout_komentar)
    LinearLayout layoutKomentar;
    @BindView(R.id.layout_tanya_jawab)
    LinearLayout layoutTanyaJawab;
    @BindView(R.id.jumlah_dilihat)
    TextView jumlahDilihat;
    @BindView(R.id.jumlah_terjual)
    TextView jumlahTerjual;
    @BindView(R.id.nama_pemilik)
    TextView namaPemilik;

    int idBarangJasa;
    int idToko;
    BarangJasa barangJasa;
    RequestQueue queue;
    int[] sampleImages = {R.drawable.ic_error_image};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang_jasa);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(intent.getStringExtra("namaBarangJasa"));
        idBarangJasa = intent.getIntExtra("idBarangJasa", 0);

        barangJasa = new BarangJasa();
        carouselGambar = (CarouselView) findViewById(R.id.carousel_gambar);
        carouselGambar.setPageCount(0);
        carouselGambar.setImageListener(imageListener);
        queue = Volley.newRequestQueue(this);

        getDetailBarangJasa();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.requestFocus();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.keranjang) {
            Intent keranjang = new Intent(this,KeranjangActivity.class);
            startActivity(keranjang);
        } else if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void getDetailBarangJasa() {
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.setMessage("Mohon Menunggu");
        loading.setIndeterminate(true);
        loading.show();
        String url = UrlUbama.BARANG_JASA + idBarangJasa;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                barangJasa = new Gson().fromJson(response.toString(), BarangJasa.class);
                if (barangJasa.gambar.size() > 0) {
                    carouselGambar.setPageCount((barangJasa.gambar.size()));
                } else {
                    carouselGambar.setPageCount(sampleImages.length);
                }
                carouselGambar.setImageListener(imageListener);
                namaBarang.setText(barangJasa.nama);
                NumberFormat currency = NumberFormat.getInstance(Locale.GERMANY);
                hargaBarang.setText("Rp. " + currency.format(barangJasa.harga).toString());
                jenisBarang.setText(barangJasa.jenis);
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
                namaPemilik.setText(barangJasa.toko.pemilik.user.name);
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
                idToko = barangJasa.toko.id;
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

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            if (barangJasa.gambar.size() > 0) {
                Picasso.with(getApplicationContext()).load(UrlUbama.URL_IMAGE + barangJasa.gambar.get(position).url_gambar).fit().memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(imageView);
            } else {
                imageView.setImageResource(sampleImages[position]);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
        }
    };

    @OnClick(R.id.favorit_barang)
    public void onFavoritBarangClicked() {
        String url = UrlUbama.USER_UBAH_FAVORIT + idBarangJasa;
        JsonObjectRequest ubahFavoritBarangJasaRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
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
                Log.e("Error Volley", error.toString());
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
        queue.add(ubahFavoritBarangJasaRequest);
    }

    @OnClick(R.id.pesan)
    public void onPesanClicked() {
        Intent pesan = new Intent(this, PemesananActivity.class);
        pesan.putExtra("idBarangJasa", idBarangJasa);
        startActivity(pesan);
    }

    @OnClick({R.id.layout_komentar, R.id.jumlah_komentar})
    public void onLayoutKomentarClicked() {
        Intent komentar = new Intent(BarangJasaActivity.this, KomentarActivity.class);
        komentar.putExtra("idBarangJasa", idBarangJasa);
        startActivity(komentar);
    }

    @OnClick({R.id.layout_tanya_jawab, R.id.jumlah_faq})
    public void onLayoutTanyaJawabClicked() {
        Intent tanyaJawab = new Intent(BarangJasaActivity.this, TanyaJawabActivity.class);
        tanyaJawab.putExtra("idBarangJasa", idBarangJasa);
        startActivity(tanyaJawab);
    }

    @OnClick(R.id.layout_toko)
    public void onLayoutTokoClicked() {
        Intent toko = new Intent(BarangJasaActivity.this, TokoActivity.class);
        toko.putExtra("idToko", barangJasa.toko.id);
        startActivity(toko);
    }
}
