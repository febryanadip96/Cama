package com.example.biyan.ubama;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class PesananFragment extends Fragment {
    private RecyclerView mRecyclerViewPesanan;
    private RecyclerView.Adapter mAdapterPesanan;
    private RecyclerView.LayoutManager mLayoutManagerPesanan;
    private List<Pesanan> pesananList;
    RequestQueue queue;

    public static PesananFragment newInstance() {
        PesananFragment pesananFragment = new PesananFragment();
        return pesananFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_pesanan, container, false);
        queue = Volley.newRequestQueue(getActivity());
        getActivity().setTitle("Pesanan");
        mRecyclerViewPesanan = (RecyclerView) rootView.findViewById(R.id.recycler_pesanan);
        mLayoutManagerPesanan = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mRecyclerViewPesanan.setLayoutManager(mLayoutManagerPesanan);
        getPesanan();
        return rootView;
    }

    private void getPesanan(){
        queue = Volley.newRequestQueue(getActivity());
        String url = UrlUbama.UserPesanan;
        JsonArrayRequest feedRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                pesananList = new Gson().fromJson(response.toString(), new TypeToken<List<Pesanan>>() {
                }.getType());
                mAdapterPesanan = new PesananAdapter(pesananList);
                mRecyclerViewPesanan.setAdapter(mAdapterPesanan);
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
        queue.add(feedRequest);
    }

}
