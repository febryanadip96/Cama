package com.example.biyan.ubama.toko;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.view.MenuItem;

import com.example.biyan.ubama.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TokoPesananActivity extends AppCompatActivity {


    PesananPagerAdapter pesananPagerAdapter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.container)
    ViewPager container;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toko_pesanan);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        pesananPagerAdapter = new PesananPagerAdapter(getSupportFragmentManager());
        container.setAdapter(pesananPagerAdapter);
        tabs.setupWithViewPager(container);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        final SharedPreferences.Editor ed = getSharedPreferences("pager",
                Context.MODE_PRIVATE).edit();
        ed.putInt("currentPagePesanan", container.getCurrentItem());
        ed.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        final SharedPreferences sp = getSharedPreferences("pager",
                Context.MODE_PRIVATE);
        container.setCurrentItem(sp.getInt("currentPagePesanan", 0));
    }

    public class PesananPagerAdapter extends FragmentPagerAdapter {

        public PesananPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return TokoPesananTungguFragment.newInstance();
            } else if (position == 1) {
                return TokoPesananDiprosesFragment.newInstance();
            } else if (position == 2) {
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
            } else if (position == 1) {
                return "Proses";
            } else if (position == 2) {
                return "Selesai";
            } else {
                return "Ditolak";
            }
        }
    }
}
