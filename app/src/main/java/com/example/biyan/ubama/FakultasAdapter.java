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

import butterknife.BindView;
import butterknife.ButterKnife;

public class FakultasAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    @BindView(R.id.image_fakultas)
    ImageView imageFakultas;
    @BindView(R.id.nama_fakultas)
    TextView namaFakultas;
    private Context context;
    private List<Fakultas> fakultasList;

    public FakultasAdapter(List<Fakultas> fakultasList) {
        this.fakultasList = fakultasList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fakultas, parent, false);
        ButterKnife.bind(this, v);
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
        Picasso.with(context).load(UrlUbama.URL_IMAGE + fakultasList.get(position).url_gambar).into(imageFakultas);
        namaFakultas.setText(fakultasList.get(position).nama);
    }

    @Override
    public int getItemCount() {
        return fakultasList.size();
    }
}
