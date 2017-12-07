package com.example.biyan.ubama.toko;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.biyan.ubama.models.BarangJasa;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class TokoProdukBarangFragment extends Fragment {


    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    @BindView(R.id.empty)
    TextView empty;
    Unbinder unbinder;

    RequestQueue queue;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    List<BarangJasa> barangList;

    public static TokoProdukBarangFragment newInstance() {
        TokoProdukBarangFragment tokoProdukBarangFragment = new TokoProdukBarangFragment();
        return tokoProdukBarangFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_toko_produk_barang, container, false);
        unbinder = ButterKnife.bind(this, view);
        queue = Volley.newRequestQueue(getContext());
        layoutManager = new GridLayoutManager(getActivity(), 2);
        recycler.setLayoutManager(layoutManager);
        swiperefresh.setOnRefreshListener(
            new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    getProdukBarang();
                }
            }
        );
        getProdukBarang();
        return view;
    }

    public void getProdukBarang() {
        queue = Volley.newRequestQueue(getActivity());
        String url = UrlUbama.USER_TOKO_PRODUK_BARANG;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                swiperefresh.setRefreshing(false);
                barangList = new Gson().fromJson(response.toString(), new TypeToken<List<BarangJasa>>() {
                }.getType());
                adapter = new TokoProdukAdapter(barangList);
                recycler.setAdapter(adapter);
                if (!(barangList.size() > 0)) {
                    recycler.setVisibility(View.GONE);
                    empty.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swiperefresh.setRefreshing(false);
                Log.e("Error Volley ", error.toString());
                return;
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", UserToken.getToken(getContext()));
                params.put("Accept", "application/json");
                return params;
            }
        };
        request.setShouldCache(false);
        queue.add(request);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
