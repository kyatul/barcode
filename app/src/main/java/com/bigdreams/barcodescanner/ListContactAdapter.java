package com.bigdreams.barcodescanner;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListContactAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<ListContactItem> listContactItems;
	Typeface tf;

	public ListContactAdapter(Context context,
			ArrayList<ListContactItem> listContactItems) {
		this.context = context;
		this.listContactItems = listContactItems;
		tf = Typeface.createFromAsset(context.getAssets(),
				"fonts/Roboto-Light.ttf");
	}

	@Override
	public int getCount() {
		return listContactItems.size();
	}

	@Override
	public Object getItem(int position) {
		return listContactItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.list_contact_item, null);
		}

		TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
		
		txtTitle.setText(listContactItems.get(position).getTitle());
		txtTitle.setTypeface(tf);
		return convertView;
	}

}