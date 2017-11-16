package com.example.biyan.ubama.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.biyan.ubama.HasilSubkategoriActivity;
import com.example.biyan.ubama.R;
import com.example.biyan.ubama.models.Subkategori;

import java.util.List;

/**
 * Created by Biyan on 11/11/2017.
 */
public class SubkategoriAdapter extends BaseAdapter {

    private List<Subkategori> subkategoriList;
    private TextView namaSubkategori;
    private Context context;

    public SubkategoriAdapter(Context context, List<Subkategori> subkategoriList) {
        this.context = context;
        this.subkategoriList = subkategoriList;
    }

    @Override
    public int getCount() {
        return subkategoriList.size();
    }

    @Override
    public Subkategori getItem(int position) {
        return subkategoriList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subkategori, parent, false);
        }
        namaSubkategori = (TextView) convertView.findViewById(R.id.nama_subkategori);
        namaSubkategori.setText(getItem(position).nama);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent subkategori = new Intent(context, HasilSubkategoriActivity.class);
                subkategori.putExtra("idSubkategori", getItem(position).id);
                subkategori.putExtra("namaSubkategori", getItem(position).nama);
                context.startActivity(subkategori);
            }
        });
        return convertView;
    }
}
