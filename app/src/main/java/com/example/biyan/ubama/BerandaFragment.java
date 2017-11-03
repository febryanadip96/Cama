package com.example.biyan.ubama;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class BerandaFragment extends Fragment {

    private RecyclerView mRecyclerViewKategori;
    private RecyclerView.Adapter mAdapterKategori;
    private RecyclerView.LayoutManager mLayoutManagerKategori;
    private RecyclerView mRecyclerViewFakultas;
    private RecyclerView.Adapter mAdapterFakultas;
    private RecyclerView.LayoutManager mLayoutManagerFakultas;
    private List<Kategori> kategoriList;
    private List<Fakultas> fakultasList;
    RequestQueue queue;

    public static BerandaFragment newInstance() {
        // Required empty public constructor
        BerandaFragment berandaFragment = new BerandaFragment();
        return berandaFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_beranda, container, false);

        queue = Volley.newRequestQueue(getActivity());
        mRecyclerViewKategori = (RecyclerView) rootView.findViewById(R.id.recycler_kategori);
        mRecyclerViewFakultas = (RecyclerView) rootView.findViewById(R.id.recycler_fakultas);
        getKategori();
        getFakultas();

        return rootView;
    }

    private void getKategori(){
        queue = Volley.newRequestQueue(getActivity());
        String url = UrlUbama.BerandaKategori;
        JsonArrayRequest berandaKategoriRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                    try {
                        kategoriList = new ArrayList<Kategori>();
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject kategori_item = response.getJSONObject(i);
                            //
                            int id = kategori_item.getInt("id");
                            String nama = kategori_item.getString("nama");
                            String url = kategori_item.getString("url_gambar");
                            kategoriList.add(new Kategori(id, nama, url));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mLayoutManagerKategori = new GridLayoutManager(getActivity(),2);
                    mRecyclerViewKategori.setLayoutManager(mLayoutManagerKategori);
                    mAdapterKategori = new KategoriAdapter(kategoriList);
                    mRecyclerViewKategori.setAdapter(mAdapterKategori);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", UserToken.getToken(getContext()));
                params.put("Accept", "application/json");
                return params;
            }
        };
        queue.add(berandaKategoriRequest);
    }

    private void getFakultas(){
        queue = Volley.newRequestQueue(getActivity());
        String url = UrlUbama.BerandaFakultas;
        JsonArrayRequest berandaFakultasRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    fakultasList = new ArrayList<Fakultas>();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject fakultas_item = response.getJSONObject(i);
                        //
                        int id = fakultas_item.getInt("id");
                        String nama = fakultas_item.getString("nama");
                        String url_gambar = fakultas_item.getString("url_gambar");
                        fakultasList.add(new Fakultas(id, nama, url_gambar));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mLayoutManagerFakultas = new GridLayoutManager(getActivity(),3);
                mRecyclerViewFakultas.setLayoutManager(mLayoutManagerFakultas);
                mAdapterFakultas = new FakultasAdapter(fakultasList);
                mRecyclerViewFakultas.setAdapter(mAdapterFakultas);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", UserToken.getToken(getContext()));
                params.put("Accept", "application/json");
                return params;
            }
        };
        queue.add(berandaFakultasRequest);
    }

}
