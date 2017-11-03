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
                //Toast.makeText(getActivity().getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                try {
                    barangJasaList = new ArrayList<BarangJasa>();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject barang_jasa_item = response.getJSONObject(i);
                        BarangJasa barangJasa = new BarangJasa();
                        barangJasa.setId(barang_jasa_item.getInt("id"));
                        barangJasa.setNama(barang_jasa_item.getString("nama"));
                        barangJasa.setHarga(barang_jasa_item.getInt("harga"));
                        Toko toko = new Toko();
                        toko.setNama(barang_jasa_item.getJSONObject("toko").getString("nama"));
                        barangJasa.setToko(toko);
                        JSONArray gambar = barang_jasa_item.getJSONArray("gambar");
                        List<String> url_gambar = new ArrayList<String>();
                        for(int j =0; j < gambar.length(); j++){
                            JSONObject gambar_item = gambar.getJSONObject(j);
                            url_gambar.add(gambar_item.getString("url_gambar"));
                        }
                        barangJasa.setUrl_gambar(url_gambar);
                        barangJasaList.add(barangJasa);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
