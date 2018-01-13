package com.example.biyan.ubama.toko;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.biyan.ubama.models.TanyaJawab;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class TokoJawabPertanyaanActivity extends AppCompatActivity {

    @BindView(R.id.gambar_barang)
    ImageView gambarBarang;
    @BindView(R.id.nama_barang)
    TextView namaBarang;
    @BindView(R.id.image_penanya)
    CircleImageView imagePenanya;
    @BindView(R.id.waktu_tanya)
    TextView waktuTanya;
    @BindView(R.id.nama_penanya)
    TextView namaPenanya;
    @BindView(R.id.pertanyaan)
    TextView pertanyaan;
    @BindView(R.id.layout_jawaban)
    LinearLayout layoutJawaban;
    @BindView(R.id.image_toko)
    CircleImageView imageToko;
    @BindView(R.id.waktu_jawab)
    TextView waktuJawab;
    @BindView(R.id.nama_toko)
    TextView namaToko;
    @BindView(R.id.jawaban)
    TextView jawaban;
    @BindView(R.id.layout_jawab)
    LinearLayout layoutJawab;
    @BindView(R.id.isi_jawaban)
    EditText isiJawaban;
    @BindView(R.id.kirim)
    ImageButton kirim;

    TanyaJawab tanyaJawab;
    int idTanyaJawab;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toko_jawab_pertanyaan);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        idTanyaJawab = intent.getIntExtra("idTanyaJawab", 0);
        queue = Volley.newRequestQueue(this);
        getDataTanyaJawab();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void getDataTanyaJawab() {
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.setMessage("Mohon Menunggu");
        loading.setIndeterminate(true);
        loading.show();
        String url = UrlUbama.USER_TOKO_TANYA_JAWAB + idTanyaJawab;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                tanyaJawab = new Gson().fromJson(response.toString(), TanyaJawab.class);
                if (tanyaJawab.barang_jasa.gambar.size() > 0) {
                    Picasso.with(TokoJawabPertanyaanActivity.this).load(UrlUbama.URL_IMAGE + tanyaJawab.barang_jasa.gambar.get(0).url_gambar).fit().into(gambarBarang);
                } else {
                    gambarBarang.setImageResource(R.drawable.ic_error_image);
                }
                namaBarang.setText(tanyaJawab.barang_jasa.nama);
                if (!tanyaJawab.penanya.url_profile.equals("")) {
                    Picasso.with(TokoJawabPertanyaanActivity.this).load(UrlUbama.URL_IMAGE + tanyaJawab.penanya.url_profile).fit().into(imagePenanya);
                } else {
                    imagePenanya.setImageResource(R.drawable.ic_user);
                }
                namaPenanya.setText(tanyaJawab.penanya.user.name);
                pertanyaan.setText(tanyaJawab.pertanyaan);
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm");
                Date date = null;
                try {
                    date = inputFormat.parse(tanyaJawab.waktu_tanya);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                waktuTanya.setText(outputFormat.format(date));
                if (tanyaJawab.jawaban != null) {
                    layoutJawaban.setVisibility(View.VISIBLE);
                    if (!tanyaJawab.barang_jasa.toko.url_profile.equals("")) {
                        Picasso.with(TokoJawabPertanyaanActivity.this).load(UrlUbama.URL_IMAGE + tanyaJawab.barang_jasa.toko.url_profile).fit().into(imageToko);
                    } else {
                        imageToko.setImageResource(R.drawable.ic_error_image);
                    }
                    namaToko.setText(tanyaJawab.barang_jasa.toko.nama);
                    jawaban.setText(tanyaJawab.jawaban);
                    try {
                        date = inputFormat.parse(tanyaJawab.waktu_jawab);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    waktuJawab.setText(outputFormat.format(date));
                    layoutJawab.setVisibility(View.GONE);
                } else {
                    layoutJawaban.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
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
        request.setShouldCache(false);
        queue.add(request);
    }

    @OnClick(R.id.kirim)
    public void onKirimClicked() {
        if (isiJawaban.getText().toString().equals("")) {
            isiJawaban.requestFocus();
            Toast.makeText(TokoJawabPertanyaanActivity.this, "Isi jawaban Anda", Toast.LENGTH_SHORT).show();
            return;
        }
        kirimJawaban();
    }

    public void kirimJawaban() {
        String url = UrlUbama.USER_TOKO_JAWAB_TANYA_JAWAB + idTanyaJawab;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject hasil = new JSONObject(response);
                    Boolean tersimpan = hasil.getBoolean("tersimpan");
                    if (tersimpan) {
                        getDataTanyaJawab();
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

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("jawaban", isiJawaban.getText().toString());
                return params;
            }
        };
        request.setShouldCache(false);
        queue.add(request);
    }
}
