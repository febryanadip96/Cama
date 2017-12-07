package com.example.biyan.ubama.pesanan;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.biyan.ubama.R;
import com.example.biyan.ubama.UrlUbama;
import com.example.biyan.ubama.UserToken;
import com.example.biyan.ubama.models.Pesanan;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PesananActivity extends AppCompatActivity {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    @BindView(R.id.empty)
    TextView empty;

    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    List<Pesanan> pesananList;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan);
        ButterKnife.bind(this);
        queue = Volley.newRequestQueue(this);
        this.setTitle("Pesanan");
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(layoutManager);
        swiperefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        getPesanan();
                    }
                }
        );
        getPesanan();
    }

    public void getPesanan() {
        String url = UrlUbama.USER_PESANAN;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                swiperefresh.setRefreshing(false);
                pesananList = new Gson().fromJson(response.toString(), new TypeToken<List<Pesanan>>() {
                }.getType());
                adapter = new PesananAdapter(pesananList);
                recycler.setAdapter(adapter);
                if (!(pesananList.size() > 0)) {
                    recycler.setVisibility(View.GONE);
                    swiperefresh.setVisibility(View.GONE);
                    empty.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swiperefresh.setRefreshing(false);
                Log.e("Error Volley", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", UserToken.getToken(getApplication()));
                params.put("Accept", "application/json");
                return params;
            }
        };
        request.setShouldCache(false);
        queue.add(request);
    }
}
