package com.example.biyan.ubama.toko;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.biyan.ubama.R;
import com.example.biyan.ubama.UrlCama;
import com.example.biyan.ubama.models.Pesanan;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Biyan on 11/11/2017.
 */
public class TokoDetailPesananItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Pesanan.Detail_pesanan> detailPesananList;
    ImageView imageBarang;
    TextView namaBarang;
    TextView hargaBarang;
    TextView catatanPembeli;
    TextView jumlah;
    Context context;

    public TokoDetailPesananItemAdapter(List<Pesanan.Detail_pesanan> detailPesananList) {
        this.context = context;
        this.detailPesananList = detailPesananList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_detail_pesanan_item, parent, false);
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
        if (detailPesananList.get(position).barang_jasa.gambar.size() > 0) {
            Picasso.with(context).load(UrlCama.URL_IMAGE + detailPesananList.get(position).barang_jasa.gambar.get(0).url_gambar).fit().centerInside().into(imageBarang);
        }
        namaBarang = (TextView) holder.itemView.findViewById(R.id.nama_barang);
        hargaBarang = (TextView) holder.itemView.findViewById(R.id.harga_barang);
        catatanPembeli = (TextView) holder.itemView.findViewById(R.id.catatan_pembeli);
        jumlah = (TextView) holder.itemView.findViewById(R.id.jumlah);
        namaBarang.setText(detailPesananList.get(position).barang_jasa.nama);
        NumberFormat currency = NumberFormat.getInstance(Locale.GERMANY);
        hargaBarang.setText(String.valueOf("Rp. " + currency.format(detailPesananList.get(position).harga)).toString());
        catatanPembeli.setText(detailPesananList.get(position).catatan_pembeli);
        jumlah.setText(detailPesananList.get(position).jumlah + "");
    }

    @Override
    public int getItemCount() {
        return detailPesananList.size();
    }

}
