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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment {
    private RecyclerView recyclerFavoritToko;
    private RecyclerView.Adapter adapterFavoritToko;
    private RecyclerView.LayoutManager layoutManagerFavoritToko;
    private List<Toko> tokoList;
    RequestQueue queue;

    public static FeedFragment newInstance() {
        // Required empty public constructor
        FeedFragment feedFragment = new FeedFragment();
        return feedFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_feed, container, false);
        queue = Volley.newRequestQueue(getActivity());

        recyclerFavoritToko = (RecyclerView) rootView.findViewById(R.id.recycler_favoritToko);
        layoutManagerFavoritToko = new GridLayoutManager(getActivity(), 1);
        recyclerFavoritToko.setLayoutManager(layoutManagerFavoritToko);

        getFeed();
        return rootView;
    }

    private void getFeed() {
        String url = UrlUbama.UserFeed;
        JsonArrayRequest feedRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                tokoList = new Gson().fromJson(response.toString(), new TypeToken<List<Toko>>() {
                }.getType());
                adapterFavoritToko = new FavoritTokoAdapter(tokoList);
                recyclerFavoritToko.setAdapter(adapterFavoritToko);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
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
        queue.add(feedRequest);
    }
}
