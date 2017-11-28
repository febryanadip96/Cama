package com.example.biyan.ubama.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Biyan on 11/10/2017.
 */

public class Subkategori {
    @SerializedName("id")
    public int id;
    @SerializedName("nama")
    public String nama;
    @SerializedName("kategori_id")
    public int kategori_id;
    @SerializedName("kategori")
    public Kategori kategori;

    public static class Kategori {
        @SerializedName("id")
        public int id;
        @SerializedName("nama")
        public String nama;
        @SerializedName("url_gambar")
        public String url_gambar;
    }
}
