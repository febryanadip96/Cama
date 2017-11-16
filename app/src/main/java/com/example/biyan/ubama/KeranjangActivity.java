package com.example.biyan.ubama;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.biyan.ubama.adapters.KeranjangAdapter;
import com.example.biyan.ubama.models.Keranjang;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class KeranjangActivity extends AppCompatActivity {
    private RecyclerView recyclerKeranjang;
    private TextView totalHarga;
    private TextView empty;
    private LinearLayout layoutLanjutPesan;
    private RecyclerView.Adapter adapterKeranjang;
    private RecyclerView.LayoutManager layoutManagerKeranjang;
    private ProgressDialog loading;
    private RequestQueue queue;
    private List<Keranjang> keranjangList;
    private int tampungTotalHarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);
        recyclerKeranjang = (RecyclerView) findViewById(R.id.recycler_keranjang);
        totalHarga = (TextView) findViewById(R.id.total_harga);
        empty = (TextView) findViewById(R.id.empty);
        layoutLanjutPesan = (LinearLayout) findViewById(R.id.layout_lanjut_pesan);
        queue = Volley.newRequestQueue(this);
        layoutManagerKeranjang = new GridLayoutManager(this, 1);
        recyclerKeranjang.setLayoutManager(layoutManagerKeranjang);
        getKeranjang();
    }

    public void getKeranjang(){
        loading = new ProgressDialog(this);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.setMessage("Mohon Menunggu");
        loading.setIndeterminate(true);
        loading.show();
        queue = Volley.newRequestQueue(this);
        String url = UrlUbama.USERKERANJANG;
        JsonObjectRequest keranjangRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    keranjangList = new Gson().fromJson(response.getJSONArray("toko").toString(), new TypeToken<List<Keranjang>>() {
                    }.getType());
                    tampungTotalHarga = response.getInt("total");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(keranjangList.size()>0){
                    layoutLanjutPesan.setVisibility(View.VISIBLE);
                    adapterKeranjang = new KeranjangAdapter(keranjangList);
                    recyclerKeranjang.setAdapter(adapterKeranjang);
                    NumberFormat currency = NumberFormat.getInstance(Locale.GERMANY);
                    totalHarga.setText("Rp. " + currency.format(tampungTotalHarga).toString());
                }
                else{
                    layoutLanjutPesan.setVisibility(View.GONE);
                    empty.setVisibility(View.VISIBLE);
                    recyclerKeranjang.setVisibility(View.GONE);
                }
                loading.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Log.e("Error Volley KeranjangActivity", error.toString());
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
        queue.add(keranjangRequest);
    }
}
