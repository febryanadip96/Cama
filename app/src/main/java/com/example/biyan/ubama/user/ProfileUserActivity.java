package com.example.biyan.ubama.user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biyan.ubama.R;
import com.example.biyan.ubama.UrlCama;
import com.example.biyan.ubama.UserToken;
import com.example.biyan.ubama.models.User;
import com.google.gson.Gson;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileUserActivity extends AppCompatActivity {

    @BindView(R.id.image_user)
    CircleImageView imageUser;
    @BindView(R.id.nama)
    TextView nama;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.telepon)
    TextView telepon;
    @BindView(R.id.alamat)
    TextView alamat;
    @BindView(R.id.pengaturan)
    Button pengaturan;
    @BindView(R.id.ubah_password)
    Button ubahPassword;

    RequestQueue queue;
    User user;

    final static int RESULT_PENGATURAN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        queue = Volley.newRequestQueue(this);
        getUserData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle res;
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RESULT_PENGATURAN:
                    getUserData();
                    break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.setResult(RESULT_OK);
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void getUserData(){
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.setMessage("Mohon Menunggu");
        loading.setIndeterminate(true);
        loading.show();
        String url = UrlCama.USER;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                user = new Gson().fromJson(response.toString(), User.class);
                Picasso.with(ProfileUserActivity.this).load(UrlCama.URL_IMAGE+user.pengguna.url_profile).memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).fit().into(imageUser);
                nama.setText(user.name);
                email.setText(user.email);
                telepon.setText(user.pengguna.telepon);
                alamat.setText(user.pengguna.alamat);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Log.e("Error Volley", error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", UserToken.getToken(getApplicationContext()));
                params.put("Accept", "application/json");
                return params;
            }
        };
        request.setShouldCache(false);
        queue.add(request);
    }

    @OnClick(R.id.pengaturan)
    public void onPengaturanClicked() {
        Intent intent = new Intent(ProfileUserActivity.this, PengaturanUserActivity.class);
        startActivityForResult(intent,RESULT_PENGATURAN);
    }

    @OnClick(R.id.ubah_password)
    public void onUbahPasswordClicked() {
        Intent intent = new Intent(ProfileUserActivity.this, UbahPasswordUserActivity.class);
        startActivity(intent);
    }
}
