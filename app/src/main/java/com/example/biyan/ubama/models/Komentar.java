package com.example.biyan.ubama.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Biyan on 11/16/2017.
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
    @SerializedName("created_at")
    public String created_at;
    @SerializedName("updated_at")
    public String updated_at;
    @SerializedName("detail_pesanan")
    public Detail_pesanan detail_pesanan;

    public static class User {
        @SerializedName("id")
        public int id;
        @SerializedName("name")
        public String name;
        @SerializedName("email")
        public String email;
        @SerializedName("created_at")
        public String created_at;
        @SerializedName("updated_at")
        public String updated_at;
        @SerializedName("deleted_at")
        public String deleted_at;
    }

    public static class Pemesan {
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
        @SerializedName("user_id")
        public int user_id;
        @SerializedName("created_at")
        public String created_at;
        @SerializedName("updated_at")
        public String updated_at;
        @SerializedName("deleted_at")
        public String deleted_at;
        @SerializedName("user")
        public User user;
    }

    public static class Pesanan {
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
        @SerializedName("pemesan")
        public Pemesan pemesan;
    }

    public static class Detail_pesanan {
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
        @SerializedName("pesanan")
        public Pesanan pesanan;
    }
}
