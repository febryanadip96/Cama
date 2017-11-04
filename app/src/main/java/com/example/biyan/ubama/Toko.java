package com.example.biyan.ubama;

import java.util.List;

/**
 * Created by Biyan on 11/3/2017.
 */

public class Toko {
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
    @com.google.gson.annotations.SerializedName("pengguna_id")
    public int pengguna_id;
    @com.google.gson.annotations.SerializedName("created_at")
    public String created_at;
    @com.google.gson.annotations.SerializedName("updated_at")
    public String updated_at;
    @com.google.gson.annotations.SerializedName("barang_jasa")
    public List<BarangJasa> barang_jasa;
}
