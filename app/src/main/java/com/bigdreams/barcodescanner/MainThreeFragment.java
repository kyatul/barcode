package com.bigdreams.barcodescanner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainThreeFragment extends Activity implements OnClickListener{
    
	ImageView  fb, twitter;	
	TextView tFb, tMail, tMail1,tvHeadContact;
	Typeface tf;


	@Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  

        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_three);
        tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        
        tMail1 = (TextView) findViewById(R.id.tMail1);
		tMail = (TextView) findViewById(R.id.tMail);
		tvHeadContact = (TextView) findViewById(R.id.tvHeadContact);
		
		tFb = (TextView) findViewById(R.id.tFb);
		fb = (ImageView) findViewById(R.id.fb);
		twitter = (ImageView) findViewById(R.id.twitter);

		tMail1.setTypeface(tf);
		tMail.setTypeface(tf);
		tFb.setTypeface(tf);
		tvHeadContact.setTypeface(tf);
		
		tMail1.setTextColor(0xFF2f7af8);

		tvHeadContact.setOnClickListener(this);
		tMail1.setOnClickListener(this);
		fb.setOnClickListener(this);
		twitter.setOnClickListener(this);

        AdView adView = (AdView)findViewById(R.id.TapAdView1);
        AdRequest adRequest = (new AdRequest.Builder()).build();
        adView.loadAd(adRequest);

	}  

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tMail1:

			Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
					new String[] { "shout@bigdreamslab.com" });
			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
					"Review/Quick Barcode Scanner");

			emailIntent.setType("plain/text`");
			startActivity(emailIntent);
			break;
		case R.id.fb:
			Intent browserIntent = new Intent(Intent.ACTION_VIEW,
					Uri.parse("http://m.facebook.com/BIGDREAMSlabs"));
			startActivity(Intent.createChooser(browserIntent,"Let's roll"));
			break;
		case R.id.twitter:
			Intent browserTwitterIntent = new Intent(Intent.ACTION_VIEW,
					Uri.parse("https://twitter.com/BIGDREAMSlab"));
			startActivity(Intent.createChooser(browserTwitterIntent,"Let's roll"));
			break;

		case R.id.tvHeadContact:
			finish();
			break;
				}

	}


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.animator.slide_in_right,R.animator.slide_out_left);
    }
}
