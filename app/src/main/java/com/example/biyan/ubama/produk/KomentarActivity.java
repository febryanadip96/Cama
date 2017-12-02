package com.example.biyan.ubama.produk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.biyan.ubama.R;
import com.example.biyan.ubama.UrlUbama;
import com.example.biyan.ubama.UserToken;
import com.example.biyan.ubama.models.BarangJasa;
import com.example.biyan.ubama.models.Komentar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KomentarActivity extends AppCompatActivity {

    @BindView(R.id.image_barang)
    ImageView imageBarang;
    @BindView(R.id.nama_barang)
    TextView namaBarang;
    @BindView(R.id.total_rating)
    TextView totalRating;
    @BindView(R.id.star_1)
    ImageView star1;
    @BindView(R.id.star_2)
    ImageView star2;
    @BindView(R.id.star_3)
    ImageView star3;
    @BindView(R.id.star_4)
    ImageView star4;
    @BindView(R.id.star_5)
    ImageView star5;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    RequestQueue queue;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    List<Komentar> komentarList;
    BarangJasa barangJasa;
    int idBarangJasa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_komentar);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        queue = Volley.newRequestQueue(this);
        idBarangJasa = intent.getIntExtra("idBarangJasa",0);
        layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);
        getDataKomentar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void getDataKomentar(){
        String url = UrlUbama.KOMENTAR_BARANG_JASA+idBarangJasa;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("hasil", response.toString());
                try {
                    barangJasa = new Gson().fromJson(response.getJSONObject("barangJasa").toString(), BarangJasa.class);
                    komentarList = new Gson().fromJson(response.getJSONArray("komentar").toString(), new TypeToken<List<Komentar>>() {
                    }.getType());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(barangJasa.gambar.size()>0){
                    Picasso.with(getApplicationContext()).load(UrlUbama.URL_IMAGE+barangJasa.gambar.get(0).url_gambar).memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).into(imageBarang);
                }
                else{
                    imageBarang.setImageResource(R.drawable.ic_error_image);
                }
                namaBarang.setText(barangJasa.nama);
                totalRating.setText(new DecimalFormat("#.#").format(barangJasa.total_rating)+"");
                int rating = (int) Math.floor(barangJasa.total_rating);
                switch (rating){
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
                adapter = new KomentarAdapter(komentarList);
                recycler.setAdapter(adapter);
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
                params.put("Authorization", UserToken.getToken(getApplicationContext()));
                params.put("Accept", "application/json");
                return params;
            }
        };
        request.setShouldCache(false);
        queue.add(request);
    }
}
