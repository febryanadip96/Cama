package com.example.biyan.ubama;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KotakMasukActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.pagerKontakMasuk)
    ViewPager pagerKontakMasuk;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;

    private KotakMasukPagerAdapter adapterKotakMasuk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kotak_masuk);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapterKotakMasuk = new KotakMasukPagerAdapter(getSupportFragmentManager());
        pagerKontakMasuk.setAdapter(adapterKotakMasuk);
        pagerKontakMasuk.setOffscreenPageLimit(0);
        tabs.setupWithViewPager(pagerKontakMasuk);
    }

    private class KotakMasukPagerAdapter extends FragmentPagerAdapter {

        public KotakMasukPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return ChatFragment.newInstance();
            } else {
                return FaqFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "Chat";
            } else {
                return "FAQ";
            }
        }
    }
}
