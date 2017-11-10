package com.example.biyan.ubama;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomeActivity extends AppCompatActivity {

    @BindView(R.id.btnMasuk)
    Button btnMasuk;
    @BindView(R.id.btnDaftar)
    Button btnDaftar;

    public static Activity welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        welcome = this;
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnMasuk, R.id.btnDaftar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnMasuk:
                Intent masukActivity = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(masukActivity);
                break;
            case R.id.btnDaftar:
                Intent daftarActivity = new Intent(WelcomeActivity.this, RegisterActivity.class);
                startActivity(daftarActivity);
                break;
        }
    }
}
