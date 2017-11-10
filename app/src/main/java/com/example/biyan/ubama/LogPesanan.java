package com.example.biyan.ubama;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Biyan on 11/10/2017.
 */

public class LogPesanan {
    @SerializedName("id")
    public int id;
    @SerializedName("keterangan")
    public String keterangan;
    @SerializedName("pesanan_id")
    public int pesanan_id;
    @SerializedName("created_at")
    public String created_at;
    @SerializedName("updated_at")
    public String updated_at;
}
