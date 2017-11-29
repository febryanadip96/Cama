package com.example.biyan.ubama.toko;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TokoAlasanDitolakActivity extends AppCompatActivity {

    @BindView(R.id.alasan)
    EditText alasan;
    @BindView(R.id.simpan)
    Button simpan;

    int idPesanan;
    RequestQueue queue;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toko_alasan_ditolak);
        ButterKnife.bind(this);
        queue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        idPesanan = intent.getIntExtra("idPesanan", 0);

    }

    @OnClick(R.id.simpan)
    public void onViewClicked() {
        loading = new ProgressDialog(this);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.setMessage("Mohon Menunggu");
        loading.setIndeterminate(true);
        loading.show();
        String url = UrlUbama.USER_TOKO_TOLAK_PESANAN + idPesanan;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                try {
                    JSONObject hasil = new JSONObject(response);
                    if(hasil.getBoolean("tersimpan")){
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

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("alasan", alasan.getText()+"");
                return params;
            }
        };
        request.setShouldCache(false);
        queue.add(request);
    }
}
