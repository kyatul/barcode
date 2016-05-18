package com.bigdreams.barcodescanner;

import java.util.ArrayList;

/*
import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.InterstitialAd;
import com.google.ads.AdRequest.ErrorCode;
*/
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class QRCreateActivity extends Activity implements OnClickListener {

	ListView listQr;
	TextView tvbackQR;
	Typeface tf;
	String[] listTitles;
	TypedArray listIcons;
	ArrayList<ListQRItem> listQRItems;
	ListQRAdapter adapter;
	
	//private InterstitialAd interstitial;
	//private AdRequest adRequest;
	
	static final String QR_COUNT_int = "qr_count";
	
	SharedPreferences pref;
	int qrCount;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.qr_create);

		tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");

		tvbackQR = (TextView) findViewById(R.id.tvbackQR);
		tvbackQR.setTypeface(tf);
		tvbackQR.setOnClickListener(this);
		
		listQr = (ListView) findViewById(R.id.list_qr);
		
		listTitles=getResources().getStringArray(R.array.list_qr_item);
		listIcons=getResources().obtainTypedArray(R.array.list_qr_icons);
		
		listQRItems=new ArrayList<ListQRItem>();
		
		listQRItems.add(new ListQRItem(listTitles[0], listIcons.getResourceId(0, -1)));
		listQRItems.add(new ListQRItem(listTitles[1], listIcons.getResourceId(1, -1)));
		listQRItems.add(new ListQRItem(listTitles[2], listIcons.getResourceId(2, -1)));
		listQRItems.add(new ListQRItem(listTitles[3], listIcons.getResourceId(3, -1)));
		listQRItems.add(new ListQRItem(listTitles[4], listIcons.getResourceId(4, -1)));

		// Recycle the typed array
		listIcons.recycle();
		
		adapter = new ListQRAdapter(getApplicationContext(),listQRItems);
		listQr.setAdapter(adapter);

		listQr.setOnItemClickListener(new ListQRItemClickListener());
		
		pref = getSharedPreferences("My App", Context.MODE_PRIVATE);
		qrCount = pref.getInt(QR_COUNT_int, 0);
		pref.edit().putInt(QR_COUNT_int, (++qrCount)).commit();


        AdView adView = (AdView)findViewById(R.id.adViewQRC);
        AdRequest adRequest = (new AdRequest.Builder()).build();
        adView.loadAd(adRequest);

	}

	@Override
	public void onClick(View v) {
		finish();
	}


	private class ListQRItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}
	private void selectItem(int position) {
		if (position == 0) {
			startActivity(new Intent(getApplicationContext(), QRProfile.class));
		}
		
		
		if (position == 1) {
			startActivity(new Intent(getApplicationContext(), QRFreeText.class));
		}
		
		if (position == 2) {
			startActivity(new Intent(getApplicationContext(), QRPhone.class));
		}
		
		if (position == 3) {
			startActivity(new Intent(getApplicationContext(), QRBookmark.class));
		}
		if (position == 4) {
			startActivity(new Intent(getApplicationContext(), QRLocation.class));
		}
	}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.animator.slide_in_left,R.animator.slide_out_right);
    }

}
