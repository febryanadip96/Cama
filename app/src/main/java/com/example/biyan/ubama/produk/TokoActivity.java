package com.example.biyan.ubama.produk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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
import com.example.biyan.ubama.models.Toko;
import com.google.gson.Gson;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class TokoActivity extends AppCompatActivity {

    @BindView(R.id.image_toko)
    CircleImageView imageToko;
    @BindView(R.id.email_pemilik)
    TextView emailPemilik;
    @BindView(R.id.slogan_toko)
    TextView sloganToko;
    @BindView(R.id.nama_toko)
    TextView namaToko;
    @BindView(R.id.nama_pemilik)
    TextView namaPemilik;
    @BindView(R.id.alamat_toko)
    TextView alamatToko;
    @BindView(R.id.favorit_toko)
    ImageView favoritToko;
    @BindView(R.id.deskripsi_toko)
    ExpandableTextView deskripsiToko;
    @BindView(R.id.catatan_toko)
    ExpandableTextView catatanToko;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    RequestQueue queue;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    int idToko;
    Toko toko;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toko);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        idToko = intent.getIntExtra("idToko", 0);
        queue = Volley.newRequestQueue(this);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recycler.setLayoutManager(layoutManager);
        getDataToko();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void getDataToko() {
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.setMessage("Mohon Menunggu");
        loading.setIndeterminate(true);
        loading.show();
        String url = UrlUbama.TOKO + idToko;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.dismiss();
                toko = new Gson().fromJson(response.toString(), Toko.class);
                if(!toko.url_profile.equals("")){
                    Picasso.with(getApplicationContext()).load(UrlUbama.URL_IMAGE+toko.url_profile).memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).fit().into(imageToko);
                } else{
                    imageToko.setImageResource(R.drawable.ic_error_image);
                }
                namaToko.setText(toko.nama);
                namaPemilik.setText(toko.pemilik.user.name);
                emailPemilik.setText(toko.pemilik.user.email);
                alamatToko.setText(toko.alamat);
                if (toko.favorit) {
                    favoritToko.setImageResource(R.drawable.ic_favorite_red);
                } else {
                    favoritToko.setImageResource(R.drawable.ic_favorite_border_grey);
                }
                sloganToko.setText(toko.slogan);
                deskripsiToko.setText(toko.deskripsi);
                catatanToko.setText(toko.catatan_toko);
                adapter = new TokoBarangJasaAdapter(toko.barang_jasa);
                recycler.setAdapter(adapter);
                favoritToko.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UbahFavoritToko();
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Log.e("Error Volley", error.toString());
                return;
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
        queue.add(request);
    }

    public void UbahFavoritToko() {
        String url = UrlUbama.USER_UBAH_FAVORIT_TOKO + idToko;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("favorit")) {
                        favoritToko.setImageResource(R.drawable.ic_favorite_red);
                    } else {
                        favoritToko.setImageResource(R.drawable.ic_favorite_border_grey);
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
        queue.add(request);
    }
}
