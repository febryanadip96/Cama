package com.example.biyan.ubama;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

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

public class LoginActivity extends AppCompatActivity {

    EditText txtEmail;
    EditText txtPassword;
    Button btnLogin;
    ProgressBar loadingLogin;
    RelativeLayout loginView;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginView = (RelativeLayout) findViewById(R.id.loginView);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        loadingLogin = (ProgressBar) findViewById(R.id.loading_login);
        queue = Volley.newRequestQueue(this);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtEmail.getText().toString().equals("") || txtPassword.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Harap masukkan email dan password Anda", Toast.LENGTH_SHORT).show();
                    return;
                }
                loadingLogin.setVisibility(View.VISIBLE);
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", txtEmail.getText().toString());
                params.put("password", txtPassword.getText().toString());
                String url = UrlUbama.login;
                JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loadingLogin.setVisibility(View.GONE);
                        try {
                            if(!response.isNull("message")){
                                Toast.makeText(getApplicationContext(),response.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                            else if(!(response.isNull("access_token") || response.isNull("token_type"))){
                                UserToken.setToken(getApplicationContext(),response.getString("token_type")+" "+response.getString("access_token"));
                                if(!UserToken.getToken(getApplicationContext()).equals("")){
                                    Toast.makeText(getApplicationContext(), "Login sukses", Toast.LENGTH_SHORT).show();
                                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(mainIntent);
                                    finish();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "Login gagal", Toast.LENGTH_SHORT).show();
                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadingLogin.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(loginRequest);
            }
        });
    }
}
