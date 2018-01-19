package com.example.biyan.ubama.toko;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
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
import com.example.biyan.ubama.models.Fakultas;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TokoProdukPilihFakultasActivity extends AppCompatActivity {

    @BindView(R.id.list_view)
    ListView listView;

    List<Fakultas> fakultasList;
    RequestQueue queue;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toko_produk_pilih_fakultas);
        ButterKnife.bind(this);
        queue = Volley.newRequestQueue(this);
        getFakultas();
    }

    public void getFakultas(){
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setIndeterminate(true);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.show();
        String url = UrlCama.FAKULTAS;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                loading.dismiss();
                fakultasList = new Gson().fromJson(response.toString(), new TypeToken<List<Fakultas>>() {
                }.getType());
                adapter = new FakultasAdapter(TokoProdukPilihFakultasActivity.this, fakultasList);
                listView.setAdapter((ListAdapter) adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Log.e("Error Volley ", error.toString());
                return;
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", UserToken.getToken(getApplicationContext()));
                params.put("Accept", "application/json");
                return params;
            }
        };
        request.setShouldCache(false);
        queue.add(request);
    }

    public class FakultasAdapter extends BaseAdapter {

        List<Fakultas> fakultasList;
        Activity parent;

        public FakultasAdapter(Activity activity, List<Fakultas> fakultasList) {
            this.parent = activity;
            this.fakultasList = fakultasList;
        }

        @Override
        public int getCount() {
            return fakultasList.size();
        }

        @Override
        public Fakultas getItem(int position) {
            return fakultasList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return fakultasList.get(position).id;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_pilih_fakultas, container, false);
            }

            TextView namaFakultas = (TextView) convertView.findViewById(R.id.nama_fakultas);
            namaFakultas.setText(fakultasList.get(position).nama);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("idFakultas", getItem(position).id);
                    intent.putExtra("namaFakultas", getItem(position).nama);
                    parent.setResult(RESULT_OK, intent);
                    finish();
                }
            });
            return convertView;
        }
    }
}
