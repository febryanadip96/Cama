package com.example.biyan.ubama;

/**
 * Created by Biyan on 10/12/2017.
 */

public final class UrlUbama {
    private final static String IP = "http://192.168.1.115:8000";

    private final static String URL_API = IP+"/api/";

    public final static String URL_IMAGE = IP;

    public final static String login = URL_API+"login";

    public final static String logout = URL_API+"logout";

    public final static String Register = URL_API+"register";

    public final static String User = URL_API+"user";

    public final static String BerandaKategori = URL_API+"beranda/kategori";

    public final static String BerandaFakultas = URL_API+"beranda/fakultas";

    public final static String UserFeed = URL_API+"user/feed";

    public final static String UserFavoritBarangJasa = URL_API+"user/favoritbarangjasa";

    public final static String UserUbahFavoritBarangJasa = URL_API+"user/ubahfavoritbarangjasa/";

    public final static String UserPesanan = URL_API+"user/pesanan";

    public final static String BarangJasa = URL_API+"barangjasa/";

    public final static String Cari = URL_API+"cari/";

    public final static String UserDataPemesanan = URL_API+"user/datapemesanan/";

    public final static String UserMasukKeranjang = URL_API+"user/masukkeranjang";

    public final static String BarangJasaFakultas = URL_API+"barangjasafakultas/";

    public final static String SubkateogirKategori = URL_API+"subkategorikategori/";

    public final static String BarangJasaSubkategori = URL_API+"barangjasasubkategori/";

    public final static String UserDetailPesanan = URL_API+"user/detailpesanan/";


}
