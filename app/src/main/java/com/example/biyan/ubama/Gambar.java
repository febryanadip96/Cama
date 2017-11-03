package com.example.biyan.ubama;

/**
 * Created by Biyan on 11/4/2017.
 */

public class Gambar {
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
