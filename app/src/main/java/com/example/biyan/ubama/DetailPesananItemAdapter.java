package com.example.biyan.ubama;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Biyan on 11/11/2017.
 */
public class DetailPesananItemAdapter extends BaseAdapter {

    private List<DetailPesanan> detailPesananList;
    private ImageView imageBarang;
    private TextView namaBarang;
    private TextView hargaBarang;
    private TextView jumlah;
    private Context context;

    public DetailPesananItemAdapter(Context context, List<DetailPesanan> detailPesananList) {
        this.context = context;
        this.detailPesananList = detailPesananList;
    }

    @Override
    public int getCount() {
        return detailPesananList.size();
    }

    @Override
    public DetailPesanan getItem(int position) {
        return detailPesananList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_pesanan_item, parent, false);
        }
        imageBarang = (ImageView) convertView.findViewById(R.id.image_barang);
        if (detailPesananList.get(position).barang_jasa.gambar.size() > 0) {
            Picasso.with(context).load(UrlUbama.URL_IMAGE + detailPesananList.get(position).barang_jasa.gambar.get(0).url_gambar).into(imageBarang);
        }
        namaBarang = (TextView) convertView.findViewById(R.id.nama_barang);
        hargaBarang = (TextView) convertView.findViewById(R.id.harga_barang);
        jumlah = (TextView) convertView.findViewById(R.id.jumlah);
        namaBarang.setText(getItem(position).barang_jasa.nama);
        NumberFormat currency = NumberFormat.getInstance(Locale.GERMANY);
        hargaBarang.setText(String.valueOf("Rp. " + currency.format(getItem(position).harga)).toString());
        jumlah.setText(getItem(position).jumlah + "");
        return convertView;
    }
}
