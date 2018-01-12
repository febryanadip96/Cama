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
import com.example.biyan.ubama.UrlUbama;
import com.example.biyan.ubama.models.TanyaJawab;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Biyan on 11/22/2017.
 */

public class TokoTanyaJawabAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<TanyaJawab> tanyaJawabList;
    ImageView imageBarang;
    CircleImageView imagePenanya;
    TextView namaPenanya;
    TextView isi;
    TextView waktu;
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        imageBarang = (ImageView) holder.itemView.findViewById(R.id.image_barang);
        imagePenanya = (CircleImageView) holder.itemView.findViewById(R.id.image_penanya);
        namaPenanya = (TextView) holder.itemView.findViewById(R.id.nama_penanya);
        isi = (TextView) holder.itemView.findViewById(R.id.isi);
        waktu = (TextView) holder.itemView.findViewById(R.id.waktu);
        final SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy HH:mm");
        Date date = null;
        if(tanyaJawabList.get(position).barang_jasa.gambar.size()>0){
            Picasso.with(context).load(UrlUbama.URL_IMAGE+tanyaJawabList.get(position).barang_jasa.gambar.get(0).url_gambar).fit().centerInside().into(imageBarang);
        }
        else{
            imageBarang.setImageResource(R.drawable.ic_error_image);
        }
        if(!tanyaJawabList.get(position).penanya.url_profile.equals("")){
            Picasso.with(context).load(UrlUbama.URL_IMAGE+tanyaJawabList.get(position).penanya.url_profile).fit().into(imagePenanya);
        }
        else{
            imagePenanya.setImageResource(R.drawable.ic_error_image);
        }
        namaPenanya.setText(tanyaJawabList.get(position).penanya.user.name);
        isi.setText(tanyaJawabList.get(position).pertanyaan);
        try {
            date = inputFormat.parse(tanyaJawabList.get(position).waktu_tanya);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        waktu.setText(outputFormat.format(date));
        if(tanyaJawabList.get(position).jawaban!=null){
            isi.setText(tanyaJawabList.get(position).jawaban);
            try {
                date = inputFormat.parse(tanyaJawabList.get(position).waktu_tanya);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            waktu.setText(outputFormat.format(date));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,TokoJawabPertanyaanActivity.class);
                intent.putExtra("idTanyaJawab", tanyaJawabList.get(position).id);
                context.startActivity(intent);
            }
        });
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
