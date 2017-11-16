package com.example.biyan.ubama.models;

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

    public static class Toko {
        @com.google.gson.annotations.SerializedName("id")
        public int id;
        @com.google.gson.annotations.SerializedName("nama")
        public String nama;
        @com.google.gson.annotations.SerializedName("deskripsi")
        public String deskripsi;
        @com.google.gson.annotations.SerializedName("alamat")
        public String alamat;
        @com.google.gson.annotations.SerializedName("catatan_toko")
        public String catatan_toko;
        @com.google.gson.annotations.SerializedName("open")
        public int open;
        @com.google.gson.annotations.SerializedName("total_rating")
        public int total_rating;
        @com.google.gson.annotations.SerializedName("slogan")
        public String slogan;
        @com.google.gson.annotations.SerializedName("url_profile")
        public String url_profile;
        @com.google.gson.annotations.SerializedName("pemilik")
        public Pengguna pemilik;
        @com.google.gson.annotations.SerializedName("created_at")
        public String created_at;
        @com.google.gson.annotations.SerializedName("updated_at")
        public String updated_at;
        @com.google.gson.annotations.SerializedName("barang_jasa")
        public List<BarangJasa> barang_jasa;
        @com.google.gson.annotations.SerializedName("barangJasa")
        public List<BarangJasa> barangJasa;
    }

    public static class Pengguna {
        @SerializedName("id")
        public int id;
        @SerializedName("jenis_kelamin")
        public String jenis_kelamin;
        @SerializedName("telepon")
        public String telepon;
        @SerializedName("alamat")
        public String alamat;
        @SerializedName("notif_number")
        public String notif_number;
        @SerializedName("url_profile")
        public String url_profile;
        @SerializedName("user")
        public User user;
        @SerializedName("created_at")
        public String created_at;
        @SerializedName("updated_at")
        public String updated_at;
        @SerializedName("deleted_at")
        public String deleted_at;
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

}
