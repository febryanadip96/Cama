package com.example.biyan.ubama.toko;

import android.Manifest;
import android.app.Activity;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.biyan.ubama.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TokoJualProdukActivity extends AppCompatActivity {

    @BindView(R.id.image1)
    ImageView image1;
    @BindView(R.id.image2)
    ImageView image2;
    @BindView(R.id.image3)
    ImageView image3;
    @BindView(R.id.image4)
    ImageView image4;
    @BindView(R.id.nama_produk)
    EditText namaProduk;
    @BindView(R.id.deskripsi_produk)
    EditText deskripsiProduk;
    @BindView(R.id.catatan_penjual)
    EditText catatanPenjual;
    @BindView(R.id.harga_produk)
    EditText hargaProduk;
    @BindView(R.id.minimal_pembelian)
    EditText minimalPembelian;
    @BindView(R.id.kategori)
    RelativeLayout kategori;
    @BindView(R.id.fakultas)
    RelativeLayout fakultas;
    @BindView(R.id.baru)
    RadioButton baru;
    @BindView(R.id.bekas)
    RadioButton bekas;
    @BindView(R.id.kondisi)
    RadioGroup kondisi;
    @BindView(R.id.barang)
    RadioButton barang;
    @BindView(R.id.jasa)
    RadioButton jasa;
    @BindView(R.id.golongan)
    RadioGroup golongan;
    @BindView(R.id.simpan)
    Button simpan;
    @BindView(R.id.kurang)
    ImageButton kurang;
    @BindView(R.id.tambah)
    ImageButton tambah;

    int pilih = 0;
    String imagePath1, imagePath2, imagePath3, imagePath4;
    final int GALLERY_REQUEST = 1;
    final int PERMISSION_REQUEST_READ_STORAGE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toko_jual_produk);
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case GALLERY_REQUEST:
                    Uri selectedImage = data.getData();
                    switch (pilih) {
                        case 1:
                            imagePath1 = getRealPathFromURI(selectedImage);
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                                image1.setImageBitmap(bitmap);
                            } catch (IOException e) {
                                Log.i("TAG", "Some exception " + e);
                            }
                            break;
                        case 2:
                            imagePath2 = getRealPathFromURI(selectedImage);
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                                image2.setImageBitmap(bitmap);
                            } catch (IOException e) {
                                Log.i("TAG", "Some exception " + e);
                            }
                            break;
                        case 3:
                            imagePath3 = getRealPathFromURI(selectedImage);
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                                image3.setImageBitmap(bitmap);
                            } catch (IOException e) {
                                Log.i("TAG", "Some exception " + e);
                            }
                            break;
                        case 4:
                            imagePath4 = getRealPathFromURI(selectedImage);
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                                image4.setImageBitmap(bitmap);
                            } catch (IOException e) {
                                Log.i("TAG", "Some exception " + e);
                            }
                            break;
                    }
                    break;
            }
        }
    }


    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_READ_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startGallery();
                }
                return;
            }
        }
    }

    @OnClick({R.id.image1, R.id.image2, R.id.image3, R.id.image4})
    public void onImageClicked(View view) {
        switch (view.getId()) {
            case R.id.image1:
                pilih = 1;
                break;
            case R.id.image2:
                pilih = 2;
                break;
            case R.id.image3:
                pilih = 3;
                break;
            case R.id.image4:
                pilih = 4;
                break;
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_READ_STORAGE);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_READ_STORAGE);
            }
        } else {
            startGallery();
        }
    }

    public void startGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }

    @OnClick(R.id.kategori)
    public void onKategoriClicked() {
    }

    @OnClick(R.id.fakultas)
    public void onFakultasClicked() {
    }

    @OnClick(R.id.simpan)
    public void onSimpanClicked() {
    }

    @OnClick({R.id.kurang, R.id.tambah})
    public void ubahJumlah(View view) {
        int minimal = Integer.parseInt(minimalPembelian.getText().toString()) + 0;
        switch (view.getId()) {
            case R.id.kurang:
                if (minimal <= 1) {
                    minimal = 1;
                    minimalPembelian.setText(minimal + "");
                    Toast.makeText(this, "Pemesanan minimal 1", Toast.LENGTH_LONG).show();
                } else {
                    minimal--;
                    minimalPembelian.setText(minimal + "");
                }
                break;
            case R.id.tambah:
                minimal++;
                minimalPembelian.setText(minimal + "");
                break;
        }
    }
}
