package com.example.biyan.ubama;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.biyan.ubama.adapters.FavoritAdapter;
import com.example.biyan.ubama.models.Favorit;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritFragment extends Fragment {
    private RecyclerView recyclerFavorit;
    private RecyclerView.Adapter adapterFavorit;
    private RecyclerView.LayoutManager layoutManagerFavorit;
    private List<Favorit> favoritList;
    RequestQueue queue;
    private TextView empty;

    public static FavoritFragment newInstance() {
        FavoritFragment favoritFragment = new FavoritFragment();
        return favoritFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_favorit, container, false);
        queue = Volley.newRequestQueue(getActivity());

        empty = (TextView) rootView.findViewById(R.id.empty);

        recyclerFavorit = (RecyclerView) rootView.findViewById(R.id.recycler_favorit);
        layoutManagerFavorit = new GridLayoutManager(getActivity(), 2);
        recyclerFavorit.setLayoutManager(layoutManagerFavorit);

        getFavorit();

        return rootView;
    }

    public void getFavorit() {
        String url = UrlUbama.USER_FAVORIT_BARANGJASA;
        JsonArrayRequest favoritBarangJasaRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                favoritList = new Gson().fromJson(response.toString(), new TypeToken<List<Favorit>>() {
                }.getType());
                adapterFavorit = new FavoritAdapter(favoritList);
                recyclerFavorit.setAdapter(adapterFavorit);
                if(!(favoritList.size()>0)){
                    empty.setVisibility(View.VISIBLE);
                    recyclerFavorit.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Volley FavoritFragment", error.toString());
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
        queue.add(favoritBarangJasaRequest);
    }
}
