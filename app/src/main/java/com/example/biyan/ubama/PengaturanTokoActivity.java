package com.example.biyan.ubama;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
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

public class PengaturanTokoActivity extends AppCompatActivity {

    @BindView(R.id.image_toko)
    CircleImageView imageToko;
    @BindView(R.id.nama_toko)
    TextView namaToko;
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

    RequestQueue queue;
    Toko toko;
    ProgressDialog loading;
    String imagePath;

    final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan_toko);
        ButterKnife.bind(this);
        queue = Volley.newRequestQueue(this);
        getUserToko();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK)
            switch (requestCode){
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

    public void getUserToko(){
        String url = UrlUbama.USER_TOKO;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                toko = new Gson().fromJson(response.toString(), Toko.class);
                Picasso.with(PengaturanTokoActivity.this).load(UrlUbama.URL_IMAGE + toko.url_profile).into(imageToko);
                namaToko.setText(toko.nama);
                sloganToko.setText(toko.slogan);
                deskripsiToko.setText(toko.deskripsi);
                alamatToko.setText(toko.alamat);
                catatanToko.setText(toko.catatan_toko);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Volley Ubah Favorit", error.toString());
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

    @OnClick(R.id.image_toko)
    public void onImageTokoClicked() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }

    @OnClick(R.id.simpan)
    public void onSimpanClicked() {
        loading = new ProgressDialog(this);
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
                            if(update){

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
                Log.e("Error Volley", error.getMessage());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", UserToken.getToken(getApplicationContext()));
                params.put("Accept", "application/json");
                return params;
            }
        };
        request.addFile("gambar", imagePath );
        request.addMultipartParam("deskripsi","text/plain", deskripsiToko.getText().toString());
        request.addMultipartParam("slogan", "text/plain",sloganToko.getText().toString());
        request.addMultipartParam("catatan_toko","text/plain", catatanToko.getText().toString());
        request.addMultipartParam("alamat","text/plain", alamatToko.getText().toString());
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                0,  // maxNumRetries = 0 means no retry
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
}
