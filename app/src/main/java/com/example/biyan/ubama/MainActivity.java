package com.example.biyan.ubama;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView txtNama;
    TextView txtEmail;
    RequestQueue queue;
    ViewPager mainPager;
    TabLayout tabs;
    MainPagerAdapter adapter;

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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        adapter=new MainPagerAdapter(getSupportFragmentManager());

        mainPager =(ViewPager) findViewById(R.id.mainPager);
        mainPager.setAdapter(adapter);
        mainPager.setOffscreenPageLimit(adapter.getCount());

        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(mainPager);

        View headerView = navigationView.getHeaderView(0);

        txtNama= (TextView) headerView.findViewById(R.id.txtNama);
        txtEmail = (TextView) headerView.findViewById(R.id.txtEmail);
        queue = Volley.newRequestQueue(this);
        IsiHeaderUser();

    }

    private void IsiHeaderUser() {
        if(UserToken.getEmail(getApplicationContext()).equals("") || UserToken.getNama(getApplicationContext()).equals("")){
            //get data user
            String url = UrlUbama.User;
            JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        //Toast.makeText(getApplicationContext(),response.getString("name")+" "+response.getString("email"), Toast.LENGTH_SHORT).show();
                        if(!(response.isNull("name") || response.isNull("email"))){
                            UserToken.setNama(getApplicationContext(),response.getString("name"));
                            UserToken.setEmail(getApplicationContext(),response.getString("email"));
                            txtNama.setText(UserToken.getNama(getApplicationContext()).toString());
                            txtEmail.setText(UserToken.getEmail(getApplicationContext()).toString());
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Gagal memperoleh data pengguna", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", UserToken.getToken(getApplicationContext()));
                    return params;
                }
            };
            queue.add(loginRequest);
        }
        else {
            txtNama.setText(UserToken.getNama(getApplicationContext()).toString());
            txtEmail.setText(UserToken.getEmail(getApplicationContext()).toString());
        }
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_beranda) {
            // Handle the camera action
        } else if (id == R.id.nav_favorit) {

        } else if (id == R.id.nav_notifikasi) {

        } else if (id == R.id.nav_keranjang) {

        } else if (id == R.id.nav_pesanan) {

        } else if (id == R.id.nav_toko) {

        } else if (id == R.id.nav_logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Anda yakin ingin keluar?")
                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            UserToken.setToken(getApplicationContext(),"");
                            if(UserToken.getToken(getApplicationContext()).equals("")) {
                                Intent welcomeIntent = new Intent(MainActivity.this, WelcomeActivity.class);
                                startActivity(welcomeIntent);
                                finish();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Logout gagal", Toast.LENGTH_SHORT).show();
                            }
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
