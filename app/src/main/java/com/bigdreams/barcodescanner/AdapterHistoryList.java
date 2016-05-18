package com.bigdreams.barcodescanner;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bigdreams.barcodescanner.R;

import java.util.ArrayList;

public class AdapterHistoryList extends ArrayAdapter<String> {
    private final Activity context;
    private  ArrayList<String> date = null;
    private  ArrayList<String> desc=null;

    Typeface tf,tfLight;
    public AdapterHistoryList(Activity context,ArrayList<String> date,ArrayList<String> desc) {
        super(context, R.layout.custom_list_history, date);
        this.context = context;
        this.date = date;
        this.desc = desc;
        tf= Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf");
        tfLight= Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf");
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.custom_list_history, null, true);

        TextView txtDate = (TextView) rowView.findViewById(R.id.tvListHistoryDate);
        TextView txtDesc = (TextView) rowView.findViewById(R.id.tvListHistoryName);

        txtDate.setText(date.get(position));
        txtDate.setTypeface(tf);

        txtDesc.setText(desc.get(position));
        txtDesc.setTypeface(tf);

        return rowView;
    }
}
