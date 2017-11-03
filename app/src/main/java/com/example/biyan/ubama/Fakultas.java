package com.example.biyan.ubama;

/**
 * Created by Biyan on 11/2/2017.
 */

public class Fakultas {
    private int id;
    private String nama;
    private String url_gambar;

    public Fakultas(int id, String nama, String url_gambar){
        this.id = id;
        this.nama = nama;
        this.url_gambar = url_gambar;
    }


    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getUrl_gambar() {
        return url_gambar;
    }
}
