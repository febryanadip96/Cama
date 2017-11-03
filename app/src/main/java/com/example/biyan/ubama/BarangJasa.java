package com.example.biyan.ubama;

import java.util.List;

/**
 * Created by Biyan on 11/3/2017.
 */

public class BarangJasa {
    @com.google.gson.annotations.SerializedName("id")
    public int id;
    @com.google.gson.annotations.SerializedName("nama")
    public String nama;
    @com.google.gson.annotations.SerializedName("deskripsi")
    public String deskripsi;
    @com.google.gson.annotations.SerializedName("jenis")
    public String jenis;
    @com.google.gson.annotations.SerializedName("harga")
    public int harga;
    @com.google.gson.annotations.SerializedName("baruBekas")
    public String baruBekas;
    @com.google.gson.annotations.SerializedName("pengiriman")
    public String pengiriman;
    @com.google.gson.annotations.SerializedName("catatan_penjual")
    public String catatan_penjual;
    @com.google.gson.annotations.SerializedName("jumlah_dilihat")
    public int jumlah_dilihat;
    @com.google.gson.annotations.SerializedName("jumlah_terjual")
    public int jumlah_terjual;
    @com.google.gson.annotations.SerializedName("jumlah_batal")
    public int jumlah_batal;
    @com.google.gson.annotations.SerializedName("jumlah_stok")
    public int jumlah_stok;
    @com.google.gson.annotations.SerializedName("total_rating")
    public double total_rating;
    @com.google.gson.annotations.SerializedName("min_pesan")
    public int min_pesan;
    @com.google.gson.annotations.SerializedName("toko_id")
    public int toko_id;
    @com.google.gson.annotations.SerializedName("created_at")
    public String created_at;
    @com.google.gson.annotations.SerializedName("updated_at")
    public String updated_at;
    @com.google.gson.annotations.SerializedName("deleted_at")
    public String deleted_at;
    @com.google.gson.annotations.SerializedName("toko")
    public Toko toko;
    @com.google.gson.annotations.SerializedName("gambar")
    public List<Gambar> gambar;
}
