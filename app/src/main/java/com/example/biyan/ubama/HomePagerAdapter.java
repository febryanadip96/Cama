package com.example.biyan.ubama;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Biyan on 10/26/2017.
 */

public class HomePagerAdapter extends FragmentPagerAdapter {

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return BerandaFragment.newInstance();
        } else if(position ==1) {
            return FeedFragment.newInstance();
        }else {
            return FavoritFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Beranda";
        }  else if(position == 1) {
            return "Feed";
        } else {
            return "Favorit";
        }
    }
}
