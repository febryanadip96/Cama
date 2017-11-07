package com.example.biyan.ubama;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    @BindView(R.id.homePager)
    ViewPager homePager;
    Unbinder unbinder;
    TabLayout tabs;
    HomePagerAdapter adapter;

    public static HomeFragment newInstance() {
        // Required empty public constructor
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        adapter = new HomePagerAdapter(getChildFragmentManager());

        homePager = (ViewPager) rootView.findViewById(R.id.homePager);
        homePager.setAdapter(adapter);
        homePager.setOffscreenPageLimit(adapter.getCount());

        tabs = (TabLayout) getActivity().findViewById(R.id.tabs);
        tabs.setupWithViewPager(homePager);
        tabs.setVisibility(View.VISIBLE);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
