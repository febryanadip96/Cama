package com.example.biyan.ubama.keranjang;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.biyan.ubama.R;
import com.example.biyan.ubama.UrlUbama;
import com.example.biyan.ubama.models.Keranjang;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Biyan on 11/15/2017.
 */

public class KeranjangAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    List<Keranjang> keranjangList;
    CircleImageView imageToko;
    TextView namaToko;
    RecyclerView recyclerItemKeranjang;
    RecyclerView.Adapter adapterItemKeranjang;
    RecyclerView.LayoutManager layoutManagerItemKeranjang;
    Context context;

    public KeranjangAdapter(List<Keranjang> keranjangList){this.keranjangList = keranjangList;}

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_keranjang_toko, parent, false);
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        imageToko = (CircleImageView) holder.itemView.findViewById(R.id.image_toko);
        namaToko = (TextView) holder.itemView.findViewById(R.id.nama_toko);
        recyclerItemKeranjang = (RecyclerView) holder.itemView.findViewById(R.id.recycler_item_keranjang);
        Picasso.with(context).load(UrlUbama.URL_IMAGE+keranjangList.get(position).url_profile).into(imageToko);
        namaToko.setText(keranjangList.get(position).nama);
        layoutManagerItemKeranjang = new LinearLayoutManager(context);
        adapterItemKeranjang = new KeranjangItemAdapter(keranjangList.get(position).barangJasa);
        recyclerItemKeranjang.setLayoutManager(layoutManagerItemKeranjang);
        recyclerItemKeranjang.setAdapter(adapterItemKeranjang);
    }

    @Override
    public int getItemCount() {
        return keranjangList.size();
    }
}
