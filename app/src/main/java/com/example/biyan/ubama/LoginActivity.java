package com.example.biyan.ubama;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.login)
    Button login;
    RequestQueue queue;
    @BindView(R.id.loginView)
    RelativeLayout loginView;
    private ProgressDialog loadingLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        queue = Volley.newRequestQueue(this);
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    @OnClick(R.id.login)
    public void CekDataLogin() {
        if (email.getText().toString().equals("") || password.getText().toString().equals("")) {
            Snackbar snackbar = Snackbar
                    .make(loginView, "Harap masukkan email dan password Anda", Snackbar.LENGTH_LONG);
            snackbar.show();
            //Toast.makeText(getApplicationContext(), "Harap masukkan email dan password Anda", Toast.LENGTH_SHORT).show();
            return;
        }
        ProsesLogin();
    }

    private void ProsesLogin() {
        loadingLogin = new ProgressDialog(this);
        loadingLogin.setIndeterminate(true);
        loadingLogin.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadingLogin.setMessage("Proses Login");
        loadingLogin.show();
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", email.getText().toString());
        params.put("password", password.getText().toString());
        String url = UrlUbama.login;
        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loadingLogin.dismiss();
                try {
                    if (!response.isNull("message")) {
                        //Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                        Snackbar snackbar = Snackbar
                                .make(loginView, response.getString("message"), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    } else if (!(response.isNull("access_token") || response.isNull("token_type"))) {
                        UserToken.setToken(getApplicationContext(), response.getString("token_type") + " " + response.getString("access_token"));
                        if (!UserToken.getToken(getApplicationContext()).equals("")) {
                            Toast.makeText(getApplicationContext(), "Login sukses", Toast.LENGTH_SHORT).show();
                            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(mainIntent);
                            finish();
                        } else {
                            Snackbar snackbar = Snackbar
                                    .make(loginView, "Login gagal", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            //Toast.makeText(getApplicationContext(), "Login gagal", Toast.LENGTH_SHORT).show();
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingLogin.dismiss();
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                return params;
            }
        };
        queue.add(loginRequest);
    }


}
