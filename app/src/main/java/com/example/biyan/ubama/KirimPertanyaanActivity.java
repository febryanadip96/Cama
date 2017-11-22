package com.example.biyan.ubama;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.biyan.ubama.models.BarangJasa;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class KirimPertanyaanActivity extends AppCompatActivity {

    @BindView(R.id.image_barang)
    ImageView imageBarang;
    @BindView(R.id.nama_barang)
    TextView namaBarang;
    @BindView(R.id.pertanyaan)
    EditText pertanyaan;
    @BindView(R.id.kirim)
    Button kirim;

    int idBarangJasa;
    RequestQueue queue;
    BarangJasa barangJasa;
    ProgressDialog loadingLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kirim_pertanyaan);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        idBarangJasa = intent.getIntExtra("idBarangJasa", 0);
        queue = Volley.newRequestQueue(this);
        getDataBarangJasa();
    }

    public void getDataBarangJasa() {
        String url = UrlUbama.BARANG_JASA + idBarangJasa;
        final JsonObjectRequest barangJasaRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                barangJasa = new Gson().fromJson(response.toString(), BarangJasa.class);
                if (barangJasa.gambar.size() > 0) {
                    Picasso.with(KirimPertanyaanActivity.this).load(UrlUbama.URL_IMAGE + barangJasa.gambar.get(0).url_gambar).into(imageBarang);
                }
                namaBarang.setText(barangJasa.nama);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Volley KirimPertanyaanActivity", error.toString());
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

    @OnClick(R.id.kirim)
    public void onViewClicked() {
        loadingLogin = new ProgressDialog(this);
        loadingLogin.setIndeterminate(true);
        loadingLogin.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadingLogin.setMessage("Proses Login");
        loadingLogin.show();
        Map<String, String> params = new HashMap<String, String>();
        params.put("pertanyaan", pertanyaan.getText().toString());
        String url = UrlUbama.KIRIM_PERTANYAAN_BARANG_JASA + idBarangJasa;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loadingLogin.dismiss();
                try {
                    if (response.getBoolean("terkirim")) {
                        Toast.makeText(KirimPertanyaanActivity.this, "Pertanyaan sudah terkirim", Toast.LENGTH_LONG).show();
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingLogin.dismiss();
                Log.e("Error Volley Login", error.toString());
                Toast.makeText(KirimPertanyaanActivity.this, "Pertanyaan gagal terkirim", Toast.LENGTH_LONG).show();
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
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                0,  // maxNumRetries = 0 means no retry
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }
}
