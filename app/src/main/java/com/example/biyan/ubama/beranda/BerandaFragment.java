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
import com.example.biyan.ubama.UrlCama;
import com.example.biyan.ubama.UserToken;
import com.example.biyan.ubama.models.Fakultas;
import com.example.biyan.ubama.models.Kategori;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collection;
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
        kategoriList = new ArrayList<>();
        fakultasList = new ArrayList<>();
        adapterKategori = new BerandaKategoriAdapter(kategoriList);
        adapterFakultas = new BerandaFakultasAdapter(fakultasList);
        recyclerKategori.setAdapter(adapterKategori);
        recyclerFakultas.setAdapter(adapterFakultas);

        getKategori();
        getFakultas();

        return rootView;
    }

    public void getKategori() {
        String url = UrlCama.KATEGORI;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                kategoriList.clear();
                kategoriList.addAll((Collection<? extends Kategori>) new Gson().fromJson(response.toString(), new TypeToken<List<Kategori>>() {
                }.getType()));
                adapterKategori.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Volley", error.toString());
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
        String url = UrlCama.FAKULTAS;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                fakultasList.clear();
                fakultasList.addAll((Collection<? extends Fakultas>) new Gson().fromJson(response.toString(), new TypeToken<List<Fakultas>>() {
                }.getType()));
                adapterFakultas.notifyDataSetChanged();
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
