package com.bigdreams.barcodescanner;

import java.util.ArrayList;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class QRPickPhoneFinal extends Activity implements OnClickListener {

	TextView tvBack;
	ListView list;
	Typeface tf;
	// ArrayList<String> contactName;
	ProgressDialog dialog;
	String name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.qr_pick_phone);

		tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");

		tvBack = (TextView) findViewById(R.id.tvbackQRPickPhone);
		tvBack.setOnClickListener(this);
		tvBack.setTypeface(tf);

		list = (ListView) findViewById(R.id.list_qr_pick_phone);
		
		Bundle b=getIntent().getExtras();
		if(b!=null){
			name=b.getString("name");
		}
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

	class DataTask extends AsyncTask<String, String, ArrayList<ListContactItem>> {

		@Override
		protected ArrayList<ListContactItem> doInBackground(String... params) {
			ArrayList<ListContactItem> contactN = new ArrayList<ListContactItem>();
			contactN = (new ContactsWork(QRPickPhoneFinal.this)).getNumbers(name);
			return contactN;
		}

		@Override
		protected void onPostExecute(ArrayList<ListContactItem> contactN) {
			super.onPostExecute(contactN);

			list.setAdapter(new ListContactAdapter(getApplicationContext(), contactN));
			list.setOnItemClickListener(new ListQRItemClickListener());

		}

	}

	private class ListQRItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
			RelativeLayout r = (RelativeLayout)view;
			TextView t=(TextView) r.findViewById(R.id.title);
			String text=t.getText().toString();
			
			Intent i = new Intent(QRPickPhoneFinal.this, QR.class);
			i.putExtra("data", text);
			i.putExtra("name", "tel_");
			startActivity(i);

			finish();
		}
	}

}