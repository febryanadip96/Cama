package com.example.biyan.ubama;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
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

public class RegisterActivity extends AppCompatActivity {

    EditText txtNamaLengkap;
    RadioGroup rdoGrpJenisKelamin;
    EditText txtTelepon;
    EditText txtAlamat;
    EditText txtEmail;
    EditText txtPassword;
    EditText txtPasswordConfirm;
    Button btnRegister;
    ProgressBar loadingRegister;
    RequestQueue queue;
    int jenis_kelamin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtNamaLengkap = (EditText) findViewById(R.id.txtNamaLengkap);
        rdoGrpJenisKelamin = (RadioGroup) findViewById(R.id.rdoGrpJenisKelamin);
        rdoGrpJenisKelamin.check(R.id.rdoLakiLaki);
        txtTelepon = (EditText) findViewById(R.id.txtTelepon);
        txtAlamat = (EditText) findViewById(R.id.txtAlamat);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        txtPasswordConfirm = (EditText) findViewById(R.id.txtPasswordConfirm);
        txtPasswordConfirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        loadingRegister = (ProgressBar) findViewById(R.id.loading_register);
        queue = Volley.newRequestQueue(this);
        btnRegister = (Button) findViewById(R.id.btnRegister);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CekDataRegister();
            }
        });
    }

    private void CekDataRegister(){
        if(txtNamaLengkap.getText().toString().equals("") || txtTelepon.getText().toString().equals("") || txtAlamat.getText().toString().equals("") || txtEmail.getText().toString().equals("") ){
            Toast.makeText(getApplicationContext(),"Harap isi data dengan lengkap",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!txtPassword.getText().toString().equals(txtPasswordConfirm.getText().toString())){
            Toast.makeText(getApplicationContext(), "Konfirmasi Password tidak sama dengan Password", Toast.LENGTH_SHORT).show();
            txtPassword.requestFocus();
            return;
        }
        jenis_kelamin=0;
        int checkedRadioButtonId = rdoGrpJenisKelamin.getCheckedRadioButtonId();
        if (checkedRadioButtonId == R.id.rdoLakiLaki) {
            jenis_kelamin=0;
        }
        else if (checkedRadioButtonId == R.id.rdoPerempuan) {
            jenis_kelamin=1;
        }
        ProsesRegister();
    }

    private void ProsesRegister(){
        loadingRegister.setVisibility(View.VISIBLE);
        Map<String, String> params = new HashMap<>();
        params.put("name",txtNamaLengkap.getText().toString());
        params.put("email", txtEmail.getText().toString());
        params.put("password", txtPassword.getText().toString());
        params.put("jenis_kelamin", jenis_kelamin+"");
        params.put("telepon", txtTelepon.getText().toString());
        params.put("alamat",txtAlamat.getText().toString());
        String url = UrlUbama.Register;
        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loadingRegister.setVisibility(View.GONE);
                try {
                    if(!response.isNull("message")){
                        Toast.makeText(getApplicationContext(),response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    if(!response.isNull("back")){
                        if(response.getBoolean("back")){
                            RegisterActivity.this.finish();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingRegister.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
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
