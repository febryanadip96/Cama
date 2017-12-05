package com.example.biyan.ubama.toko;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import com.example.biyan.ubama.R;
import com.example.biyan.ubama.UrlUbama;
import com.example.biyan.ubama.UserToken;
import com.example.biyan.ubama.models.Toko;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class TokoPengaturanActivity extends AppCompatActivity {

    @BindView(R.id.image_toko)
    CircleImageView imageToko;
    @BindView(R.id.nama_toko)
    TextView namaToko;
    @BindView(R.id.edit_nama_toko)
    EditText editNamaToko;
    @BindView(R.id.slogan_toko)
    EditText sloganToko;
    @BindView(R.id.deskripsi_toko)
    EditText deskripsiToko;
    @BindView(R.id.alamat_toko)
    EditText alamatToko;
    @BindView(R.id.catatan_toko)
    EditText catatanToko;
    @BindView(R.id.simpan)
    Button simpan;
    @BindView(R.id.layout)
    LinearLayout layout;
    @BindView(R.id.layout_nama_toko)
    TextInputLayout layoutNamaToko;
    @BindView(R.id.layout_slogan_toko)
    TextInputLayout layoutSloganToko;
    @BindView(R.id.layout_deskripsi_toko)
    TextInputLayout layoutDeskripsiToko;
    @BindView(R.id.layout_alamat_toko)
    TextInputLayout layoutAlamatToko;
    @BindView(R.id.layout_catatan_toko)
    TextInputLayout layoutCatatanToko;

    RequestQueue queue;
    Toko toko;
    String imagePath;

    final int GALLERY_REQUEST = 1;
    final int PERMISSION_REQUEST_READ_STORAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toko_pengaturan);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        queue = Volley.newRequestQueue(this);
        getUserToko();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case GALLERY_REQUEST:
                    Uri selectedImage = data.getData();
                    imagePath = getRealPathFromURI(selectedImage);
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                        imageToko.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        Log.i("TAG", "Some exception " + e);
                    }
                    break;
            }
    }

    public void getUserToko() {
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.setMessage("Mohon Menunggu");
        loading.setIndeterminate(true);
        loading.show();
        String url = UrlUbama.USER_TOKO;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.dismiss();
                toko = new Gson().fromJson(response.toString(), Toko.class);
                Picasso.with(TokoPengaturanActivity.this).load(UrlUbama.URL_IMAGE + toko.url_profile).into(imageToko);
                namaToko.setText(toko.nama);
                if (toko.lewati == 1) {
                    editNamaToko.setVisibility(View.VISIBLE);
                    layoutNamaToko.setVisibility(View.VISIBLE);
                    editNamaToko.setText(toko.nama);
                } else {
                    editNamaToko.setVisibility(View.GONE);
                    layoutNamaToko.setVisibility(View.GONE);
                }
                sloganToko.setText(toko.slogan);
                deskripsiToko.setText(toko.deskripsi);
                alamatToko.setText(toko.alamat);
                catatanToko.setText(toko.catatan_toko);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Log.e("Error Volley", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", UserToken.getToken(getApplicationContext()));
                params.put("Accept", "application/json");
                return params;
            }
        };
        request.setShouldCache(false);
        queue.add(request);
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

    @OnClick(R.id.image_toko)
    public void onImageTokoClicked() {
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

    @OnClick(R.id.simpan)
    public void onSimpanClicked() {
        if(toko.lewati == 1){
            if(namaToko.getText().toString().equals("")){
                layoutNamaToko.setError("Nama toko harus diisi");
                namaToko.requestFocus();
                return;
            } else {
                layoutNamaToko.setError(null);
            }
        }
        if(sloganToko.getText().toString().equals("")){
            layoutSloganToko.setError("Slogan toko harus diisi");
            sloganToko.requestFocus();
            return;
        } else {
            layoutSloganToko.setError(null);
        }
        if(deskripsiToko.getText().toString().equals("")){
            layoutDeskripsiToko.setError("Deskripsi toko harus diisi");
            deskripsiToko.requestFocus();
            return;
        } else {
            layoutDeskripsiToko.setError(null);
        }
        if(alamatToko.getText().toString().equals("")){
            layoutAlamatToko.setError("Alamat toko harus diisi");
            alamatToko.requestFocus();
            return;
        } else {
            layoutAlamatToko.setError(null);
        }
        if(catatanToko.getText().toString().equals("")){
            layoutCatatanToko.setError("Catatan toko harus diisi");
            catatanToko.requestFocus();
            return;
        } else {
            layoutCatatanToko.setError(null);
        }
        simpanDataToko();
    }

    public void simpanDataToko(){
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setIndeterminate(true);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.setMessage("Mohon menunggu");
        loading.show();
        String url = UrlUbama.USER_UPDATE_TOKO;
        SimpleMultiPartRequest request = new SimpleMultiPartRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            Boolean update = jsonResponse.getBoolean("update");
                            if (update) {

                                finish();
                            }

                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                            Log.e("JSONException", e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Log.e("Error Volley", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", UserToken.getToken(getApplicationContext()));
                params.put("Accept", "application/json");
                return params;
            }
        };
        request.addFile("gambar", imagePath);
        if (toko.lewati == 1) {
            request.addMultipartParam("nama", "text/plain", editNamaToko.getText().toString());
        }
        request.addMultipartParam("deskripsi", "text/plain", deskripsiToko.getText().toString());
        request.addMultipartParam("slogan", "text/plain", sloganToko.getText().toString());
        request.addMultipartParam("catatan_toko", "text/plain", catatanToko.getText().toString());
        request.addMultipartParam("alamat", "text/plain", alamatToko.getText().toString());
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                0,  // maxNumRetries = 0 means no retry
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
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
}
