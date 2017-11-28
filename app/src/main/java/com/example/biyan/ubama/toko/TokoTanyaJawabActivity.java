package com.example.biyan.ubama.toko;

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

public class TokoTanyaJawabActivity extends AppCompatActivity {

    TanyaJawabPagerAdapter tanyaJawabPagerAdapter;

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
        setContentView(R.layout.activity_toko_tanya_jawab);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tanyaJawabPagerAdapter = new TanyaJawabPagerAdapter(getSupportFragmentManager());
        container.setAdapter(tanyaJawabPagerAdapter);
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

    public class TanyaJawabPagerAdapter extends FragmentPagerAdapter {

        public TanyaJawabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
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
            if (position == 0) {
                return "Belum Terjawab";
            } else {
                return "Terjawab";
            }
        }
    }
}
