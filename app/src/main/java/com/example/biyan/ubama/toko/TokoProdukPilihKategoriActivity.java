package com.example.biyan.ubama.toko;

import android.app.Activity;
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
import com.example.biyan.ubama.UrlUbama;
import com.example.biyan.ubama.UserToken;
import com.example.biyan.ubama.models.Kategori;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TokoProdukPilihKategoriActivity extends AppCompatActivity {

    @BindView(R.id.list_view)
    ListView listView;

    final static int PILIH_SUBKATEGORI = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case PILIH_SUBKATEGORI:
                    Bundle res = data.getExtras();
                    Intent intent = new Intent();
                    intent.putExtra("idSubkategori", res.getInt("idSubkategori"));
                    intent.putExtra("namaSubkategori", res.getString("namaSubkategori"));
                    setResult(RESULT_OK,intent);
                    finish();
            }
    }

    List<Kategori> kategoriList;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toko_produk_pilih_kategori);
        ButterKnife.bind(this);
        queue = Volley.newRequestQueue(this);
        getKategori();
    }

    public void getKategori() {
        String url = UrlUbama.KATEGORI;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                kategoriList = new Gson().fromJson(response.toString(), new TypeToken<List<Kategori>>() {
                }.getType());
                Adapter adapter = new KategoriAdapter(kategoriList);
                listView.setAdapter((ListAdapter) adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Volley BerandaFragment", error.toString());
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

    public class KategoriAdapter extends BaseAdapter {

        List<Kategori> kategoriList;

        public KategoriAdapter(List<Kategori> kategoriList) {
            this.kategoriList = kategoriList;
        }

        @Override
        public int getCount() {
            return kategoriList.size();
        }

        @Override
        public Kategori getItem(int position) {
            return kategoriList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return kategoriList.get(position).id;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_pilih_kategori, container, false);
            }

            TextView namaKategori = (TextView) convertView.findViewById(R.id.nama_kategori);
            namaKategori.setText(kategoriList.get(position).nama);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent subkategori = new Intent(TokoProdukPilihKategoriActivity.this, TokoProdukPilihSubkategoriActivity.class);
                    subkategori.putExtra("idKategori", getItem(position).id);
                    startActivityForResult(subkategori, PILIH_SUBKATEGORI);
                }
            });
            return convertView;
        }
    }
}