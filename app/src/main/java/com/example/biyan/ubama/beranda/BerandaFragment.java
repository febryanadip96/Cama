package com.example.biyan.ubama.beranda;


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

import com.example.biyan.ubama.R;
import com.example.biyan.ubama.UrlUbama;
import com.example.biyan.ubama.UserToken;
import com.example.biyan.ubama.models.Fakultas;
import com.example.biyan.ubama.models.Kategori;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class BerandaFragment extends Fragment {

    RecyclerView recyclerKategori;
    RecyclerView recyclerFakultas;
    RecyclerView.Adapter adapterKategori;
    RecyclerView.LayoutManager layoutManagerKategori;
    RecyclerView.Adapter adapterFakultas;
    RecyclerView.LayoutManager layoutManagerFakultas;
    List<Kategori> kategoriList;
    List<Fakultas> fakultasList;
    RequestQueue queue;

    public static BerandaFragment newInstance() {
        BerandaFragment berandaFragment = new BerandaFragment();
        return berandaFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_beranda, container, false);

        queue = Volley.newRequestQueue(getActivity());

        recyclerKategori = (RecyclerView) rootView.findViewById(R.id.recycler_kategori);
        recyclerFakultas = (RecyclerView) rootView.findViewById(R.id.recycler_fakultas);
        layoutManagerKategori = new GridLayoutManager(getActivity(), 2);
        layoutManagerFakultas = new GridLayoutManager(getActivity(), 2);
        recyclerKategori.setLayoutManager(layoutManagerKategori);
        recyclerFakultas.setLayoutManager(layoutManagerFakultas);

        getKategori();
        getFakultas();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getKategori();
        getFakultas();
    }

    public void getKategori() {
        String url = UrlUbama.KATEGORI;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                kategoriList = new Gson().fromJson(response.toString(), new TypeToken<List<Kategori>>() {
                }.getType());
                adapterKategori = new BerandaKategoriAdapter(kategoriList);
                recyclerKategori.setAdapter(adapterKategori);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Volley BerandaFragment", error.toString());
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

    public void getFakultas() {
        String url = UrlUbama.FAKULTAS;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                fakultasList = new Gson().fromJson(response.toString(), new TypeToken<List<Fakultas>>() {
                }.getType());
                adapterFakultas = new BerandaFakultasAdapter(fakultasList);
                recyclerFakultas.setAdapter(adapterFakultas);
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

}
