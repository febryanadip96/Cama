package com.example.biyan.ubama.beranda;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.biyan.ubama.produk.BarangJasaActivity;
import com.example.biyan.ubama.R;
import com.example.biyan.ubama.UrlUbama;
import com.example.biyan.ubama.models.Feed;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Biyan on 11/10/2017.
 */
public class FeedItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ImageView imageBarang;
    TextView namaBarang;
    TextView hargaBarang;
    Context context;
    List<Feed.Barang_jasa> feedBarangJasaList;

    public FeedItemAdapter(List<Feed.Barang_jasa> feedBarangJasaList) {
        this.feedBarangJasaList = feedBarangJasaList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_feed_barang, parent, false);
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

        if (!feedBarangJasaList.get(position).gambar.isEmpty()) {
            Picasso.with(context).load(UrlUbama.URL_IMAGE + feedBarangJasaList.get(position).gambar.get(0).url_gambar).error(R.drawable.ic_error_image).fit().centerInside().into(imageBarang);
        } else {
            imageBarang.setImageResource(R.drawable.ic_error_image);
        }
        namaBarang.setText(feedBarangJasaList.get(position).nama.toString());
        NumberFormat currency = NumberFormat.getInstance(Locale.GERMANY);
        hargaBarang.setText("Rp. " + currency.format(feedBarangJasaList.get(position).harga).toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toBarangJasa = new Intent(context, BarangJasaActivity.class);
                toBarangJasa.putExtra("idBarangJasa", feedBarangJasaList.get(position).id);
                toBarangJasa.putExtra("namaBarangJasa", feedBarangJasaList.get(position).nama);
                context.startActivity(toBarangJasa);
            }
        });
    }

    @Override
    public int getItemCount() {
        return feedBarangJasaList.size();
    }
}
