package com.example.biyan.ubama.welcome;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.example.biyan.ubama.MainActivity;
import com.example.biyan.ubama.R;
import com.example.biyan.ubama.UrlUbama;
import com.example.biyan.ubama.UserToken;
import com.example.biyan.ubama.messaging.SendToken;
import com.google.firebase.iid.FirebaseInstanceId;

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
    @BindView(R.id.loginView)
    LinearLayout loginView;
    @BindView(R.id.lupa_password)
    TextView lupaPassword;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        queue = Volley.newRequestQueue(this);
    }

    @OnClick(R.id.login)
    public void onLoginClicked() {
        if (email.getText().toString().equals("") || password.getText().toString().equals("")) {
            Snackbar snackbar = Snackbar
                    .make(loginView, "Harap masukkan email dan password Anda", Snackbar.LENGTH_LONG);
            snackbar.show();
            return;
        }
        ProsesLogin();
    }

    public void ProsesLogin() {
        final ProgressDialog loadingLogin = new ProgressDialog(this);
        loadingLogin.setIndeterminate(true);
        loadingLogin.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadingLogin.setMessage("Proses Login");
        loadingLogin.show();
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", email.getText().toString());
        params.put("password", password.getText().toString());
        String url = UrlUbama.LOGIN;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
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
                            SendToken.sendRegistrationToServer(LoginActivity.this, FirebaseInstanceId.getInstance().getToken() + "");
                            startActivity(mainIntent);
                            WelcomeActivity.welcome.finish();
                            finish();
                        } else {
                            Snackbar snackbar = Snackbar
                                    .make(loginView, "Login gagal", Snackbar.LENGTH_LONG);
                            snackbar.show();
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
                Log.e("Error Volley Login", error.toString());
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


    @OnClick(R.id.lupa_password)
    public void onLupaPasswordClicked() {
        Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
    }
}
