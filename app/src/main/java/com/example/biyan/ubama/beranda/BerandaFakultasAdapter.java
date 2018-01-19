package com.example.biyan.ubama.beranda;

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
import com.example.biyan.ubama.models.Fakultas;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Biyan on 11/10/2017.
 */
public class BerandaFakultasAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ImageView imageFakultas;
    TextView namaFakultas;
    Context context;
    List<Fakultas> fakultasList;

    public BerandaFakultasAdapter(List<Fakultas> fakultasList) {
        this.fakultasList = fakultasList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fakultas, parent, false);
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
        imageFakultas = (ImageView) holder.itemView.findViewById(R.id.image_fakultas);
        namaFakultas = (TextView) holder.itemView.findViewById(R.id.nama_fakultas);
        Picasso.with(context).load(UrlCama.URL_IMAGE + fakultasList.get(position).url_gambar).error(R.drawable.ic_error_image).fit().centerInside().into(imageFakultas);
        namaFakultas.setText(fakultasList.get(position).nama);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fakultas = new Intent(context, HasilFakultasActivity.class);
                fakultas.putExtra("idFakultas", fakultasList.get(position).id);
                fakultas.putExtra("namaFakultas", fakultasList.get(position).nama);
                context.startActivity(fakultas);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fakultasList.size();
    }
}
