package com.example.biyan.ubama.pesanan;

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
import com.example.biyan.ubama.models.Pesanan;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Biyan on 11/10/2017.
 */
public class PesananAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    TextView tanggalPesan;
    ImageView imageBarang;
    TextView idPesanan;
    TextView namaBarang;
    TextView statusPesanan;
    Context context;
    List<Pesanan> pesananList;

    public PesananAdapter(List<Pesanan> pesananList) {
        this.pesananList = pesananList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pesanan, parent, false);
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
        tanggalPesan = (TextView) holder.itemView.findViewById(R.id.tanggal_pesan);
        imageBarang = (ImageView) holder.itemView.findViewById(R.id.image_barang);
        idPesanan = (TextView) holder.itemView.findViewById(R.id.id_pesanan);
        namaBarang = (TextView) holder.itemView.findViewById(R.id.nama_barang);
        statusPesanan = (TextView) holder.itemView.findViewById(R.id.status_pesanan);
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date =null;
        try {
            date = inputFormat.parse(pesananList.get(position).created_at.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outputFormat= new SimpleDateFormat("dd MMMM yyyy");
        tanggalPesan.setText(outputFormat.format(date));
        if (pesananList.get(position).detail_pesanan.get(0).barang_jasa.gambar.size() > 0) {
            Picasso.with(context).load(UrlUbama.URL_IMAGE + pesananList.get(position).detail_pesanan.get(0).barang_jasa.gambar.get(0).url_gambar).into(imageBarang);
        }
        idPesanan.setText(pesananList.get(position).id+"");
        String itemBarang = "";
        for (Pesanan.Detail_pesanan itemDetailPesanan:pesananList.get(position).detail_pesanan) {
            itemBarang += itemDetailPesanan.jumlah +"x "+itemDetailPesanan.barang_jasa.nama+System.getProperty("line.separator");
        }
        namaBarang.setText(itemBarang);
        statusPesanan.setText(pesananList.get(position).status.toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailPesanan = new Intent(context, DetailPesananActivity.class);
                detailPesanan.putExtra("idPesanan", pesananList.get(position).id);
                context.startActivity(detailPesanan);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pesananList.size();
    }
}
