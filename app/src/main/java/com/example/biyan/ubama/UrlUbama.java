package com.example.biyan.ubama;

/**
 * Created by Biyan on 10/12/2017.
 */

public final class UrlUbama {
    private final static String IP = "http://192.168.1.115:8000";

    private final static String URL_API = IP+"/api/";

    public final static String URL_IMAGE = IP;

    public final static String LOGIN = URL_API+"login";

    public final static String LOGOUT = URL_API+"logout";

    public final static String REGISTER = URL_API+"register";

    public final static String USER = URL_API+"user";

    public final static String BERANDAKATEGORI = URL_API+"beranda/kategori";

    public final static String BERANDAFAKULTAS = URL_API+"beranda/fakultas";

    public final static String USERFEED = URL_API+"user/feed";

    public final static String USER_FAVORIT_BARANGJASA = URL_API+"user/favoritbarangjasa";

    public final static String USER_UBAH_FAVORIT_BARANG_JADA = URL_API+"user/ubahfavoritbarangjasa/";

    public final static String USER_PESANAN = URL_API+"user/pesanan";

    public final static String BARANG_JASA = URL_API+"barangjasa/";

    public final static String CARI = URL_API+"cari/";

    public final static String USER_DATA_PESANAN = URL_API+"user/datapemesanan/";

    public final static String USER_MASUK_KERANJANG = URL_API+"user/masukkeranjang";

    public final static String BARANG_JASA_FAKULTAS = URL_API+"barangjasafakultas/";

    public final static String SUBKATEGORI_KATEGORI = URL_API+"subkategorikategori/";

    public final static String BARANG_JASA_SUBKATEGORI = URL_API+"barangjasasubkategori/";

    public final static String USER_DETAIL_PESANAN = URL_API+"user/detailpesanan/";

    public final static String USERKERANJANG = URL_API+"user/keranjang";

    public final static String HAPUS_BARANG_JASA_DI_KERANJANG = URL_API+"user/keranjang/";

    public final static String CEK_TOKO = URL_API+"user/cektoko";
}
