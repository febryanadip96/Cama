package com.example.biyan.ubama.beranda;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.biyan.ubama.BarangJasaActivity;
import com.example.biyan.ubama.R;
import com.example.biyan.ubama.UrlUbama;
import com.example.biyan.ubama.models.Favorit;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Biyan on 11/15/2017.
 */

public class FavoritAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ImageView imageBarang;
    private TextView namaBarang;
    private CircleImageView imageToko;
    private TextView namaToko;
    private TextView hargaBarang;
    private Context context;
    private List<Favorit> favoritList;

    public FavoritAdapter(List<Favorit> favoritList) {
        this.favoritList = favoritList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorit, parent, false);
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
        imageToko = (CircleImageView) holder.itemView.findViewById(R.id.image_toko);
        namaToko = (TextView) holder.itemView.findViewById(R.id.nama_toko);

        if (favoritList.get(position).gambar.size() > 0) {
            Picasso.with(context).load(UrlUbama.URL_IMAGE + favoritList.get(position).gambar.get(0).url_gambar).fit().error(R.drawable.ic_error_image).into(imageBarang);
        } else {
            imageBarang.setImageResource(R.drawable.ic_error_image);
        }
        namaBarang.setText(favoritList.get(position).nama.toString());
        NumberFormat currency = NumberFormat.getInstance(Locale.GERMANY);
        hargaBarang.setText("Rp. " + currency.format(favoritList.get(position).harga).toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toBarangJasa = new Intent(context, BarangJasaActivity.class);
                toBarangJasa.putExtra("idBarangJasa", favoritList.get(position).id);
                toBarangJasa.putExtra("namaBarangJasa", favoritList.get(position).nama);
                context.startActivity(toBarangJasa);
            }
        });
        namaToko.setText(favoritList.get(position).toko.nama.toString());
    }

    @Override
    public int getItemCount() {
        return favoritList.size();
    }
}
