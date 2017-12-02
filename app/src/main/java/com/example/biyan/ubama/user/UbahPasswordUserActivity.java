package com.example.biyan.ubama.user;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UbahPasswordUserActivity extends AppCompatActivity {

    @BindView(R.id.password_lama)
    EditText passwordLama;
    @BindView(R.id.password_baru)
    EditText passwordBaru;
    @BindView(R.id.konfirmasi_password_baru)
    EditText konfirmasiPasswordBaru;
    @BindView(R.id.simpan)
    Button simpan;
    @BindView(R.id.layout_password_lama)
    TextInputLayout layoutPasswordLama;
    @BindView(R.id.layout_password_baru)
    TextInputLayout layoutPasswordBaru;
    @BindView(R.id.layout_password_konfirmasi)
    TextInputLayout layoutPasswordKonfirmasi;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_password_user);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Ganti Password");
        queue = Volley.newRequestQueue(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.simpan)
    public void onViewClicked() {
        if (passwordLama.getText().toString().equals("")) {
            layoutPasswordLama.setError("Password lama harus diisi");
            passwordLama.requestFocus();
            return;
        }
        else{
            layoutPasswordLama.setError(null);
        }
        if( passwordBaru.getText().toString().equals("")){
            layoutPasswordBaru.setError("Password baru harus diisi");
            passwordBaru.requestFocus();
            return;
        }
        else{
            layoutPasswordBaru.setError(null);
        }
        if(konfirmasiPasswordBaru.getText().toString().equals("")){
            layoutPasswordKonfirmasi.setError("Konfirmasi password baru harus diisi");
            konfirmasiPasswordBaru.requestFocus();
            return;
        }
        else{
            layoutPasswordKonfirmasi.setError(null);
        }
        if (!passwordBaru.getText().toString().equals(konfirmasiPasswordBaru.getText().toString())) {
            layoutPasswordKonfirmasi.setError("Konfirmasi password baru tidak sama dengan password baru");
            return;
        }
        else{
            layoutPasswordKonfirmasi.setError(null);
        }
        requestUbahPassword();
    }

    public void requestUbahPassword() {
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.setMessage("Mohon Menunggu");
        loading.setIndeterminate(true);
        loading.show();
        String url = UrlUbama.USER_UBAH_PASSWORD;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject hasil = new JSONObject(response);
                    Boolean ubah = hasil.getBoolean("ubah");
                    if (ubah) {
                        Toast.makeText(UbahPasswordUserActivity.this, "Password Anda berhasil diubah", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(UbahPasswordUserActivity.this, "Password lama Anda salah", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                loading.dismiss();
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

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("passwordLama", passwordLama.getText().toString());
                params.put("passwordBaru", passwordBaru.getText().toString());
                return params;
            }
        };
        request.setShouldCache(false);
        queue.add(request);
    }
}
