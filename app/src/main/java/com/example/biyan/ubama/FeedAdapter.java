package com.example.biyan.ubama;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ImageView imageBarang = (ImageView) holder.itemView.findViewById(R.id.image_barang);
        TextView namaBarang = (TextView) holder.itemView.findViewById(R.id.nama_barang);
        TextView hargaBarang = (TextView) holder.itemView.findViewById(R.id.harga_barang);
        TextView namaToko = (TextView) holder.itemView.findViewById(R.id.nama_toko);
        if(barangJasaList.get(position).gambar != null){
            if(barangJasaList.get(position).gambar.size() > 0){
                Picasso.with(context).load(UrlUbama.URL_IMAGE+barangJasaList.get(position).gambar.get(0).url_gambar).into(imageBarang);
            }
        }
        namaBarang.setText(barangJasaList.get(position).nama.toString());

        Locale localeID = new Locale("in", "ID");
        NumberFormat currency  = NumberFormat.getCurrencyInstance(localeID);
        hargaBarang.setText(currency.format(barangJasaList.get(position).harga).toString());

        namaToko.setText(barangJasaList.get(position).toko.nama.toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toBarangJasa = new Intent(context, BarangJasaActivity.class);
                toBarangJasa.putExtra("idBarangJasa", barangJasaList.get(position).id);
                toBarangJasa.putExtra("namaBarangJasa", barangJasaList.get(position).nama);
                context.startActivity(toBarangJasa);
            }
        });
    }

    @Override
    public int getItemCount() {
        return barangJasaList.size();
    }


}
