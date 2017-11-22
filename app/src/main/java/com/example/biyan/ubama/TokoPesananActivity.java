package com.example.biyan.ubama;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class TokoPesananActivity extends AppCompatActivity {


    PesananPagerAdapter pesananPagerAdapter;

    ViewPager pesananViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toko_pesanan);
        pesananPagerAdapter = new PesananPagerAdapter(getSupportFragmentManager());
        pesananViewPager = (ViewPager) findViewById(R.id.container);
        pesananViewPager.setAdapter(pesananPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pesananViewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }


    public class PesananPagerAdapter extends FragmentPagerAdapter {

        public PesananPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return TokoPesananMenungguFragment.newInstance();
            } else if(position == 1){
                return TokoPesananProsesFragment.newInstance();
            } else if(position == 2){
                return TokoPesananSelesaiFragment.newInstance();
            } else {
                return TokoPesananDitolakFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "Menunggu";
            } else if(position == 1){
                return "Proses";
            } else if(position == 2){
                return "Selesai";
            } else {
                return "Ditolak";
            }
        }
    }
}
