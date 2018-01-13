package com.example.biyan.ubama;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biyan.ubama.beranda.BerandaPagerAdapter;
import com.example.biyan.ubama.keranjang.KeranjangActivity;
import com.example.biyan.ubama.pesanan.PesananActivity;
import com.example.biyan.ubama.toko.TokoCreateActivity;
import com.example.biyan.ubama.toko.TokoUserActivity;
import com.example.biyan.ubama.user.ProfileUserActivity;
import com.example.biyan.ubama.user.TanyaJawabUserActivity;
import com.example.biyan.ubama.welcome.WelcomeActivity;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView nama;
    TextView email;
    CircleImageView imageProfile;
    TabLayout tabs;
    NavigationView navigationView;
    ViewPager pagerBeranda;
    BerandaPagerAdapter adapterBeranda;
    RequestQueue queue;

    MenuItem keranjang;

    final static int RESULT_PENGATURAN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        pagerBeranda = (ViewPager) findViewById(R.id.pager_beranda);
        adapterBeranda = new BerandaPagerAdapter(getSupportFragmentManager());
        pagerBeranda.setAdapter(adapterBeranda);
        pagerBeranda.setOffscreenPageLimit(3);
        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(pagerBeranda);

        View headerView = navigationView.getHeaderView(0);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileUserActivity.class);
                startActivityForResult(intent, RESULT_PENGATURAN);
            }
        });
        imageProfile = (CircleImageView) headerView.findViewById(R.id.imageProfile);
        nama = (TextView) headerView.findViewById(R.id.nama);
        email = (TextView) headerView.findViewById(R.id.email);
        queue = Volley.newRequestQueue(this);
        isiHeaderUser();
        cekKeranjang();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle res;
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RESULT_PENGATURAN:
                    isiHeaderUser();
                    break;
            }
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        final SharedPreferences.Editor ed = getSharedPreferences("pager",
                android.content.Context.MODE_PRIVATE).edit();
        ed.putInt("currentPage", pagerBeranda.getCurrentItem());
        ed.commit();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        cekKeranjang();
        final SharedPreferences sp = getSharedPreferences("pager",
                android.content.Context.MODE_PRIVATE);
        pagerBeranda.setCurrentItem(sp.getInt("currentPage", 0));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        keranjang = menu.findItem(R.id.keranjang);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.requestFocus();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.keranjang) {
            Intent keranjang = new Intent(this,KeranjangActivity.class);
            startActivity(keranjang);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            //tidak ada
        } else if (id == R.id.nav_tanya_jawab) {
            Intent intent = new Intent(this,TanyaJawabUserActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_keranjang) {
            Intent intent = new Intent(this,KeranjangActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_pesanan) {
            Intent intent = new Intent(this, PesananActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_toko) {
            cekToko();
        } else if (id == R.id.nav_logout) {
            askLogout();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    public void isiHeaderUser() {
        String url = UrlUbama.USER;
        JsonObjectRequest requst = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(!(response.isNull("name") || response.isNull("email"))){
                        Picasso.with(getApplicationContext()).load(UrlUbama.URL_IMAGE+response.getJSONObject("pengguna").getString("url_profile")).memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).into(imageProfile);
                        nama.setText(response.getString("name"));
                        email.setText(response.getString("email"));
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),response.getString("message"), Toast.LENGTH_SHORT).show();
                        Intent welcomeIntent = new Intent(MainActivity.this, WelcomeActivity.class);
                        startActivity(welcomeIntent);
                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Volley ", error.toString());
                if (error instanceof AuthFailureError) {
                    Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
                    Intent welcomeIntent = new Intent(MainActivity.this, WelcomeActivity.class);
                    startActivity(welcomeIntent);
                    finish();
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", UserToken.getToken(getApplicationContext()));
                params.put("Accept", "application/json");
                return params;
            }
        };
        requst.setShouldCache(false);
        queue.add(requst);
    }

    public void askLogout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Anda yakin ingin keluar?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        logout();
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

    public void logout(){
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.setMessage("Mohon Menunggu");
        loading.setIndeterminate(true);
        loading.show();
        String url = UrlUbama.LOGOUT;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.dismiss();
                try {
                    Toast.makeText(getApplicationContext(),response.getString("message"), Toast.LENGTH_SHORT).show();
                    UserToken.setToken(getApplicationContext(),"");
                    Intent welcomeIntent = new Intent(MainActivity.this, WelcomeActivity.class);
                    startActivity(welcomeIntent);
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Log.e("Error Volley ", error.toString());
                Intent welcomeIntent = new Intent(MainActivity.this, WelcomeActivity.class);
                startActivity(welcomeIntent);
                finish();
            }
        }){
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

    public void cekToko(){
        String url = UrlUbama.USER_CEK_TOKO;
        JsonObjectRequest requst = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("toko")) {
                        Intent toko = new Intent(MainActivity.this, TokoUserActivity.class);
                        startActivity(toko);
                    } else {
                        Intent buatToko = new Intent(MainActivity.this, TokoCreateActivity.class);
                        startActivity(buatToko);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Volley", error.toString());
                return;
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
        requst.setShouldCache(false);
        queue.add(requst);
    }

    public void cekKeranjang(){
        String url = UrlUbama.USER_CEK_KERANJANG;
        StringRequest requst = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int jumlah = Integer.parseInt(response.toString());
                if(jumlah>0){
                    keranjang.setIcon(R.drawable.ic_keranjang_isi);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
        requst.setShouldCache(false);
        queue.add(requst);
    }
}
