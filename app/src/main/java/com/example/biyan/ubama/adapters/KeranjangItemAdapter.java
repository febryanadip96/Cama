package com.example.biyan.ubama.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.biyan.ubama.KeranjangActivity;
import com.example.biyan.ubama.R;
import com.example.biyan.ubama.UrlUbama;
import com.example.biyan.ubama.UserToken;
import com.example.biyan.ubama.models.Keranjang;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Biyan on 11/15/2017.
 */

public class KeranjangItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ImageView hapus;
    private ImageView gambarBarang;
    private TextView namaBarang;
    private TextView hargaBarang;
    private TextView jumlah;
    private List<Keranjang.BarangJasa> barangJasaList;
    private Context context;
    private RequestQueue queue;
    private KeranjangItemAdapter adapter;

    public KeranjangItemAdapter (List<Keranjang.BarangJasa> barangJasaList){
        this.barangJasaList = barangJasaList;
        this.adapter = this;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_keranjang_barang, parent, false);
        context = parent.getContext();
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
        hapus = (ImageView) holder.itemView.findViewById(R.id.hapus);
        gambarBarang = (ImageView) holder.itemView.findViewById(R.id.gambar_barang);
        namaBarang = (TextView) holder.itemView.findViewById(R.id.nama_barang);
        hargaBarang = (TextView) holder.itemView.findViewById(R.id.harga_barang);
        jumlah = (TextView) holder.itemView.findViewById(R.id.jumlah);
        if(barangJasaList.get(position).gambar.size()>0){
            Picasso.with(context).load(UrlUbama.URL_IMAGE+barangJasaList.get(position).gambar.get(0).url_gambar).into(gambarBarang);
        }
        namaBarang.setText(barangJasaList.get(position).nama);
        NumberFormat currency = NumberFormat.getInstance(Locale.GERMANY);
        hargaBarang.setText("Rp. " + currency.format(barangJasaList.get(position).harga).toString());
        jumlah.setText(barangJasaList.get(position).data_keranjang.jumlah+"");
        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Anda yakin ingin menghapus barang/jasa ini dari keranjang?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                hapusBarangJasaKeranjang(barangJasaList.get(position).id, position);
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return barangJasaList.get(position).id;
    }

    @Override
    public int getItemCount() {
        return barangJasaList.size();
    }


    private void hapusBarangJasaKeranjang(final int idBarangJasa, final int position) {
        queue = Volley.newRequestQueue(context);
        String url = UrlUbama.USER_HAPUS_KERANJANG + idBarangJasa;
        JsonObjectRequest hapusBarangJasaDiKeranjangRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("hapus")) {
                        ((KeranjangActivity)context).getKeranjang();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Volley", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", UserToken.getToken(context));
                params.put("Accept", "application/json");
                return params;
            }
        };
        queue.add(hapusBarangJasaDiKeranjangRequest);
    }
}
