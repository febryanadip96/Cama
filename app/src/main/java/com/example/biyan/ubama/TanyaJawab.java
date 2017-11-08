package com.example.biyan.ubama;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Biyan on 11/8/2017.
 */

public class TanyaJawab {
    @SerializedName("id")
    public int id;
    @SerializedName("pertanyaan")
    public String pertanyaan;
    @SerializedName("waktu_tanya")
    public String waktu_tanya;
    @SerializedName("jawaban")
    public String jawaban;
    @SerializedName("waktu_jawab")
    public String waktu_jawab;
    @SerializedName("pengguna_id")
    public int pengguna_id;
    @SerializedName("barang_jasa_id")
    public int barang_jasa_id;
}
