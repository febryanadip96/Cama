package com.example.biyan.ubama;

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
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.biyan.ubama.adapters.BerandaPagerAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView nama;
    TextView email;
    ImageView imageProfile;
    TabLayout tabs;
    NavigationView navigationView;
    ViewPager pagerBeranda;
    BerandaPagerAdapter adapterBeranda;
    RequestQueue queue;

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
        imageProfile = (ImageView) headerView.findViewById(R.id.imageProfile);
        nama = (TextView) headerView.findViewById(R.id.nama);
        email = (TextView) headerView.findViewById(R.id.email);
        queue = Volley.newRequestQueue(this);
        IsiHeaderUser();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        final SharedPreferences.Editor ed = getSharedPreferences("name",
                android.content.Context.MODE_PRIVATE).edit();
        ed.putInt("currentPage", pagerBeranda.getCurrentItem());
        ed.commit();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        final SharedPreferences sp = getSharedPreferences("name",
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.keranjang) {
            Intent keranjang = new Intent(this,KeranjangActivity.class);
            startActivity(keranjang);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            //tidak ada
        } else if (id == R.id.nav_keranjang) {
            Intent keranjang = new Intent(this,KeranjangActivity.class);
            startActivity(keranjang);
        } else if (id == R.id.nav_pesanan) {
            Intent pesanan = new Intent(this, PesananActivity.class);
            startActivity(pesanan);
        } else if (id == R.id.nav_toko) {
            CekToko();
        } else if (id == R.id.nav_logout) {
            AskLogout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    public void IsiHeaderUser() {
        String url = UrlUbama.USER;
        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //Toast.makeText(getApplicationContext(),response.getString("name")+" "+response.getString("email"), Toast.LENGTH_SHORT).show();
                    if(!(response.isNull("name") || response.isNull("email"))){
                        Picasso.with(getApplicationContext()).load(UrlUbama.URL_IMAGE+response.getJSONObject("pengguna").getString("url_profile")).into(imageProfile);
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
                try {
                    if (error instanceof AuthFailureError) {
                        //error
                        finish();
                    }


                } catch (Exception e) {
                    Log.e("Error Volley ", e.toString());
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
        queue.add(loginRequest);
    }

    public void AskLogout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Anda yakin ingin keluar?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Logout();
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

    public void Logout(){
        String url = UrlUbama.LOGOUT;
        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
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
        queue.add(loginRequest);
    }

    public void CekToko(){
        String url = UrlUbama.USER_CEK_TOKO;
        Log.d("URL", url.toString());
        JsonObjectRequest cekTokoRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("toko")) {
                        Intent toko = new Intent(MainActivity.this, TokoUserActivity.class);
                        startActivity(toko);
                    } else {
                        Intent buatToko = new Intent(MainActivity.this, BuatTokoUserActivity.class);
                        startActivity(buatToko);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Volley Cek Toko", error.toString());
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
        cekTokoRequest.setShouldCache(false);
        queue.add(cekTokoRequest);


    }
}
