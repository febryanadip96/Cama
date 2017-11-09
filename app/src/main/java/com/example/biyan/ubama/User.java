package com.example.biyan.ubama;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Biyan on 11/9/2017.
 */

public class User {
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
    @SerializedName("pengguna")
    public Pengguna pengguna;
}
