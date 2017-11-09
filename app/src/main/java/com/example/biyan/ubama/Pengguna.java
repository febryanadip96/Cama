package com.example.biyan.ubama;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Biyan on 11/9/2017.
 */

public class Pengguna {
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
    @SerializedName("user")
    public User user;
    @SerializedName("created_at")
    public String created_at;
    @SerializedName("updated_at")
    public String updated_at;
    @SerializedName("deleted_at")
    public String deleted_at;
}
