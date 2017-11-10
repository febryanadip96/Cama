package com.example.biyan.ubama;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Biyan on 11/6/2017.
 */

public class Pesanan {
    @SerializedName("id")
    public int id;
    @SerializedName("total_harga")
    public int total_harga;
    @SerializedName("jumlah_barang")
    public int jumlah_barang;
    @SerializedName("alamat")
    public String alamat;
    @SerializedName("alasan_ditolak")
    public String alasan_ditolak;
    @SerializedName("status")
    public String status;
    @SerializedName("waktu_selesai")
    public String waktu_selesai;
    @SerializedName("pengguna_id")
    public int pengguna_id;
    @SerializedName("created_at")
    public String created_at;
    @SerializedName("updated_at")
    public String updated_at;
    @SerializedName("detail_pesanan")
    public List<DetailPesanan> detail_pesanan;
    @SerializedName("log_pesanan")
    public List<LogPesanan> log_pesanan;
}
