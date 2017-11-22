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
    @SerializedName("pengguna_id")
    public int pengguna_id;
    @SerializedName("created_at")
    public String created_at;
    @SerializedName("updated_at")
    public String updated_at;
    @SerializedName("penulis")
    public Penulis penulis;

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

    public static class Penulis {
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
}
