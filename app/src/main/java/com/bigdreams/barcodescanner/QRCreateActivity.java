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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class QRCreateActivity extends Activity implements OnClickListener {

	TextView tvbackQR;
	Typeface tf;
	String[] listTitles;
	TypedArray listIcons;
	ArrayList<ListQRItem> listQRItems;
	ListQRAdapter adapter;
	LinearLayout llQRLocation, llQRPhone, llQRFreeText, llQRProfile;
	
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

		llQRPhone = (LinearLayout) findViewById(R.id.llQRPhone);
		llQRLocation = (LinearLayout) findViewById(R.id.llQRLocation);
		llQRFreeText = (LinearLayout) findViewById(R.id.llQRFreeText);
		llQRProfile = (LinearLayout) findViewById(R.id.llQRProfile);


		tvbackQR = (TextView) findViewById(R.id.tvbackQR);
		tvbackQR.setTypeface(tf);

		tvbackQR.setOnClickListener(this);
		llQRProfile.setOnClickListener(this);
		llQRLocation.setOnClickListener(this);
		llQRFreeText.setOnClickListener(this);
		llQRPhone.setOnClickListener(this);


		pref = getSharedPreferences("My App", Context.MODE_PRIVATE);
		qrCount = pref.getInt(QR_COUNT_int, 0);
		pref.edit().putInt(QR_COUNT_int, (++qrCount)).commit();


        AdView adView = (AdView)findViewById(R.id.adViewQRC);
        AdRequest adRequest = (new AdRequest.Builder()).build();
        adView.loadAd(adRequest);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.llQRFreeText:
				startActivity(new Intent(getApplicationContext(), QRFreeText.class));
				break;

			case R.id.llQRLocation:
				startActivity(new Intent(getApplicationContext(), QRLocation.class));
				break;

			case R.id.llQRPhone:
				startActivity(new Intent(getApplicationContext(), QRPhone.class));
				break;

			case R.id.llQRProfile:
				startActivity(new Intent(getApplicationContext(), QRProfile.class));
				break;

			default:
				finish();
		}

	}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.animator.slide_in_left,R.animator.slide_out_right);
    }

}
