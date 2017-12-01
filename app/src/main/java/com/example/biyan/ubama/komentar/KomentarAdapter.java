package com.example.biyan.ubama.komentar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.biyan.ubama.R;
import com.example.biyan.ubama.UrlUbama;
import com.example.biyan.ubama.models.Komentar;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Biyan on 11/16/2017.
 */

public class KomentarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    CircleImageView imagePengguna;
    TextView namaPengguna;
    ImageView star1;
    ImageView star2;
    ImageView star3;
    ImageView star4;
    ImageView star5;
    TextView isiKomentar;
    List<Komentar> komentarList;
    Context context;

    public KomentarAdapter (List<Komentar> komentarList){
        this.komentarList = komentarList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_komentar, parent, false);
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
        imagePengguna = (CircleImageView) holder.itemView.findViewById(R.id.image_pengguna);
        namaPengguna = (TextView) holder.itemView.findViewById(R.id.nama_pengguna);
        isiKomentar = (TextView) holder.itemView.findViewById(R.id.isi_komentar);
        star1 = (ImageView) holder.itemView.findViewById(R.id.star_1);
        star2 = (ImageView) holder.itemView.findViewById(R.id.star_2);
        star3 = (ImageView) holder.itemView.findViewById(R.id.star_3);
        star4 = (ImageView) holder.itemView.findViewById(R.id.star_4);
        star5 = (ImageView) holder.itemView.findViewById(R.id.star_5);
        Picasso.with(context).load(UrlUbama.URL_IMAGE+komentarList.get(position).detail_pesanan.pesanan.pemesan.url_profile).into(imagePengguna);
        namaPengguna.setText(komentarList.get(position).detail_pesanan.pesanan.pemesan.user.name);
        isiKomentar.setText(komentarList.get(position).komentar);
        int rating = (int) Math.floor(komentarList.get(position).nilai);
        switch (rating){
            case 1:
                star1.setImageResource(R.drawable.ic_star_yellow);
                break;
            case 2:
                star1.setImageResource(R.drawable.ic_star_yellow);
                star2.setImageResource(R.drawable.ic_star_yellow);
                break;
            case 3:
                star1.setImageResource(R.drawable.ic_star_yellow);
                star2.setImageResource(R.drawable.ic_star_yellow);
                star3.setImageResource(R.drawable.ic_star_yellow);
                break;
            case 4:
                star1.setImageResource(R.drawable.ic_star_yellow);
                star2.setImageResource(R.drawable.ic_star_yellow);
                star3.setImageResource(R.drawable.ic_star_yellow);
                star4.setImageResource(R.drawable.ic_star_yellow);
                break;
            case 5:
                star1.setImageResource(R.drawable.ic_star_yellow);
                star2.setImageResource(R.drawable.ic_star_yellow);
                star3.setImageResource(R.drawable.ic_star_yellow);
                star4.setImageResource(R.drawable.ic_star_yellow);
                star5.setImageResource(R.drawable.ic_star_yellow);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return komentarList.size();
    }
}
