package com.example.biyan.ubama;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class TokoTanyaJawabActivity extends AppCompatActivity {

    TanyaJawabPagerAdapter tanyaJawabPagerAdapter;

    ViewPager tanyaJawabViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toko_tanya_jawab);
        tanyaJawabPagerAdapter = new TanyaJawabPagerAdapter(getSupportFragmentManager());
        tanyaJawabViewPager = (ViewPager) findViewById(R.id.container);
        tanyaJawabViewPager.setAdapter(tanyaJawabPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(tanyaJawabViewPager);
    }

    public class TanyaJawabPagerAdapter extends FragmentPagerAdapter {

        public TanyaJawabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0){
                return TokoTanyaJawabBelumTerjawabFragment.newInstance();
            } else {
                return TokoTanyaJawabTerjawabFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position == 0){
                return "Belum Terjawab";
            }else{
                return "Terjawab";
            }
        }
    }
}
