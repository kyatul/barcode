package com.bigdreams.barcodescanner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class SearchProduct extends Activity implements OnClickListener{
	String barcode;
	WebView webBrowser;
	Typeface tfThin;
	TextView tvHeadSearchProduct;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.search_product);

		tfThin = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
		tvHeadSearchProduct = (TextView) findViewById(R.id.tvHeadSearchProduct);
		tvHeadSearchProduct.setTypeface(tfThin);
		tvHeadSearchProduct.setOnClickListener(this);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			barcode = extras.getString("barcode");
		}

		webBrowser = (WebView) findViewById(R.id.webBrowser);
		webBrowser.setWebViewClient(new Callback());
		webBrowser.getSettings().setJavaScriptEnabled(true);
		webBrowser.loadUrl("http://www.google.com/search?q=" + barcode);

        AdView adView = (AdView)findViewById(R.id.adViewS);
        AdRequest adRequest = (new AdRequest.Builder()).build();
        adView.loadAd(adRequest);
	}

	private class Callback extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {

			Pattern p = Pattern.compile("http\\w*://\\w*.google.");
			Matcher m = p.matcher(url);
			if (m.find()) {
				return false;
			} else {
				view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
		        return true;
			}
		}


	}

	@Override
	public void onClick(View arg0) {
		startActivity(new Intent(this,MainOneFragment.class));
		finish();
	}

}
