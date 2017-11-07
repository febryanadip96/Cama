package com.example.biyan.ubama;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Biyan on 11/6/2017.
 */

public class DetailPesanan {
    @SerializedName("id")
    public int id;
    @SerializedName("harga")
    public int harga;
    @SerializedName("jumlah")
    public int jumlah;
    @SerializedName("subtotal")
    public int subtotal;
    @SerializedName("catatan_pembeli")
    public String catatan_pembeli;
    @SerializedName("barang_jasa_id")
    public int barang_jasa_id;
    @SerializedName("pesanan_id")
    public int pesanan_id;
    @SerializedName("created_at")
    public String created_at;
    @SerializedName("updated_at")
    public String updated_at;
    @SerializedName("barang_jasa")
    public BarangJasa barang_jasa;
}
