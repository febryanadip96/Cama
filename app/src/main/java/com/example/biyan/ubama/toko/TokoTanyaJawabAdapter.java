package com.example.biyan.ubama.toko;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.biyan.ubama.R;
import com.example.biyan.ubama.UrlUbama;
import com.example.biyan.ubama.models.TanyaJawab;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Biyan on 11/22/2017.
 */

public class TokoTanyaJawabAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<TanyaJawab> tanyaJawabList;
    ImageView imageBarang;
    TextView namaPenanya;
    TextView pertanyaan;
    TextView waktuTanya;
    ImageView imageToko;
    TextView namaToko;
    TextView jawaban;
    TextView waktuJawab;
    Context context;

    final static int BELUM_TERJAWAB = 1;
    final static int TERJAWAB = 2;

    public TokoTanyaJawabAdapter(List<TanyaJawab> tanyaJawabList){
        this.tanyaJawabList = tanyaJawabList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_toko_tanya_jawab_belum_terjawab, parent, false);
        if(viewType == TERJAWAB){
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_toko_tanya_jawab_terjawab, parent, false);
        }
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
        imageBarang = (ImageView) holder.itemView.findViewById(R.id.image_barang);
        namaPenanya = (TextView) holder.itemView.findViewById(R.id.nama_penanya);
        pertanyaan = (TextView) holder.itemView.findViewById(R.id.pertanyaan);
        waktuTanya = (TextView) holder.itemView.findViewById(R.id.waktu_tanya);
        if(tanyaJawabList.get(position).jawaban!=null){
            imageToko = (ImageView) holder.itemView.findViewById(R.id.image_toko);
            namaToko = (TextView) holder.itemView.findViewById(R.id.nama_toko);
            jawaban = (TextView) holder.itemView.findViewById(R.id.jawaban);
            waktuJawab = (TextView) holder.itemView.findViewById(R.id.waktu_jawab);
        }
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm");
        Date date = null;
        if(tanyaJawabList.get(position).barang_jasa.gambar.size()>0){
            Picasso.with(context).load(UrlUbama.URL_IMAGE+tanyaJawabList.get(position).barang_jasa.gambar.get(0)).into(imageBarang);
        }
        namaPenanya.setText(tanyaJawabList.get(position).penanya.user.name);
        pertanyaan.setText(tanyaJawabList.get(position).pertanyaan);
        try {
            date = inputFormat.parse(tanyaJawabList.get(position).waktu_tanya);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        waktuTanya.setText(outputFormat.format(date));
        if(tanyaJawabList.get(position).jawaban!=null){
            if(!tanyaJawabList.get(position).barang_jasa.toko.url_profile.equals("")){
                Picasso.with(context).load(UrlUbama.URL_IMAGE+tanyaJawabList.get(position).barang_jasa.toko.url_profile).into(imageToko);
            }
            namaToko.setText(tanyaJawabList.get(position).barang_jasa.toko.nama);
            jawaban.setText(tanyaJawabList.get(position).jawaban);
            try {
                date = inputFormat.parse(tanyaJawabList.get(position).waktu_jawab);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            waktuJawab.setText(outputFormat.format(date));
        }
    }

    @Override
    public int getItemCount() {
        return tanyaJawabList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(tanyaJawabList.get(position).jawaban!=null){
            return TERJAWAB;
        }
        else{
            return BELUM_TERJAWAB;
        }
    }
}
