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
import com.example.biyan.ubama.models.Subkategori;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Biyan on 11/11/2017.
 */
public class SubkategoriAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Subkategori> subkategoriList;
    ImageView imageSubkategori;
    TextView namaSubkategori;
    Context context;

    public SubkategoriAdapter(List<Subkategori> subkategoriList) {
        this.subkategoriList = subkategoriList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_subkategori, parent, false);
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
        imageSubkategori = (ImageView) holder.itemView.findViewById(R.id.image_subkategori);
        namaSubkategori = (TextView) holder.itemView.findViewById(R.id.nama_subkategori);
        if(!subkategoriList.get(position).url_gambar.equals("")){
            Picasso.with(context).load(UrlUbama.URL_IMAGE+subkategoriList.get(position).url_gambar).into(imageSubkategori);
        } else {
            imageSubkategori.setImageResource(R.drawable.ic_error_image);
        }
        namaSubkategori.setText(subkategoriList.get(position).nama);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent subkategori = new Intent(context, HasilSubkategoriActivity.class);
                subkategori.putExtra("idSubkategori", subkategoriList.get(position).id);
                subkategori.putExtra("namaSubkategori", subkategoriList.get(position).nama);
                context.startActivity(subkategori);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subkategoriList.size();
    }
}
