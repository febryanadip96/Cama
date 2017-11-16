package com.example.biyan.ubama.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Biyan on 11/15/2017.
 */

public class Keranjang {

    @SerializedName("id")
    public int id;
    @SerializedName("nama")
    public String nama;
    @SerializedName("deskripsi")
    public String deskripsi;
    @SerializedName("alamat")
    public String alamat;
    @SerializedName("catatan_toko")
    public String catatan_toko;
    @SerializedName("open")
    public int open;
    @SerializedName("total_rating")
    public int total_rating;
    @SerializedName("slogan")
    public String slogan;
    @SerializedName("url_profile")
    public String url_profile;
    @SerializedName("pengguna_id")
    public int pengguna_id;
    @SerializedName("created_at")
    public String created_at;
    @SerializedName("updated_at")
    public String updated_at;
    @SerializedName("barangJasa")
    public List<BarangJasa> barangJasa;

    public static class Data_keranjang {
        @SerializedName("pengguna_id")
        public int pengguna_id;
        @SerializedName("barang_jasa_id")
        public int barang_jasa_id;
        @SerializedName("jumlah")
        public int jumlah;
        @SerializedName("catatan_pembeli")
        public String catatan_pembeli;
        @SerializedName("alamat")
        public String alamat;
    }

    public static class Gambar {
        @com.google.gson.annotations.SerializedName("id")
        public int id;
        @com.google.gson.annotations.SerializedName("barang_jasa_id")
        public int barang_jasa_id;
        @com.google.gson.annotations.SerializedName("url_gambar")
        public String url_gambar;
        @com.google.gson.annotations.SerializedName("created_at")
        public String created_at;
        @com.google.gson.annotations.SerializedName("updated_at")
        public String updated_at;
        @com.google.gson.annotations.SerializedName("deleted_at")
        public String deleted_at;
    }

    public static class BarangJasa {
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
        @SerializedName("data_keranjang")
        public Data_keranjang data_keranjang;
        @SerializedName("gambar")
        public List<Gambar> gambar;
    }
}
