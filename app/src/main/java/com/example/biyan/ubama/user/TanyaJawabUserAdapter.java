package com.example.biyan.ubama.user;

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
import com.example.biyan.ubama.models.TanyaJawab;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Biyan on 12/2/2017.
 */

public class TanyaJawabUserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ImageView imageBarang;
    TextView namaToko;
    TextView isi;
    ImageView cek;
    TextView waktu;
    List<TanyaJawab> tanyaJawabList;
    Context context;

    public TanyaJawabUserAdapter(List<TanyaJawab> tanyaJawabList){
        this.tanyaJawabList = tanyaJawabList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tanya_jawab_user, parent, false);
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
        namaToko = (TextView) holder.itemView.findViewById(R.id.nama_toko);
        isi = (TextView) holder.itemView.findViewById(R.id.isi);
        waktu = (TextView) holder.itemView.findViewById(R.id.waktu);
        cek = (ImageView) holder.itemView.findViewById(R.id.cek);
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm");
        Date date = null;
        if(tanyaJawabList.get(position).barang_jasa.gambar.size()>0){
            Picasso.with(context).load(UrlCama.URL_IMAGE+tanyaJawabList.get(position).barang_jasa.gambar.get(0).url_gambar).fit().centerInside().into(imageBarang);
        }
        namaToko.setText(tanyaJawabList.get(position).barang_jasa.toko.nama);
        isi.setText(tanyaJawabList.get(position).pertanyaan);
        try {
            date = inputFormat.parse(tanyaJawabList.get(position).waktu_tanya);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        waktu.setText(outputFormat.format(date));
        cek.setVisibility(View.GONE);
        if(tanyaJawabList.get(position).jawaban !=null){
            isi.setText(tanyaJawabList.get(position).jawaban);
            try {
                date = inputFormat.parse(tanyaJawabList.get(position).waktu_jawab);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            waktu.setText(outputFormat.format(date));
            cek.setVisibility(View.VISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LihatTanyaJawabUserActivity.class);
                intent.putExtra("idTanyaJawab", tanyaJawabList.get(position).id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tanyaJawabList.size();
    }
}
