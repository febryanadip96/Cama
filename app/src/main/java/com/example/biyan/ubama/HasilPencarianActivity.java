package com.example.biyan.ubama;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.biyan.ubama.keranjang.KeranjangActivity;
import com.example.biyan.ubama.models.BarangJasa;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HasilPencarianActivity extends AppCompatActivity {


    @BindView(R.id.sort)
    LinearLayout sort;
    @BindView(R.id.filter)
    LinearLayout filter;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.empty)
    TextView empty;

    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    List<BarangJasa> barangJasaList;
    RequestQueue queue;

    int idSort = 0;
    int idFilter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_pencarian);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        layoutManager = new GridLayoutManager(this, 2);
        recycler.setLayoutManager(layoutManager);
        queue = Volley.newRequestQueue(this);
        handleIntent(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.requestFocus();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            this.finish();
        }
        else if (id == R.id.keranjang) {
            Intent keranjang = new Intent(this, KeranjangActivity.class);
            startActivity(keranjang);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            HasilPencarianActivity.this.setTitle(query);
            Cari(query);
        }
    }

    public void Cari(String query) {
        String url = UrlUbama.CARI_BARANG_JASA + query;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                barangJasaList = new Gson().fromJson(response.toString(), new TypeToken<List<BarangJasa>>() {
                }.getType());
                adapter = new BarangJasaAdapter(barangJasaList);
                recycler.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Volley HasilPencarianActivity", error.toString());
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
        queue.add(request);
    }

    @OnClick(R.id.sort)
    public void onSortClicked() {
        final String[] sortArray = {"Paling Sesuai", "Terbaru", "Harga Tertinggi", "Harga Terendah"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Urutkan")
                .setItems(sortArray, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("Filter", sortArray[which]);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @OnClick(R.id.filter)
    public void onFilterClicked() {
        final String[] filterArray = {"Baru", "Bekas"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Filter")
                .setItems(filterArray, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("Filter",  filterArray[which]);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
