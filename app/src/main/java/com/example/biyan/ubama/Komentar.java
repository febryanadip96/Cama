package com.example.biyan.ubama;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Biyan on 11/8/2017.
 */

public class Komentar {
    @SerializedName("id")
    public int id;
    @SerializedName("nilai")
    public int nilai;
    @SerializedName("komentar")
    public String komentar;
    @SerializedName("detail_pesanan_id")
    public int detail_pesanan_id;
    @SerializedName("pengguna_id")
    public int pengguna_id;
    @SerializedName("created_at")
    public String created_at;
    @SerializedName("updated_at")
    public String updated_at;
}
