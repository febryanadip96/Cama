package com.example.biyan.ubama.toko;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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
import com.example.biyan.ubama.UrlCama;
import com.example.biyan.ubama.UserToken;
import com.example.biyan.ubama.models.TanyaJawab;
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
public class TokoTanyaJawabBelumTerjawabFragment extends Fragment {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.empty)
    TextView empty;
    Unbinder unbinder;

    RequestQueue queue;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    List<TanyaJawab> tanyaJawabList;

    public static TokoTanyaJawabBelumTerjawabFragment newInstance() {
        TokoTanyaJawabBelumTerjawabFragment tokoTanyaJawabBelumTerjawabFragment = new TokoTanyaJawabBelumTerjawabFragment();
        return tokoTanyaJawabBelumTerjawabFragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        getTanyaJawabBelumTerjawab();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_toko_tanya_jawab_belum_terjawab, container, false);
        unbinder = ButterKnife.bind(this, view);
        queue = Volley.newRequestQueue(getActivity());
        layoutManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(layoutManager);
        getTanyaJawabBelumTerjawab();
        return view;
    }

    public void getTanyaJawabBelumTerjawab() {
        String url = UrlCama.USER_TOKO_TANYA_JAWAB_BELUM_TERJAWAB;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                tanyaJawabList = new Gson().fromJson(response.toString(), new TypeToken<List<TanyaJawab>>() {
                }.getType());
                adapter = new TokoTanyaJawabAdapter(tanyaJawabList);
                recycler.setAdapter(adapter);
                if(!(tanyaJawabList.size()>0)){
                    recycler.setVisibility(View.GONE);
                    empty.setVisibility(View.VISIBLE);
                }
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
