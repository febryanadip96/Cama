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
public class FavoritFragment extends Fragment {
    private RecyclerView mRecyclerViewFavorit;
    private RecyclerView.Adapter mAdapterFavorit;
    private RecyclerView.LayoutManager mLayoutManagerFavorit;
    private List<BarangJasa> barangJasaList;
    RequestQueue queue;

    public static FavoritFragment newInstance() {
        // Required empty public constructor
        FavoritFragment favoritFragment = new FavoritFragment();
        return favoritFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_favorit, container, false);
        queue = Volley.newRequestQueue(getActivity());
        mRecyclerViewFavorit = (RecyclerView) rootView.findViewById(R.id.recycler_favorit);
        getFavorit();
        return rootView;
    }

    public void getFavorit(){
        queue = Volley.newRequestQueue(getActivity());
        String url = UrlUbama.UserFavorit;
        JsonArrayRequest feedRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                barangJasaList = new Gson().fromJson(response.toString(), new TypeToken<List<BarangJasa>>() {
                }.getType());
                mLayoutManagerFavorit = new GridLayoutManager(getActivity(),2);
                mRecyclerViewFavorit.setLayoutManager(mLayoutManagerFavorit);
                mAdapterFavorit = new FavoritAdapter(barangJasaList);
                mRecyclerViewFavorit.setAdapter(mAdapterFavorit);
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
