package com.example.biyan.ubama;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class TokoPesananSelesaiFragment extends Fragment {


    public static TokoPesananSelesaiFragment newInstance() {
        TokoPesananSelesaiFragment tokoPesananSelesaiFragment = new TokoPesananSelesaiFragment();
        return tokoPesananSelesaiFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_toko_pesanan_selesai, container, false);
    }

}
