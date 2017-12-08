package com.example.biyan.ubama.beranda;

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
import com.example.biyan.ubama.models.Kategori;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Biyan on 11/10/2017.
 */

public class BerandaKategoriAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ImageView imageKategori;
    TextView namaKategori;
    Context context;
    List<Kategori> kategoriList;

    public BerandaKategoriAdapter(List<Kategori> kategoriList) {
        this.kategoriList = kategoriList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_kategori, parent, false);
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
        imageKategori = (ImageView) holder.itemView.findViewById(R.id.image_kategori);
        namaKategori = (TextView) holder.itemView.findViewById(R.id.nama_kategori);
        Picasso.with(context).load(UrlUbama.URL_IMAGE + kategoriList.get(position).url_gambar).fit().into(imageKategori);
        namaKategori.setText(kategoriList.get(position).nama);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kategori = new Intent(context, SubkategoriActivity.class);
                kategori.putExtra("idKategori", kategoriList.get(position).id);
                kategori.putExtra("namaKategori", kategoriList.get(position).nama);
                context.startActivity(kategori);
            }
        });
    }

    @Override
    public int getItemCount() {
        return kategoriList.size();
    }
}
