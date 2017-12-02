package com.example.biyan.ubama.produk;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.biyan.ubama.R;
import com.example.biyan.ubama.UrlUbama;
import com.example.biyan.ubama.models.Toko;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Biyan on 11/16/2017.
 */

public class TokoBarangJasaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ImageView imageBarang;
    TextView namaBarang;
    TextView hargaBarang;
    Context context;
    List<Toko.Barang_jasa> tokoBarangJasaList;

    public TokoBarangJasaAdapter(List<Toko.Barang_jasa> tokoBarangJasaList) {
        this.tokoBarangJasaList = tokoBarangJasaList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_toko_barang_jasa, parent, false);
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
        imageBarang = (ImageView) holder.itemView.findViewById(R.id.image_barang);
        namaBarang = (TextView) holder.itemView.findViewById(R.id.nama_barang);
        hargaBarang = (TextView) holder.itemView.findViewById(R.id.harga_barang);

        if (!tokoBarangJasaList.get(position).gambar.isEmpty()) {
            Picasso.with(context).load(UrlUbama.URL_IMAGE + tokoBarangJasaList.get(position).gambar.get(0).url_gambar).fit().error(R.drawable.ic_error_image).into(imageBarang);
        } else {
            imageBarang.setImageResource(R.drawable.ic_error_image);
        }
        namaBarang.setText(tokoBarangJasaList.get(position).nama.toString());
        NumberFormat currency = NumberFormat.getInstance(Locale.GERMANY);
        hargaBarang.setText("Rp. " + currency.format(tokoBarangJasaList.get(position).harga).toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toBarangJasa = new Intent(context, BarangJasaActivity.class);
                toBarangJasa.putExtra("idBarangJasa", tokoBarangJasaList.get(position).id);
                toBarangJasa.putExtra("namaBarangJasa", tokoBarangJasaList.get(position).nama);
                context.startActivity(toBarangJasa);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tokoBarangJasaList.size();
    }
}
