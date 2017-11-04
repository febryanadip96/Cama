package com.example.biyan.ubama;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Biyan on 11/5/2017.
 */

public class FavoritTokoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private Context context;
    private List<Toko> tokoList;
    private RecyclerView mRecyclerViewFeed;
    private RecyclerView.Adapter mAdapterFeed;
    private RecyclerView.LayoutManager mLayoutManagerFeed;

    public  FavoritTokoAdapter(List<Toko> tokoList){
        this.tokoList = tokoList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorit_toko, parent, false);
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
        ImageView imageToko = (ImageView) holder.itemView.findViewById(R.id.image_toko);
        mRecyclerViewFeed = (RecyclerView) holder.itemView.findViewById(R.id.recycler_feed);
        mLayoutManagerFeed = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        mRecyclerViewFeed.setLayoutManager(mLayoutManagerFeed);
        mAdapterFeed = new FeedAdapter(tokoList.get(position).barang_jasa);
        mRecyclerViewFeed.setAdapter(mAdapterFeed);
        Picasso.with(context).load(UrlUbama.URL_IMAGE+tokoList.get(position).url_profile).into(imageToko);
    }

    @Override
    public int getItemCount() {
        return tokoList.size();
    }
}
