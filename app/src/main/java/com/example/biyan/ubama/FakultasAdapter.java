package com.example.biyan.ubama;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Biyan on 11/2/2017.
 */

public class FakultasAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Fakultas> fakultasList;

    public FakultasAdapter(List<Fakultas> fakultasList){this.fakultasList = fakultasList;}

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fakultas, parent, false);
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
        TextView namaFakultas = (TextView) holder.itemView.findViewById(R.id.nama_fakultas);
        namaFakultas.setText(fakultasList.get(position).getNama());
    }

    @Override
    public int getItemCount() {
        return fakultasList.size();
    }
}
