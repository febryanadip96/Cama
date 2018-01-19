package com.example.biyan.ubama.toko;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.biyan.ubama.R;
import com.example.biyan.ubama.UrlCama;
import com.example.biyan.ubama.models.BarangJasa;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Biyan on 11/22/2017.
 */

public class TokoProdukAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    List<BarangJasa> barangJasaList;
    Context context;
    ImageView imageBarang;
    TextView namaBarang;
    TextView hargaBarang;


    public TokoProdukAdapter(List<BarangJasa> barangJasaList){
        this.barangJasaList = barangJasaList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_toko_produk, parent, false);
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
        if(barangJasaList.get(position).gambar.size()>0){
            Picasso.with(context).load(UrlCama.URL_IMAGE+barangJasaList.get(position).gambar.get(0).url_gambar).fit().centerInside().into(imageBarang);
        } else {
            imageBarang.setImageResource(R.drawable.ic_error_image);
        }
        namaBarang.setText(barangJasaList.get(position).nama);
        NumberFormat currency = NumberFormat.getInstance(Locale.GERMANY);
        hargaBarang.setText("Rp. " + currency.format(barangJasaList.get(position).harga).toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,TokoLihatProdukActivity.class);
                intent.putExtra("idBarangJasa", barangJasaList.get(position).id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return barangJasaList.size();
    }
}
