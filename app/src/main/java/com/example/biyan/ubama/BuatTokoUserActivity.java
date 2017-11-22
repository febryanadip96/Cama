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
import android.widget.ImageView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BuatTokoUserActivity extends AppCompatActivity {

    @BindView(R.id.image_toko)
    ImageView imageToko;
    @BindView(R.id.nama_toko)
    EditText namaToko;
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
    ProgressDialog loading;
    String imagePath;

    final int GALLERY_REQUEST = 1;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_toko_user);
        ButterKnife.bind(this);
        queue = Volley.newRequestQueue(this);
    }

    @OnClick(R.id.image_toko)
    public void pilihGambar() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }

    @OnClick(R.id.simpan)
    public void simpanToko() {
        loading = new ProgressDialog(this);
        loading.setIndeterminate(true);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.setMessage("Mohon menunggu");
        loading.show();
        String url = UrlUbama.BUAT_TOKO;
        SimpleMultiPartRequest buatTokoRequest = new SimpleMultiPartRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        Log.d("Response", response);
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            Boolean toko = jsonResponse.getBoolean("toko");
                            if(!jsonResponse.isNull("message")){
                                String message = jsonResponse.getString("message");
                            }
                            if(toko){
                                Intent tokoUser = new Intent(BuatTokoUserActivity.this, TokoUserActivity.class);
                                startActivity(tokoUser);
                                finish();
                            }

                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                            Log.e("JSONException BuatTokoUserActivity", e.toString());
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
        buatTokoRequest.addFile("gambar", imagePath );
        buatTokoRequest.addMultipartParam("nama", "text/plain",namaToko.getText().toString());
        buatTokoRequest.addMultipartParam("deskripsi","text/plain", deskripsiToko.getText().toString());
        buatTokoRequest.addMultipartParam("slogan", "text/plain",sloganToko.getText().toString());
        buatTokoRequest.addMultipartParam("catatan_toko","text/plain", catatanToko.getText().toString());
        buatTokoRequest.addMultipartParam("alamat","text/plain", alamatToko.getText().toString());
        buatTokoRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                0,  // maxNumRetries = 0 means no retry
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(buatTokoRequest);
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
