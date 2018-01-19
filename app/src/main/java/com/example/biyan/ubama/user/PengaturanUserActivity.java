package com.example.biyan.ubama.user;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.CursorLoader;
import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biyan.ubama.R;
import com.example.biyan.ubama.UrlCama;
import com.example.biyan.ubama.UserToken;
import com.example.biyan.ubama.gps.AlamatActivity;
import com.example.biyan.ubama.models.User;
import com.google.gson.Gson;
import com.squareup.picasso.MemoryPolicy;
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

public class PengaturanUserActivity extends AppCompatActivity {

    @BindView(R.id.image_user)
    CircleImageView imageUser;
    @BindView(R.id.nama)
    TextView nama;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.telepon)
    EditText telepon;
    @BindView(R.id.alamat)
    EditText alamat;
    @BindView(R.id.alamat_map)
    EditText alamatMap;
    @BindView(R.id.layout_alamat_map)
    TextInputLayout layoutAlamatMap;
    @BindView(R.id.simpan)
    Button simpan;

    RequestQueue queue;
    User user;
    String imagePath = "";
    final int GALLERY_REQUEST = 1;
    final int PERMISSION_REQUEST_READ_STORAGE = 2;
    final int MAP_REQUEST = 2;
    double latitude = 0, longitude = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan_user);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Ubah Data Diri");
        queue = Volley.newRequestQueue(this);
        getDataUser();
        alamatMap.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    alamatMap.setShowSoftInputOnFocus(false);
                    Intent intent = new Intent(PengaturanUserActivity.this, AlamatActivity.class);
                    startActivityForResult(intent, MAP_REQUEST);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle res;
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case GALLERY_REQUEST:
                    Uri selectedImage = data.getData();
                    imagePath = getRealPathFromURI(selectedImage);
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                        imageUser.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        Log.i("TAG", "Some exception " + e);
                    }
                    break;
                case MAP_REQUEST:
                    res = data.getExtras();
                    latitude = res.getDouble("latitude");
                    longitude = res.getDouble("longitude");
                    alamatMap.setText(res.getString("alamat_map"));
                    break;
            }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void startGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
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

    public void getDataUser() {
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.setMessage("Mohon Menunggu");
        loading.setIndeterminate(true);
        loading.show();
        String url = UrlCama.USER;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                user = new Gson().fromJson(response.toString(), User.class);
                Picasso.with(PengaturanUserActivity.this).load(UrlCama.URL_IMAGE + user.pengguna.url_profile).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).fit().into(imageUser);
                nama.setText(user.name);
                email.setText(user.email);
                telepon.setText(user.pengguna.telepon);
                alamat.setText(user.pengguna.alamat);
                latitude = user.pengguna.latitude;
                longitude = user.pengguna.longitude;
                alamatMap.setText(user.pengguna.alamatMap);
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", UserToken.getToken(getApplicationContext()));
                params.put("Accept", "application/json");
                return params;
            }
        };
        request.setShouldCache(false);
        queue.add(request);
    }

    @OnClick(R.id.image_user)
    public void onImageUserClicked() {
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

    @OnClick(R.id.simpan)
    public void onSimpanClicked() {
        if (alamat.getText().toString().equals("") || telepon.getText().equals("")) {
            Toast.makeText(PengaturanUserActivity.this, "Data harus diisi dengan benar", Toast.LENGTH_LONG).show();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(PengaturanUserActivity.this);
        builder.setMessage("Anda ingin mengubah data profil Anda?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        updateDataUser();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void updateDataUser() {
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setIndeterminate(true);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.setMessage("Mohon menunggu");
        loading.show();
        String url = UrlCama.USER_UPDATE_PROFILE;
        SimpleMultiPartRequest request = new SimpleMultiPartRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        Log.d("Response", response);
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            Boolean tersimpan = jsonResponse.getBoolean("tersimpan");
                            if (tersimpan) {
                                Toast.makeText(PengaturanUserActivity.this, "Data baru telah tersimpan", Toast.LENGTH_LONG).show();
                                setResult(RESULT_OK);
                                finish();
                            }
                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                            Log.e("JSONException TokoCreateActivity", e.toString());
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
        if (!imagePath.equals("")) {
            request.addFile("gambar", imagePath);
        }
        request.addMultipartParam("alamat", "text/plain", alamat.getText().toString());
        request.addMultipartParam("alamat_map", "text/plain", alamatMap.getText().toString());
        request.addMultipartParam("latitude", "text/plain", latitude+"");
        request.addMultipartParam("longitude", "text/plain", longitude+"");
        request.addMultipartParam("telepon", "text/plain", telepon.getText().toString());
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                0,  // maxNumRetries = 0 means no retry
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);
        queue.add(request);
    }

    @OnClick(R.id.alamat_map)
    public void onAlamatMapClicked() {
        alamatMap.setShowSoftInputOnFocus(false);
        Intent intent = new Intent(PengaturanUserActivity.this, AlamatActivity.class);
        startActivityForResult(intent,MAP_REQUEST);
    }
}
