package com.example.biyan.ubama;

/**
 * Created by Biyan on 10/12/2017.
 */

public final class UrlUbama {
    private final static String IP = "http://192.168.100.17:8000";

    private final static String URL_API = IP+"/api/";

    public final static String URL_IMAGE = IP;

    //LOGIN LOGOUT REGISTER

    public final static String LOGIN = URL_API+"login";

    public final static String LOGOUT = URL_API+"logout";

    public final static String REGISTER = URL_API+"register";

    public final static String USER = URL_API+"user";

    public final static String USER_SEND_TOKEN = URL_API +"user/token";

    //HOME

    public final static String KATEGORI = URL_API+"beranda/kategori";

    public final static String FAKULTAS = URL_API+"beranda/fakultas";

    public final static String FAKULTAS_BARANG_JASA = URL_API+"fakultas/barangjasa/";

    public final static String KATEGORI_SUBKATEGORI = URL_API+"kategori/subkategori/";

    public final static String SUBKATEGORI_BARANG_JASA = URL_API+"kategori/subkategori/barangjasa/";

    public final static String USER_FEED = URL_API+"user/feed";

    public final static String USER_FAVORIT = URL_API+"user/favoritbarangjasa";

    public final static String USER_UBAH_FAVORIT = URL_API+"user/ubahfavoritbarangjasa/";

    //PESANAN

    public final static String USER_PESANAN = URL_API+"user/pesanan";

    public final static String USER_DETAIL_PESANAN = URL_API+"user/detailpesanan/";


    //BARANG JASA

    public final static String BARANG_JASA = URL_API+"barangjasa/";

    public final static String KOMENTAR_BARANG_JASA = URL_API+"barangjasa/komentar/";

    public final static String TANYA_JAWAB_BARANG_JASA = URL_API+"barangjasa/tanyajawab/";

    public final static String KIRIM_PERTANYAAN_BARANG_JASA = URL_API+"barangjasa/kirimpertanyaan/";

    public final static String CARI_BARANG_JASA = URL_API+"barangjasa/cari/";

    public final static String USER_DATA_PEMESANAN = URL_API+"user/datapemesanan/";

    public final static String USER_MASUK_KERANJANG = URL_API+"user/masukkeranjang";

    public final static String USER_KERANJANG = URL_API+"user/keranjang";

    public final static String USER_HAPUS_KERANJANG = URL_API+"user/hapuskeranjang/";

    public final static String USER_PROSES_PESANAN = URL_API+"user/prosespesanan";

    //TOKO

    public final static String TOKO = URL_API+"toko/";

    public final static String USER_UBAH_FAVORIT_TOKO = URL_API+"user/ubahfavorittoko/";

    //USER TOKO

    public final static String USER_CEK_TOKO = URL_API+"user/cektoko";

    public final static String USER_BUAT_TOKO = URL_API+"user/buattoko";

    public final static String USER_TOKO = URL_API+"user/toko";

    public final static String USER_UPDATE_TOKO = URL_API+"user/updatetoko";

    //USER TOKO TANYA JAWAB

    public final static String USER_TOKO_TANYA_JAWAB_TERJAWAB = URL_API+"user/toko/tanyajawab/terjawab";

    public final static String USER_TOKO_TANYA_JAWAB_BELUM_TERJAWAB = URL_API+"user/toko/tanyajawab/belumterjawab";

    //USER JUAL PRODUK

    public final static String USER_JUAL_PRODUK = URL_API+"user/toko/produk/simpan";

    //USER TOKO PRODUK

    public final static String USER_TOKO_PRODUK_JASA = URL_API+"user/toko/produk/jasa";

    public final static String USER_TOKO_PRODUK_BARANG = URL_API+"user/toko/produk/barang";

    //USER TOKO PESANAN

    public final static String USER_TOKO_PESANAN_TUNGGU = URL_API+"user/toko/pesanan/tunggu";

    public final static String USER_TOKO_PESANAN_DIPROSES = URL_API+"user/toko/pesanan/diproses";

    public final static String USER_TOKO_PESANAN_SELESAI = URL_API+"user/toko/pesanan/selesai";

    public final static String USER_TOKO_PESANAN_DITOLAK= URL_API+"user/toko/pesanan/ditolak";

    public final static String USER_TOKO_PESANAN_DETAIL = URL_API+"user/toko/pesanan/detail/";

    public final static String USER_TOKO_TERIMA_PESANAN = URL_API+"user/toko/terima/pesanan/";

    public final static String USER_TOKO_SELESAI_PESANAN = URL_API+"user/toko/selesai/pesanan/";

    public final static String USER_TOKO_TOLAK_PESANAN = URL_API+"user/toko/tolak/pesanan/";
}
