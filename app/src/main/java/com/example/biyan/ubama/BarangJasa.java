package com.example.biyan.ubama;

import java.util.List;

/**
 * Created by Biyan on 11/3/2017.
 */

public class BarangJasa {
    private int id;
    private String nama;
    private String deskripsi;
    private String jenis;
    private int harga;
    private String baruBekas;
    private String pengiriman;
    private String catatan_penjual;
    private int jumlah_dilihat;
    private int jumlah_terjual;
    private int jumlah_batal;
    private int jumlah_stok;
    private float total_rating;
    private int min_pesan;
    private List<String> url_gambar;
    private Toko toko;



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

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getBaruBekas() {
        return baruBekas;
    }

    public void setBaruBekas(String baruBekas) {
        this.baruBekas = baruBekas;
    }

    public String getPengiriman() {
        return pengiriman;
    }

    public void setPengiriman(String pengiriman) {
        this.pengiriman = pengiriman;
    }

    public String getCatatan_penjual() {
        return catatan_penjual;
    }

    public void setCatatan_penjual(String catatan_penjual) {
        this.catatan_penjual = catatan_penjual;
    }

    public int getJumlah_dilihat() {
        return jumlah_dilihat;
    }

    public void setJumlah_dilihat(int jumlah_dilihat) {
        this.jumlah_dilihat = jumlah_dilihat;
    }

    public int getJumlah_terjual() {
        return jumlah_terjual;
    }

    public void setJumlah_terjual(int jumlah_terjual) {
        this.jumlah_terjual = jumlah_terjual;
    }

    public int getJumlah_batal() {
        return jumlah_batal;
    }

    public void setJumlah_batal(int jumlah_batal) {
        this.jumlah_batal = jumlah_batal;
    }

    public int getJumlah_stok() {
        return jumlah_stok;
    }

    public void setJumlah_stok(int jumlah_stok) {
        this.jumlah_stok = jumlah_stok;
    }

    public float getTotal_rating() {
        return total_rating;
    }

    public void setTotal_rating(float total_rating) {
        this.total_rating = total_rating;
    }

    public int getMin_pesan() {
        return min_pesan;
    }

    public void setMin_pesan(int min_pesan) {
        this.min_pesan = min_pesan;
    }

    public List<String> getUrl_gambar() {
        return url_gambar;
    }

    public void setUrl_gambar(List<String> url_gambar) {
        this.url_gambar = url_gambar;
    }

    public Toko getToko() {
        return toko;
    }

    public void setToko(Toko toko) {
        this.toko = toko;
    }
}
