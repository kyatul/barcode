package com.bigdreams.barcodescanner;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Browser;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class QRPickBookmark extends Activity implements OnClickListener {

	TextView tvBack;
	ListView list;
	Typeface tf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.qr_pick_bookmark);

		tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");


		tvBack = (TextView) findViewById(R.id.tvbackQRPickBookmark);
		tvBack.setOnClickListener(this);
		tvBack.setTypeface(tf);

		list = (ListView) findViewById(R.id.list_qr_pick_bookmark);
	}

	@Override
	protected void onResume() {
		super.onResume();
		new DataTask().execute();
	}

	@Override
	public void onClick(View arg0) {
		finish();
	}

	class DataTask extends AsyncTask<String, String, ArrayList<ListBookmarkItem>> {

		@Override
		protected ArrayList<ListBookmarkItem> doInBackground(String... params) {
			ArrayList<ListBookmarkItem> bMark = new ArrayList<ListBookmarkItem>();
			bMark = getBookmarkNames();
			return bMark;
		}

		@Override
		protected void onPostExecute(ArrayList<ListBookmarkItem> bMark) {
			super.onPostExecute(bMark);
			
			if(bMark.size()>0){			
				list.setAdapter(new ListBookmarkAdapter(getApplicationContext(), bMark));
				list.setOnItemClickListener(new ListQRItemClickListener());
			}
			else{
				bMark.add(new ListBookmarkItem("Oops!! No Bookmark found.",""));
				list.setAdapter(new ListBookmarkAdapter(getApplicationContext(), bMark));
		    }
		    
		}

	}
	
	public ArrayList<ListBookmarkItem> getBookmarkNames(){
		ArrayList<ListBookmarkItem> temp=new ArrayList<ListBookmarkItem>();
		Cursor mCur;
		 	
			String[] proj = new String[] { Browser.BookmarkColumns.TITLE, Browser.BookmarkColumns.URL };
		    String sel = Browser.BookmarkColumns.BOOKMARK + " = 1"; // 0 = history, 1 = bookmark
		    mCur = this.managedQuery(Browser.BOOKMARKS_URI, proj, sel, null, null);
		    this.startManagingCursor(mCur);
		    mCur.moveToFirst();

		    String title ="";
		    String url ="";
		    String briefUrl="";
		    if (mCur.moveToFirst() && mCur.getCount() > 0) {
		        while (mCur.isAfterLast() == false) {

		            title = mCur.getString(mCur.getColumnIndex(Browser.BookmarkColumns.TITLE));
		            url = mCur.getString(mCur.getColumnIndex(Browser.BookmarkColumns.URL));
		            if(title.length()>15){
		            	title=title.substring(0,15)+"..";
		            }
		            
		            temp.add(new ListBookmarkItem(title,url));
		            mCur.moveToNext();
		        }
		    }
		    
		    return temp;
	}

	private class ListQRItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
			RelativeLayout r = (RelativeLayout)view;
			TextView t=(TextView) r.findViewById(R.id.title);
			String title=t.getText().toString();
			
			t=(TextView) r.findViewById(R.id.highDetail);
			String url=t.getText().toString();
			
			
			String data="Title :"+title+"\n"+"URL :"+url;
			Intent i = new Intent(QRPickBookmark.this, QR.class);
			i.putExtra("data", data);
			i.putExtra("name", "bookmark_");
			startActivity(i);
			
		}
	}



}