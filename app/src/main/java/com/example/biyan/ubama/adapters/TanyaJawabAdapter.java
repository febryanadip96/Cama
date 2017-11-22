package com.example.biyan.ubama.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.biyan.ubama.R;
import com.example.biyan.ubama.UrlUbama;
import com.example.biyan.ubama.models.TanyaJawab;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Biyan on 11/17/2017.
 */

public class TanyaJawabAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    CircleImageView image_penanya;
    CircleImageView image_toko;
    TextView nama_penanya;
    TextView pertanyaan;
    TextView waktu_tanya;
    TextView nama_toko;
    TextView jawaban;
    TextView waktu_jawab;
    List<TanyaJawab> tanyaJawabList;
    Context context;

    public TanyaJawabAdapter(List<TanyaJawab> tanyaJawabList){
        this.tanyaJawabList = tanyaJawabList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tanya_jawab, parent, false);
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
        image_penanya = (CircleImageView) holder.itemView.findViewById(R.id.image_penanya);
        image_toko = (CircleImageView) holder.itemView.findViewById(R.id.image_toko);
        nama_penanya = (TextView) holder.itemView.findViewById(R.id.nama_penanya);
        pertanyaan = (TextView) holder.itemView.findViewById(R.id.pertanyaan);
        waktu_tanya = (TextView) holder.itemView.findViewById(R.id.waktu_tanya);
        nama_toko = (TextView) holder.itemView.findViewById(R.id.nama_toko);
        jawaban = (TextView) holder.itemView.findViewById(R.id.jawaban);
        waktu_jawab = (TextView) holder.itemView.findViewById(R.id.waktu_jawab);
        Picasso.with(context).load(UrlUbama.URL_IMAGE+tanyaJawabList.get(position).penanya.url_profile).into(image_penanya);
        nama_penanya.setText(tanyaJawabList.get(position).penanya.user.name);
        pertanyaan.setText(tanyaJawabList.get(position).pertanyaan);
        waktu_tanya.setText(tanyaJawabList.get(position).waktu_tanya);
        if(TextUtils.isEmpty(tanyaJawabList.get(position).waktu_jawab)){
            jawaban.setText("Belum Ada Jawaban");
            nama_toko.setText("");
            image_toko.setImageResource(android.R.color.transparent);
            image_toko.setVisibility(View.INVISIBLE);
            waktu_jawab.setText("");
        }
        else{
            jawaban.setText(tanyaJawabList.get(position).jawaban);
            nama_toko.setText(tanyaJawabList.get(position).barang_jasa.toko.nama);
            waktu_jawab.setText(tanyaJawabList.get(position).waktu_jawab);
            Picasso.with(context).load(UrlUbama.URL_IMAGE+tanyaJawabList.get(position).barang_jasa.toko.url_profile).into(image_toko);
        }
    }

    @Override
    public int getItemCount() {
        return tanyaJawabList.size();
    }
}
