package com.example.biyan.ubama.pesanan;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biyan.ubama.R;
import com.example.biyan.ubama.UrlCama;
import com.example.biyan.ubama.UserToken;
import com.example.biyan.ubama.models.Pesanan;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Biyan on 12/13/2017.
 */

public class KomentarPesananAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    List<Pesanan.Detail_pesanan> detailPesananList;
    RequestQueue queue;
    Context context;

    public KomentarPesananAdapter(List<Pesanan.Detail_pesanan> detailPesananList) {
        this.detailPesananList = detailPesananList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_form_komentar, parent, false);
        context = parent.getContext();
        queue = Volley.newRequestQueue(context);
        RecyclerView.ViewHolder vh = new RecyclerView.ViewHolder(v) {
            @Override
            public String toString() {
                return super.toString();
            }
        };
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ImageView imageBarang = (ImageView) holder.itemView.findViewById(R.id.image_barang);
        final TextView namaBarang = (TextView) holder.itemView.findViewById(R.id.nama_barang);
        final ImageView star1 = (ImageView) holder.itemView.findViewById(R.id.star_1);
        final ImageView star2 = (ImageView) holder.itemView.findViewById(R.id.star_2);
        final ImageView star3 = (ImageView) holder.itemView.findViewById(R.id.star_3);
        final ImageView star4 = (ImageView) holder.itemView.findViewById(R.id.star_4);
        final ImageView star5 = (ImageView) holder.itemView.findViewById(R.id.star_5);
        final TextView totalRating = (TextView) holder.itemView.findViewById(R.id.total_rating);
        final EditText  komentar = (EditText) holder.itemView.findViewById(R.id.komentar);
        final TextInputLayout layoutKomentar = (TextInputLayout) holder.itemView.findViewById(R.id.layout_komentar);
        final TextView isiKomentar = (TextView) holder.itemView.findViewById(R.id.isi_komentar);
        final Button simpan = (Button) holder.itemView.findViewById(R.id.simpan);
        if(detailPesananList.get(position).barang_jasa.gambar.size()>0){
            Picasso.with(context).load(UrlCama.URL_IMAGE+detailPesananList.get(position).barang_jasa.gambar.get(0).url_gambar).error(R.drawable.ic_error_image).fit().centerInside().into(imageBarang);
        }
        else{
            imageBarang.setImageResource(R.drawable.ic_error_image);
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
                    totalRating.setText(5+"");
                }
            });
            simpan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(totalRating.getText().toString().equals("0")){
                        Toast.makeText(context, "Rating harus diisi", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(komentar.getText().toString().equals("")){
                        layoutKomentar.setError("Komentar harus diisi");
                        komentar.requestFocus();
                        return;
                    } else{
                        layoutKomentar.setError(null);
                    }
                    final ProgressDialog loading = new ProgressDialog(context);
                    loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    loading.setMessage("Mohon Menunggu");
                    loading.setIndeterminate(true);
                    loading.show();
                    queue = Volley.newRequestQueue(context);
                    String url = UrlCama.SIMPAN_KOMENTAR;
                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                loading.dismiss();
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
                            loading.dismiss();
                            Log.e("Error Volley", error.toString());
                        }
                    }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Authorization", UserToken.getToken(context));
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
    }

    @Override
    public int getItemCount() {
        return detailPesananList.size();
    }
}
