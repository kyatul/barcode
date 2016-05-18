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

public class ListBookmarkAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<ListBookmarkItem> listBookmarkItems;
	Typeface tf;

	public ListBookmarkAdapter(Context context,
			ArrayList<ListBookmarkItem> listBookmarkItems) {
		this.context = context;
		this.listBookmarkItems = listBookmarkItems;
		tf = Typeface.createFromAsset(context.getAssets(),
				"fonts/Roboto-Light.ttf");
	}

	@Override
	public int getCount() {
		return listBookmarkItems.size();
	}

	@Override
	public Object getItem(int position) {
		return listBookmarkItems.get(position);
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
			convertView = mInflater.inflate(R.layout.list_bookmark_item, null);
		}

		TextView txtTitle = (TextView) convertView.findViewById(R.id.title);

		txtTitle.setText(listBookmarkItems.get(position).getTitle());
		txtTitle.setTypeface(tf);

		TextView highDetail = (TextView) convertView.findViewById(R.id.highDetail);
		highDetail.setText(listBookmarkItems.get(position).getHighDetail());
		highDetail.setTypeface(tf);
		return convertView;
	}

}