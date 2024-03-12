package com.prueba.fragments.IdiomasAdapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.prueba.fragments.R;


public class LanguageItemAdapter extends BaseAdapter {
    private Context context;
    private String[] languages_item = {"ES", "EN"};
    private int[] languages_icons = {R.drawable.spain, R.drawable.eeuu};

    public LanguageItemAdapter(Context context) {
        this.context = context;
    }
    @Override
    public int getCount() {
        return languages_item.length;
    }

    @Override
    public Object getItem(int position) {
        return languages_item[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.language_items, null);
        ImageView iconL = convertView.findViewById(R.id.ic_language);
        TextView itemL = convertView.findViewById(R.id.name_language);
        iconL.setImageResource(languages_icons[position]);
        itemL.setText(languages_item[position]);

        return convertView;
    }
}
