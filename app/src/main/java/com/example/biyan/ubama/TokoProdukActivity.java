package com.example.biyan.ubama;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class TokoProdukActivity extends AppCompatActivity {

    ProdukPagerAdapter produkPagerAdapter;
    ViewPager viewPagerProduk;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toko_produk);
        produkPagerAdapter = new ProdukPagerAdapter(getSupportFragmentManager());
        viewPagerProduk = (ViewPager) findViewById(R.id.container);
        viewPagerProduk.setAdapter(produkPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPagerProduk);

    }

    public class ProdukPagerAdapter extends FragmentPagerAdapter {

        public ProdukPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return TokoProdukBarangFragment.newInstance();
            } else {
                return TokoProdukJasaFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "Barang";
            } else {
                return "Jasa";
            }
        }
    }
}
