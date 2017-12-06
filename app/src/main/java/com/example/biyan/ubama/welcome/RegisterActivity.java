package com.example.biyan.ubama.welcome;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.biyan.ubama.gps.AlamatActivity;
import com.example.biyan.ubama.R;
import com.example.biyan.ubama.UrlUbama;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.nama_lengkap)
    EditText namaLengkap;
    @BindView(R.id.layout_nama_lengkap)
    TextInputLayout layoutNamaLengkap;
    @BindView(R.id.laki_laki)
    RadioButton lakiLaki;
    @BindView(R.id.perempuan)
    RadioButton perempuan;
    @BindView(R.id.jenis_kelamin)
    RadioGroup jenisKelamin;
    @BindView(R.id.telepon)
    EditText telepon;
    @BindView(R.id.layout_telepon)
    TextInputLayout layoutTelepon;
    @BindView(R.id.alamat)
    EditText alamat;
    @BindView(R.id.alamat_map)
    EditText alamatMap;
    @BindView(R.id.layout_alamat_map)
    TextInputLayout layoutAlamatMap;
    @BindView(R.id.layout_alamat)
    TextInputLayout layoutAlamat;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.layout_email)
    TextInputLayout layoutEmail;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.layout_password)
    TextInputLayout layoutPassword;
    @BindView(R.id.password_konfirmasi)
    EditText passwordKonfirmasi;
    @BindView(R.id.layout_password_konfirmasi)
    TextInputLayout layoutPasswordKonfirmasi;
    @BindView(R.id.register)
    Button register;

    RequestQueue queue;
    int jenis;
    final static int MAP_REQUEST = 1;
    double latitude = 0, longitude = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        queue = Volley.newRequestQueue(this);
        lakiLaki.setChecked(true);
        jenis = 1;
        alamatMap.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                alamatMap.setShowSoftInputOnFocus(false);
                Intent intent = new Intent(RegisterActivity.this, AlamatActivity.class);
                startActivityForResult(intent, MAP_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle res;
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case MAP_REQUEST:
                    res = data.getExtras();
                    latitude = res.getDouble("latitude");
                    longitude = res.getDouble("longitude");
                    alamatMap.setText(res.getString("alamatMap"));
                    break;
            }
        }
    }

    @OnClick(R.id.register)
    public void CekDataRegister() {
        if (namaLengkap.getText().toString().equals("")) {
            layoutNamaLengkap.setError("Nama Lengkap harus diisi");
            namaLengkap.requestFocus();
            return;
        } else {
            layoutNamaLengkap.setError(null);
        }
        if (telepon.getText().toString().equals("")) {
            layoutTelepon.setError("Nomor Telepon harus diisi");
            telepon.requestFocus();
            return;
        } else {
            layoutTelepon.setError(null);
        }
        if (alamat.getText().toString().equals("")) {
            layoutAlamat.setError("Alamat harus diisi");
            alamat.requestFocus();
            return;
        } else {
            layoutAlamat.setError(null);
        }
        if(longitude == 0 || latitude == 0 || alamatMap.getText().toString().equals("")){
            layoutAlamatMap.setError("Harap memasukkan posisi Anda");
        }
        else{
            layoutAlamatMap.setError(null);
        }
        if (email.getText().toString().equals("")) {
            layoutEmail.setError("Email harus diisi");
            email.requestFocus();
            return;
        } else {
            layoutEmail.setError(null);
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            layoutEmail.setError("Isikan dengan email yang valid");
            email.requestFocus();
            return;
        } else {
            layoutEmail.setError(null);
        }
        if (password.getText().toString().equals("")) {
            layoutPassword.setError("Password harus diisi");
            password.requestFocus();
            return;
        } else {
            password.setError(null);
        }
        if (passwordKonfirmasi.getText().toString().equals("")) {
            layoutPasswordKonfirmasi.setError("Konfirmasi password harus diisi");
            passwordKonfirmasi.requestFocus();
            return;
        } else {
            layoutPasswordKonfirmasi.setError(null);
        }
        if (!password.getText().toString().equals(passwordKonfirmasi.getText().toString())) {
            layoutPasswordKonfirmasi.setError("Konfirmasi password tidak sama dengan password");
            passwordKonfirmasi.requestFocus();
            return;
        } else {
            layoutPasswordKonfirmasi.setError(null);
        }

        jenis = 1;
        int checkedRadioButtonId = jenisKelamin.getCheckedRadioButtonId();

        if (checkedRadioButtonId == R.id.laki_laki) {
            jenis = 1;
        } else if (checkedRadioButtonId == R.id.perempuan) {
            jenis = 2;
        }

        ProsesRegister();
    }

    public void ProsesRegister() {
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setIndeterminate(true);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.setMessage("Proses Register");
        loading.show();

        Map<String, String> params = new HashMap<>();
        params.put("name", namaLengkap.getText().toString());
        params.put("email", email.getText().toString());
        params.put("password", password.getText().toString());
        params.put("jenis_kelamin", jenis + "");
        params.put("telepon", telepon.getText().toString());
        params.put("alamat", alamat.getText().toString());
        params.put("alamat_map", alamatMap.getText().toString());
        params.put("latitude", latitude+"");
        params.put("longitude", longitude+"");

        String url = UrlUbama.REGISTER;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.dismiss();
                try {
                    if (!response.isNull("message")) {
                        Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    if (!response.isNull("back")) {
                        if (response.getBoolean("back")) {
                            finish();
                        }
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
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
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


    @OnClick(R.id.alamat_map)
    public void onAlamatMapClicked() {
        alamatMap.setShowSoftInputOnFocus(false);
        Intent intent = new Intent(RegisterActivity.this, AlamatActivity.class);
        startActivityForResult(intent,MAP_REQUEST);
    }
}
