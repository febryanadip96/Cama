package com.example.biyan.ubama;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Biyan on 11/10/2017.
 */
public class FeedTokoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private CircleImageView imageToko;
    private TextView namaToko;
    private RecyclerView recyclerFeed;
    private Context context;
    private List<Toko> tokoList;
    private RecyclerView.Adapter adapterFeed;
    private RecyclerView.LayoutManager layoutManagerFeed;

    public FeedTokoAdapter(List<Toko> tokoList) {
        this.tokoList = tokoList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_feed_toko, parent, false);
        RecyclerView.ViewHolder vh = new RecyclerView.ViewHolder(v) {
            @Override
            public String toString() {
                return super.toString();
            }
        };

        context = parent.getContext();
        layoutManagerFeed = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        imageToko = (CircleImageView) holder.itemView.findViewById(R.id.image_toko);
        namaToko = (TextView) holder.itemView.findViewById(R.id.nama_toko);
        recyclerFeed = (RecyclerView) holder.itemView.findViewById(R.id.recycler_feed);
        recyclerFeed.setLayoutManager(layoutManagerFeed);
        adapterFeed = new FeedItemAdapter(tokoList.get(position).barang_jasa);
        recyclerFeed.setAdapter(adapterFeed);
        Picasso.with(context).load(UrlUbama.URL_IMAGE + tokoList.get(position).url_profile).into(imageToko);
        namaToko.setText(tokoList.get(position).nama);
    }

    @Override
    public int getItemCount() {
        return tokoList.size();
    }
}
