package com.example.biyan.ubama.beranda;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.biyan.ubama.R;
import com.example.biyan.ubama.produk.TokoActivity;
import com.example.biyan.ubama.UrlCama;
import com.example.biyan.ubama.models.Feed;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Biyan on 11/10/2017.
 */
public class FeedTokoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    CircleImageView imageToko;
    TextView namaToko;
    RecyclerView recyclerFeed;
    Context context;
    List<Feed> feedList;
    RecyclerView.Adapter adapterFeed;
    RecyclerView.LayoutManager layoutManagerFeed;

    public FeedTokoAdapter(List<Feed> feedList) {
        this.feedList = feedList;
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        imageToko = (CircleImageView) holder.itemView.findViewById(R.id.image_toko);
        namaToko = (TextView) holder.itemView.findViewById(R.id.nama_toko);
        recyclerFeed = (RecyclerView) holder.itemView.findViewById(R.id.recycler_feed);
        recyclerFeed.setLayoutManager(layoutManagerFeed);
        adapterFeed = new FeedItemAdapter(feedList.get(position).barang_jasa);
        recyclerFeed.setAdapter(adapterFeed);
        Picasso.with(context).load(UrlCama.URL_IMAGE + feedList.get(position).url_profile).fit().into(imageToko);
        namaToko.setText(feedList.get(position).nama);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toko = new Intent(context, TokoActivity.class);
                toko.putExtra("idToko", feedList.get(position).id);
                context.startActivity(toko);
            }
        });
    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }
}
