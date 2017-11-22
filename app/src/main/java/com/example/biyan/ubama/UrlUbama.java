package com.example.biyan.ubama;

/**
 * Created by Biyan on 10/12/2017.
 */

public final class UrlUbama {
    private final static String IP = "http://192.168.1.115:8000";

    private final static String URL_API = IP+"/api/";

    public final static String URL_IMAGE = IP;

    //LOGIN LOGOUT REGISTER

    public final static String LOGIN = URL_API+"login";

    public final static String LOGOUT = URL_API+"logout";

    public final static String REGISTER = URL_API+"register";

    public final static String USER = URL_API+"user";

    //HOME

    public final static String KATEGORI = URL_API+"beranda/kategori";

    public final static String FAKULTAS = URL_API+"beranda/fakultas";

    public final static String BARANG_JASA_FAKULTAS = URL_API+"barangjasafakultas/";

    public final static String SUBKATEGORI_KATEGORI = URL_API+"subkategorikategori/";

    public final static String BARANG_JASA_SUBKATEGORI = URL_API+"barangjasasubkategori/";

    public final static String USER_FEED = URL_API+"userfeed";

    public final static String USER_FAVORIT = URL_API+"userfavoritbarangjasa";

    public final static String USER_UBAH_FAVORIT = URL_API+"userubahfavoritbarangjasa/";

    //PESANAN

    public final static String USER_PESANAN = URL_API+"userpesanan";

    public final static String USER_DETAIL_PESANAN = URL_API+"userdetailpesanan/";


    //BARANG JASA

    public final static String BARANG_JASA = URL_API+"barangjasa/";

    public final static String CARI = URL_API+"cari/";

    public final static String USER_DATA_PEMESANAN = URL_API+"userdatapemesanan/";

    public final static String USER_MASUK_KERANJANG = URL_API+"usermasukkeranjang";

    public final static String USER_KERANJANG = URL_API+"userkeranjang";

    public final static String USER_HAPUS_KERANJANG = URL_API+"userhapuskeranjang/";

    public final static String KOMENTAR_BARANG_JASA = URL_API+"komentarbarangjasa/";

    public final static String TANYA_JAWAB_BARANG_JASA = URL_API+"tanyajawabbarangjasa/";

    public final static String USER_KIRIM_PERTANYAAN_BARANG_JASA = URL_API+"userkirimpertanyaanbarangjasa/";

    public final static String USER_PROSES_PESANAN = URL_API+"userprosespesanan";

    //TOKO

    public final static String CEK_TOKO = URL_API+"cektoko";

    public final static String DATA_TOKO = URL_API+"toko/";

    public final static String BUAT_TOKO = URL_API+"buattoko";

    public final static String UBAH_FAVORIT_TOKO = URL_API+"ubahfavorittoko/";

    public final static String USER_TOKO = URL_API+"usertoko";
}
