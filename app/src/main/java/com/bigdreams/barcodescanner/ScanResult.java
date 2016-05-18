package com.bigdreams.barcodescanner;

import java.io.IOException;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class ScanResult extends Activity implements OnClickListener {
	TextView tvType, tvBarcode, tvDate, tvName, tvCategory,tvOrigin,tvHeadOrigin;
	Button searchGoogle, share;
	String type, barcode,name="No match found",category="No match found";
	Typeface tf, tfThin;
	ProgressDialog dialog;
	Toast toast;
	HttpClient client;
	final static String URL = "https://api.scandit.com/v2/products/";
	final static String Scandit_API_KEY_1 = "?key=";
	final static String Scandit_API_KEY_2="?key=";
	JSONObject jsonOb;
	StringBuilder url;
	HistoryDatabase hData;

	String originCountry="No Match Found";

    ImageView ivShare,ivIcon2;
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.scan_result);

		hData = new HistoryDatabase(this);
		tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");

        ivShare = (ImageView) findViewById(R.id.ivShare);
        ivIcon2 = (ImageView) findViewById(R.id.ivIcon2);

		tvType = (TextView) findViewById(R.id.tvType);
		tvBarcode = (TextView) findViewById(R.id.tvBarcode);
		tvDate = (TextView) findViewById(R.id.tvDate);
		tvName = (TextView) findViewById(R.id.tvName);
		tvCategory = (TextView) findViewById(R.id.tvCategory);
		tvOrigin = (TextView) findViewById(R.id.tvOrigin);
		tvHeadOrigin = (TextView) findViewById(R.id.tvHeadOrigin);


        ivShare.setOnClickListener(this);
        ivIcon2.setOnClickListener(this);

		tvOrigin.setTypeface(tf);
		tvType.setTypeface(tf);
		tvBarcode.setTypeface(tf);
		tvDate.setTypeface(tf);
		tvName.setTypeface(tf);
		tvCategory.setTypeface(tf);


		searchGoogle = (Button) findViewById(R.id.searchGoogle);
		searchGoogle.setOnClickListener(this);
		searchGoogle.setTypeface(tf);

		share = (Button) findViewById(R.id.share);
		share.setOnClickListener(this);
		share.setTypeface(tf);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			type = extras.getString("type");
			barcode = extras.getString("barcode");
		}

        if(barcode.substring(0,4).equals("http")){
            tvBarcode.setOnClickListener(this);
            tvBarcode.setTextColor(0xff40bfea);
            tvBarcode.setPaintFlags(tvBarcode.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

            searchGoogle.setText("Open Browser");
        }


		tvType.setText(type);
		tvBarcode.setText(barcode);
		tvDate.setText(ScanDate.getDate());
		
		if(type.equals("EAN13")){
			originCountry=ScanDate.getCountry(barcode.substring(0,3));
			tvOrigin.setText(originCountry);
			
			if(barcode.substring(0, 2).equals("97")){   //97 STANDS FOR BOOKS
				tvHeadOrigin.setText("Origin/Category");

                category="Books";
                tvCategory.setText(category);
			}
		}
		else{
			tvOrigin.setVisibility(View.GONE);
			tvHeadOrigin.setVisibility(View.GONE);
		}
		
		toast = Toast.makeText(this,"Unable to connect, Please Try again later", Toast.LENGTH_SHORT);

		client = new DefaultHttpClient();
		new ReadProduct().execute();
		dialog = ProgressDialog.show(this, "",
				"Fetching product Details. Please wait...", true);

        AdView adView = (AdView)findViewById(R.id.ads);
        AdRequest adRequest = (new AdRequest.Builder()).build();
        adView.loadAd(adRequest);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

        case R.id.tvBarcode:
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(tvBarcode.getText().toString())));
            break;

        case R.id.ivIcon2:
        case R.id.searchGoogle:

            if(barcode.substring(0, 2).equals("97")){   //97 STANDS FOR BOOKS
                Intent dialog = new Intent(this,DialogSearch.class);
                dialog.putExtra("barcode",barcode);
                startActivity(dialog);
            }
            else{
                String url="";
                if(barcode.substring(0,4).equals("http")){
                    url=barcode;
                }
                else{
                    url="http://www.google.com/search?q=" + barcode;
                }

                Intent browserTwitterIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
                startActivity(Intent.createChooser(browserTwitterIntent,"Search Barcode on Web"));
            }
			break;

        case R.id.ivShare:
        case R.id.share:
            /*
            SENDING AS TEXT
            Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                    "Format: "
                            + type
                            + "\nDate: "
                            + ScanDate.getDate()
                            + "\nBarcode:"
                            + barcode
                            + "\n \nhttp://goo.gl/ImKFE7");

            emailIntent.setType("plain/text`");
            startActivity(Intent.createChooser(emailIntent,"Share Barcode Information"));
            */

            //SENDING AS QR
            Intent i = new Intent(getApplicationContext(), QR.class);
            i.putExtra("data", barcode);
            i.putExtra("name", "share_");
            startActivity(i);

            break;
		
		}

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

	public JSONObject fetchProductDatabase() throws ClientProtocolException,
			IOException, JSONException {

		url = new StringBuilder(URL);
		url.append(barcode);
		
		if((new Random().nextInt(50))>25){
			url.append(Scandit_API_KEY_1);	
		}
		else{
			url.append(Scandit_API_KEY_2);
		}
		

		HttpGet get = new HttpGet(url.toString());
        
		HttpResponse r = client.execute(get);
		
		int status = r.getStatusLine().getStatusCode();

		if (status == 200) {
			HttpEntity e = r.getEntity();
			String data = EntityUtils.toString(e);
			
			JSONObject jObj = new JSONObject(data);
			jObj=jObj.getJSONObject("basic");
			return jObj;
			
		} else {
			//toast.show();
			dialog.dismiss();
			return null;
		}

	}

	public class ReadProduct extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... param) {
			try {
				jsonOb = fetchProductDatabase();
				return null;
			} catch (Exception e) {
                //toast.show();
			} finally {
				dialog.dismiss();
			}
			return null;
		}

	
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			try {
				if(jsonOb.has("name")){
					name=jsonOb.getString("name");
					tvName.setText(name);
				}
				if(jsonOb.has("category")){
					category=jsonOb.getString("category");
					tvCategory.setText(category);
				}
				
									

			} catch (Exception e) {
				//toast.show();
			}
			finally{
				hData.open();
				hData.newEntry(type, barcode,originCountry,name,category);
				hData.close();	
			}
		}

	}
	
	
}
