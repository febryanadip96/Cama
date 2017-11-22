package com.example.biyan.ubama;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.biyan.ubama.adapters.TokoProdukAdapter;
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
public class TokoProdukJasaFragment extends Fragment {


    @BindView(R.id.recycler)
    RecyclerView recycler;
    Unbinder unbinder;

    RequestQueue queue;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    List<BarangJasa> jasaList;

    public static TokoProdukJasaFragment newInstance() {
        TokoProdukJasaFragment tokoProdukJasaFragment = new TokoProdukJasaFragment();
        return tokoProdukJasaFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_toko_produk_jasa, container, false);
        unbinder = ButterKnife.bind(this, view);
        queue = Volley.newRequestQueue(getContext());
        layoutManager = new GridLayoutManager(getActivity(), 2);
        recycler.setLayoutManager(layoutManager);
        getProdukJasa();
        return view;
    }

    public void getProdukJasa(){
        queue = Volley.newRequestQueue(getActivity());
        String url = UrlUbama.USER_TOKO_PRODUK_JASA;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                jasaList = new Gson().fromJson(response.toString(), new TypeToken<List<BarangJasa>>() {
                }.getType());
                adapter = new TokoProdukAdapter(jasaList);
                recycler.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
