package com.example.biyan.ubama;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Biyan on 11/2/2017.
 */

public class KategoriAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Kategori> kategoriList;

    public KategoriAdapter(List<Kategori> kategoriList){this.kategoriList = kategoriList;}


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_kategori, parent, false);
        context = parent.getContext();
        RecyclerView.ViewHolder vh = new RecyclerView.ViewHolder(v){
            @Override
            public String toString() {
                return super.toString();
            }
        };
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ImageView imageKategori = (ImageView) holder.itemView.findViewById(R.id.image_kategori);
        TextView namaKategori = (TextView) holder.itemView.findViewById(R.id.nama_kategori);
        Picasso.with(context).load(UrlUbama.URL_IMAGE+kategoriList.get(position).getUrl_gambar()).into(imageKategori);
        namaKategori.setText(kategoriList.get(position).getNama());
    }

    @Override
    public int getItemCount() {
        return kategoriList.size();
    }
}
