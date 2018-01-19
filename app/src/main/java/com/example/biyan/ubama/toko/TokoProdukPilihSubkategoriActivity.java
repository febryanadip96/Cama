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
import com.example.biyan.ubama.models.Subkategori;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TokoProdukPilihSubkategoriActivity extends AppCompatActivity {

    @BindView(R.id.list_view)
    ListView listView;

    int idKategori;
    List<Subkategori> subkategoriList;
    RequestQueue queue;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toko_produk_pilih_subkategori);
        ButterKnife.bind(this);
        queue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        idKategori = intent.getIntExtra("idKategori",0);
        getSubkategori();
    }

    public void getSubkategori(){
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setIndeterminate(true);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.show();
        String url = UrlCama.KATEGORI_SUBKATEGORI + idKategori;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                loading.dismiss();
                subkategoriList = new Gson().fromJson(response.toString(), new TypeToken<List<Subkategori>>() {
                }.getType());
                adapter = new SubkategoriAdapter(TokoProdukPilihSubkategoriActivity.this, subkategoriList);
                listView.setAdapter((ListAdapter) adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Log.e("Error Volley", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", UserToken.getToken(getApplication()));
                params.put("Accept", "application/json");
                return params;
            }
        };
        request.setShouldCache(false);
        queue.add(request);
    }

    public class SubkategoriAdapter extends BaseAdapter {

        List<Subkategori> subkategoriList;
        Activity parent;

        public SubkategoriAdapter(Activity activity, List<Subkategori> subkategoriList) {
            this.parent = activity;
            this.subkategoriList = subkategoriList;
        }

        @Override
        public int getCount() {
            return subkategoriList.size();
        }

        @Override
        public Subkategori getItem(int position) {
            return subkategoriList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return subkategoriList.get(position).id;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_pilih_subkategori, container, false);
            }

            TextView namaSubkategori = (TextView) convertView.findViewById(R.id.nama_subkategori);
            namaSubkategori.setText(subkategoriList.get(position).nama);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("idSubkategori", getItem(position).id);
                    intent.putExtra("namaSubkategori", getItem(position).kategori.nama+" > "+getItem(position).nama);
                    parent.setResult(RESULT_OK,intent);
                    finish();
                }
            });
            return convertView;
        }
    }
}
