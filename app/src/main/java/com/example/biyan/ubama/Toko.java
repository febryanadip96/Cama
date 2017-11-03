package com.example.biyan.ubama;

import java.util.List;

/**
 * Created by Biyan on 11/3/2017.
 */

public class Toko {
    private String pemilik;
    private String pemilik_email;
    private String pemilik_telpon;
    private int id;
    private String nama;
    private String deskripsi;
    private String alamat;
    private String catatan_toko;
    private boolean open;
    private float total_rating;
    private String slogan;
    private List<BarangJasa> barangJasaList;


    public String getPemilik() {
        return pemilik;
    }

    public void setPemilik(String pemilik) {
        this.pemilik = pemilik;
    }

    public String getPemilik_email() {
        return pemilik_email;
    }

    public void setPemilik_email(String pemilik_email) {
        this.pemilik_email = pemilik_email;
    }

    public String getPemilik_telpon() {
        return pemilik_telpon;
    }

    public void setPemilik_telpon(String pemilik_telpon) {
        this.pemilik_telpon = pemilik_telpon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getCatatan_toko() {
        return catatan_toko;
    }

    public void setCatatan_toko(String catatan_toko) {
        this.catatan_toko = catatan_toko;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public float getTotal_rating() {
        return total_rating;
    }

    public void setTotal_rating(float total_rating) {
        this.total_rating = total_rating;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public List<BarangJasa> getBarangJasaList() {
        return barangJasaList;
    }

    public void setBarangJasaList(List<BarangJasa> barangJasaList) {
        this.barangJasaList = barangJasaList;
    }
}
