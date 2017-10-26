package com.example.biyan.ubama;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritFragment extends Fragment {


    public static FavoritFragment newInstance() {
        // Required empty public constructor
        FavoritFragment favoritFragment = new FavoritFragment();
        return favoritFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_favorit, container, false);
        return rootView;
    }

}
