package com.example.biyan.ubama.pesanan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biyan.ubama.R;
import com.example.biyan.ubama.UrlUbama;
import com.example.biyan.ubama.UserToken;
import com.example.biyan.ubama.models.Pesanan;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KomentarPesananActivity extends AppCompatActivity {

    @BindView(R.id.grid_view)
    GridView gridView;

    int idPesanan;
    Pesanan pesanan;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_komentar_pesanan);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        queue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        idPesanan = intent.getIntExtra("idPesanan", 0);
        getDetailPesanan();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void getDetailPesanan(){
        String url = UrlUbama.PESANAN_FORM_KOMENTAR+idPesanan;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pesanan = new Gson().fromJson(response.toString(), Pesanan.class);
                Adapter adapter = new FormKomentarAdapter(KomentarPesananActivity.this, pesanan.detail_pesanan);
                gridView.setAdapter((ListAdapter) adapter);
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

    public class FormKomentarAdapter extends BaseAdapter{

        ImageView imageBarang;
        TextView namaBarang;
        ImageView star1;
        ImageView star2;
        ImageView star3;
        ImageView star4;
        ImageView star5 ;
        TextView totalRating;
        TextInputLayout layoutKomentar;
        EditText komentar;
        TextView isiKomentar;
        Button simpan;

        List<Pesanan.Detail_pesanan> detailPesananList;
        Context context;

        public FormKomentarAdapter(Context context, List<Pesanan.Detail_pesanan> detailPesananList){
            this.context = context;
            this.detailPesananList = detailPesananList;
        }

        @Override
        public int getCount() {
            return detailPesananList.size();
        }

        @Override
        public Pesanan.Detail_pesanan getItem(int position) {
            return detailPesananList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_form_komentar, parent, false);
            }
            imageBarang = (ImageView) convertView.findViewById(R.id.image_barang);
            namaBarang = (TextView) convertView.findViewById(R.id.nama_barang);
            star1 = (ImageView) convertView.findViewById(R.id.star_1);
            star2 = (ImageView) convertView.findViewById(R.id.star_2);
            star3 = (ImageView) convertView.findViewById(R.id.star_3);
            star4 = (ImageView) convertView.findViewById(R.id.star_4);
            star5 = (ImageView) convertView.findViewById(R.id.star_5);
            totalRating = (TextView) convertView.findViewById(R.id.total_rating);
            komentar = (EditText) convertView.findViewById(R.id.komentar);
            layoutKomentar = (TextInputLayout) convertView.findViewById(R.id.layout_komentar);
            isiKomentar = (TextView) convertView.findViewById(R.id.isi_komentar);
            simpan = (Button) convertView.findViewById(R.id.simpan);
            if(detailPesananList.get(position).barang_jasa.gambar.size()>0){
                Picasso.with(context).load(UrlUbama.URL_IMAGE+detailPesananList.get(position).barang_jasa.gambar.get(0).url_gambar).error(R.drawable.ic_error_image).into(imageBarang);
            }
            namaBarang.setText(detailPesananList.get(position).barang_jasa.nama);
            if(detailPesananList.get(position).komentar==null){
                isiKomentar.setVisibility(View.GONE);
                star1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        star1.setImageResource(R.drawable.ic_star_yellow);
                        star2.setImageResource(R.drawable.ic_star_grey);
                        star3.setImageResource(R.drawable.ic_star_grey);
                        star4.setImageResource(R.drawable.ic_star_grey);
                        star5.setImageResource(R.drawable.ic_star_grey);
                        totalRating.setText(1+"");
                    }
                });
                star2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        star1.setImageResource(R.drawable.ic_star_yellow);
                        star2.setImageResource(R.drawable.ic_star_yellow);
                        star3.setImageResource(R.drawable.ic_star_grey);
                        star4.setImageResource(R.drawable.ic_star_grey);
                        star5.setImageResource(R.drawable.ic_star_grey);
                        totalRating.setText(2+"");
                    }
                });
                star3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        star1.setImageResource(R.drawable.ic_star_yellow);
                        star2.setImageResource(R.drawable.ic_star_yellow);
                        star3.setImageResource(R.drawable.ic_star_yellow);
                        star4.setImageResource(R.drawable.ic_star_grey);
                        star5.setImageResource(R.drawable.ic_star_grey);
                        totalRating.setText(3+"");
                    }
                });
                star4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        star1.setImageResource(R.drawable.ic_star_yellow);
                        star2.setImageResource(R.drawable.ic_star_yellow);
                        star3.setImageResource(R.drawable.ic_star_yellow);
                        star4.setImageResource(R.drawable.ic_star_yellow);
                        star5.setImageResource(R.drawable.ic_star_grey);
                        totalRating.setText(4+"");
                    }
                });
                star5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        star1.setImageResource(R.drawable.ic_star_yellow);
                        star2.setImageResource(R.drawable.ic_star_yellow);
                        star3.setImageResource(R.drawable.ic_star_yellow);
                        star4.setImageResource(R.drawable.ic_star_yellow);
                        star5.setImageResource(R.drawable.ic_star_yellow);
                        totalRating.setText(2+"");
                    }
                });
                simpan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(totalRating.getText().toString().equals("")){
                            layoutKomentar.setError("Rating harus diisi");
                            return;
                        }
                        if(komentar.getText().toString().equals("")){
                            layoutKomentar.setError("Komentar harus diisi");
                            komentar.requestFocus();
                            return;
                        }

                        queue = Volley.newRequestQueue(context);
                        String url = UrlUbama.SIMPAN_KOMENTAR;
                        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject hasil = new JSONObject(response);
                                    if(hasil.getBoolean("tersimpan")) {
                                        ((KomentarPesananActivity) context).getDetailPesanan();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

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

                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("nilai", totalRating.getText()+"");
                                params.put("komentar", komentar.getText()+"");
                                params.put("detail_pesanan_id", detailPesananList.get(position).id+"");
                                return params;
                            }
                        };
                        request.setShouldCache(false);
                        queue.add(request);
                    }
                });
            }
            else{
                layoutKomentar.setVisibility(View.GONE);
                komentar.setVisibility(View.GONE);
                simpan.setVisibility(View.GONE);
                isiKomentar.setVisibility(View.VISIBLE);
                isiKomentar.setText(detailPesananList.get(position).komentar.komentar);
                switch ((int) Math.floor(detailPesananList.get(position).komentar.nilai)) {
                    case 1:
                        star1.setImageResource(R.drawable.ic_star_yellow);
                        break;
                    case 2:
                        star1.setImageResource(R.drawable.ic_star_yellow);
                        star2.setImageResource(R.drawable.ic_star_yellow);
                        break;
                    case 3:
                        star1.setImageResource(R.drawable.ic_star_yellow);
                        star2.setImageResource(R.drawable.ic_star_yellow);
                        star3.setImageResource(R.drawable.ic_star_yellow);
                        break;
                    case 4:
                        star1.setImageResource(R.drawable.ic_star_yellow);
                        star2.setImageResource(R.drawable.ic_star_yellow);
                        star3.setImageResource(R.drawable.ic_star_yellow);
                        star4.setImageResource(R.drawable.ic_star_yellow);
                        break;
                    case 5:
                        star1.setImageResource(R.drawable.ic_star_yellow);
                        star2.setImageResource(R.drawable.ic_star_yellow);
                        star3.setImageResource(R.drawable.ic_star_yellow);
                        star4.setImageResource(R.drawable.ic_star_yellow);
                        star5.setImageResource(R.drawable.ic_star_yellow);
                        break;
                    default:
                        break;
                }
            }

            return convertView;
        }
    }
}
