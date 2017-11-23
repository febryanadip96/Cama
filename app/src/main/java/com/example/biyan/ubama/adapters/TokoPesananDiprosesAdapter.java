package com.example.biyan.ubama.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.biyan.ubama.R;
import com.example.biyan.ubama.UrlUbama;
import com.example.biyan.ubama.models.Pesanan;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Biyan on 11/23/2017.
 */

public class TokoPesananDiprosesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Pesanan> pesananList;
    Context context;
    ImageView imagePemesan;
    TextView namaPemesan;
    TextView statusPesanan;
    TextView idPesanan;
    TextView tanggalPesan;

    public TokoPesananDiprosesAdapter(List<Pesanan> pesananList){
        this.pesananList = pesananList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_toko_pesanan, parent, false);
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
        imagePemesan = (ImageView) holder.itemView.findViewById(R.id.image_pemesan);
        namaPemesan = (TextView) holder.itemView.findViewById(R.id.nama_pemesan);
        statusPesanan = (TextView) holder.itemView.findViewById(R.id.status_pesanan);
        idPesanan = (TextView) holder.itemView.findViewById(R.id.id_pesanan);
        tanggalPesan = (TextView) holder.itemView.findViewById(R.id.tanggal_pesan);
        Picasso.with(context).load(UrlUbama.URL_IMAGE+pesananList.get(position).pemesan.url_profile).into(imagePemesan);
        namaPemesan.setText(pesananList.get(position).pemesan.user.name);
        statusPesanan.setText(pesananList.get(position).status);
        idPesanan.setText(pesananList.get(position).id+"");
        tanggalPesan.setText(pesananList.get(position).created_at);
    }

    @Override
    public int getItemCount() {
        return pesananList.size();
    }
}
