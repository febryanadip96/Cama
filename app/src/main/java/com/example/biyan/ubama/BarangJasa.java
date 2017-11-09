package com.example.biyan.ubama;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Biyan on 11/3/2017.
 */

public class BarangJasa {






    @SerializedName("id")
    public int id;
    @SerializedName("nama")
    public String nama;
    @SerializedName("deskripsi")
    public String deskripsi;
    @SerializedName("jenis")
    public String jenis;
    @SerializedName("harga")
    public int harga;
    @SerializedName("baruBekas")
    public String baruBekas;
    @SerializedName("pengiriman")
    public String pengiriman;
    @SerializedName("catatan_penjual")
    public String catatan_penjual;
    @SerializedName("jumlah_dilihat")
    public int jumlah_dilihat;
    @SerializedName("jumlah_terjual")
    public int jumlah_terjual;
    @SerializedName("jumlah_komentar")
    public int jumlah_komentar;
    @SerializedName("jumlah_faq")
    public int jumlah_faq;
    @SerializedName("total_rating")
    public double total_rating;
    @SerializedName("min_pesan")
    public int min_pesan;
    @SerializedName("toko_id")
    public int toko_id;
    @SerializedName("created_at")
    public String created_at;
    @SerializedName("updated_at")
    public String updated_at;
    @SerializedName("deleted_at")
    public String deleted_at;
    @SerializedName("favorit")
    public boolean favorit;
    @SerializedName("toko")
    public Toko toko;
    @SerializedName("gambar")
    public List<Gambar> gambar;
    @SerializedName("detail_pesanan")
    public List<DetailPesanan> detail_pesanan;
    @SerializedName("tanya_jawab")
    public List<TanyaJawab> tanya_jawab;

}
