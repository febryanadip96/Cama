package com.example.biyan.ubama;

/**
 * Created by Biyan on 10/12/2017.
 */

public final class UrlUbama {

    private final static String IP = "http://192.168.43.243:8000";

    private final static String URL_API = IP+"/api/";

    public final static String URL_IMAGE = IP;

    //LOGIN LOGOUT REGISTER

    public final static String LOGIN = URL_API+"login";

    public final static String LOGOUT = URL_API+"logout";

    public final static String REGISTER = URL_API+"register";

    public final static String RESEND_VERIFICATION = URL_API+"resendverification";

    public final static String FORGOT_PASSWORD = URL_API+"forgotpassword";

    //USER

    public final static String USER = URL_API+"user";

    public final static String USER_FEED = URL_API+"user/feed";

    public final static String USER_FAVORIT = URL_API+"user/favoritbarangjasa";

    public final static String USER_UBAH_FAVORIT = URL_API+"user/ubahfavoritbarangjasa/";

    public final static String USER_UPDATE_PROFILE = URL_API+"user/updateprofile";

    public final static String USER_SEND_TOKEN = URL_API +"user/token";

    public final static String USER_UBAH_PASSWORD = URL_API+"user/ubahpassword";

    public final static String USER_TANYA_JAWAB = URL_API+"user/tanyajawab";

    public final static String USER_LIHAT_TANYA_JAWAB = URL_API+"user/tanyajawab/";

    //HOME

    public final static String KATEGORI = URL_API+"beranda/kategori";

    public final static String FAKULTAS = URL_API+"beranda/fakultas";

    public final static String USER_CEK_KERANJANG = URL_API+"user/cekkeranjang";

    public final static String FAKULTAS_BARANG_JASA = URL_API+"fakultas/barangjasa/";

    public final static String KATEGORI_SUBKATEGORI = URL_API+"kategori/subkategori/";

    public final static String SUBKATEGORI_BARANG_JASA = URL_API+"kategori/subkategori/barangjasa/";

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

    public final static String USER_TOKO_TANYA_JAWAB = URL_API+"user/toko/tanyajawab/";

    public final static String USER_TOKO_JAWAB_TANYA_JAWAB = URL_API+"user/toko/tanyajawab/jawab/";

    //USER TOKO PRODUK

    public final static String USER_JUAL_PRODUK = URL_API+"user/toko/produk/simpan";

    public final static String USER_TOKO_PRODUK_JASA = URL_API+"user/toko/produk/jasa";

    public final static String USER_TOKO_PRODUK_BARANG = URL_API+"user/toko/produk/barang";

    public final static String USER_TOKO_PRODUK_DETAIL = URL_API+"user/toko/produk/detail/";

    public final static String USER_TOKO_HAPUS_PRODUK = URL_API+"user/toko/produk/hapus/";

    public final static String USER_TOKO_UPDATE_PRODUK = URL_API+"user/toko/produk/update/";

    //USER TOKO PESANAN

    public final static String USER_TOKO_PESANAN_TUNGGU = URL_API+"user/toko/pesanan/tunggu";

    public final static String USER_TOKO_PESANAN_DIPROSES = URL_API+"user/toko/pesanan/diproses";

    public final static String USER_TOKO_PESANAN_SELESAI = URL_API+"user/toko/pesanan/selesai";

    public final static String USER_TOKO_PESANAN_DITOLAK= URL_API+"user/toko/pesanan/ditolak";

    public final static String USER_TOKO_PESANAN_BATAL = URL_API+"user/toko/pesanan/batal";

    public final static String USER_TOKO_PESANAN_DETAIL = URL_API+"user/toko/pesanan/detail/";

    public final static String USER_TOKO_TERIMA_PESANAN = URL_API+"user/toko/terima/pesanan/";

    public final static String USER_TOKO_TOLAK_PESANAN = URL_API+"user/toko/tolak/pesanan/";

    //USER PESANAN

    public final static String PESANAN_SELESAI = URL_API+"user/pesanan/selesai/";

    public final static String PESANAN_BATAL = URL_API+"user/pesanan/batal/";

    public final static String PESANAN_FORM_KOMENTAR = URL_API+"user/pesanan/formkomentar/";

    public final static String SIMPAN_KOMENTAR = URL_API+"user/pesanan/simpankomentar";
}
