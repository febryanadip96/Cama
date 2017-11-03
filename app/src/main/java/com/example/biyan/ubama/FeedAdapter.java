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
 * Created by Biyan on 11/3/2017.
 */

public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<BarangJasa> barangJasaList;

    public FeedAdapter(List<BarangJasa> barangJasaList){this.barangJasaList = barangJasaList;}

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_feed, parent, false);
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
        ImageView imageBarang = (ImageView) holder.itemView.findViewById(R.id.image_barang);
        TextView namaBarang = (TextView) holder.itemView.findViewById(R.id.nama_barang);
        TextView hargaBarang = (TextView) holder.itemView.findViewById(R.id.harga_barang);
        TextView namaToko = (TextView) holder.itemView.findViewById(R.id.nama_toko);
        if(barangJasaList.get(position).getUrl_gambar() != null){
            if(barangJasaList.get(position).getUrl_gambar().size() > 0){
                Picasso.with(context).load(UrlUbama.URL_IMAGE+barangJasaList.get(position).getUrl_gambar().get(0)).into(imageBarang);
            }
        }
        namaBarang.setText(barangJasaList.get(position).getNama().toString());
        hargaBarang.setText(barangJasaList.get(position).getHarga()+"");
        namaToko.setText(barangJasaList.get(position).getToko().getNama().toString());
    }

    @Override
    public int getItemCount() {
        return barangJasaList.size();
    }
}
