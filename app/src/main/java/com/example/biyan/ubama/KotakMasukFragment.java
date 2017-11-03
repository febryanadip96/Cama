package com.example.biyan.ubama;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class KotakMasukFragment extends Fragment {

    public static KotakMasukFragment newInstace() {
        KotakMasukFragment kotakMasukFragment = new KotakMasukFragment();
        return kotakMasukFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_kotak_masuk, container, false);
        return rootView;
    }

}
