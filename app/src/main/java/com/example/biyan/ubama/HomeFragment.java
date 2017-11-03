package com.example.biyan.ubama;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    ViewPager mainPager;
    TabLayout tabs;
    MainPagerAdapter adapter;

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
        adapter=new MainPagerAdapter(getChildFragmentManager());

        mainPager =(ViewPager) rootView.findViewById(R.id.mainPager);
        mainPager.setAdapter(adapter);
        mainPager.setOffscreenPageLimit(adapter.getCount());

        tabs = (TabLayout) getActivity().findViewById(R.id.tabs);
        tabs.setupWithViewPager(mainPager);
        return rootView;
    }

}
