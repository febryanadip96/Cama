package com.example.biyan.ubama;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class TokoPesananProsesFragment extends Fragment {


    public static TokoPesananProsesFragment newInstance() {
        TokoPesananProsesFragment tokoPesananProsesFragment = new TokoPesananProsesFragment();
        return tokoPesananProsesFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_toko_pesanan_proses, container, false);
    }

}
