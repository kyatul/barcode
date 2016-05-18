package com.bigdreams.barcodescanner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class QRLocation extends Activity implements OnClickListener {

	Button btCreate,btCurrentLocation;
	TextView tvbackQR;
	Typeface tf;
	EditText etLAdd,etLongitude,etLatitude;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.qr_location);

		tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");

		tvbackQR = (TextView) findViewById(R.id.tvbackQRLocation);
		tvbackQR.setTypeface(tf);
		tvbackQR.setOnClickListener(this);

		btCreate = (Button) findViewById(R.id.btCreateLocation);
		btCreate.setOnClickListener(this);
		btCreate.setTypeface(tf);
		

		btCurrentLocation = (Button) findViewById(R.id.btCurrentLocation);
		btCurrentLocation.setOnClickListener(this);
		btCurrentLocation.setTypeface(tf);

		etLAdd = (EditText) findViewById(R.id.etLAdd);
		etLAdd.setTypeface(tf);

		etLongitude = (EditText) findViewById(R.id.etLongitude);
		etLongitude.setTypeface(tf);
		
		etLatitude = (EditText) findViewById(R.id.etLatitude);
		etLatitude.setTypeface(tf);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvbackQRLocation:
			finish();
			break;
		case R.id.btCurrentLocation:
			 GPSTracker gps = new GPSTracker(this);
			 if(gps.canGetLocation()){
                  
                 double latitude = gps.getLatitude();
                 double longitude = gps.getLongitude();
             
                 etLongitude.setText(""+longitude);
                 etLatitude.setText(""+latitude);
			 }else{
                 gps.showSettingsAlert();
             }
			break;
		case R.id.btCreateLocation:
			String add = etLAdd.getText().toString();
			String longitude = etLongitude.getText().toString();			
			String latitude = etLatitude.getText().toString();
			
			String data="Address    :"+add
					+"\nLongitude :"+longitude
					+"\nLatitude  :"+latitude;
			
			if (add.length() == 0) {
				Toast.makeText(getApplicationContext(),"Please enter some address.", Toast.LENGTH_SHORT).show();
			} else {
				Intent i = new Intent(getApplicationContext(), QR.class);
				i.putExtra("data", data);
				i.putExtra("name", "location_");
				startActivity(i);

				finish();
			}
		}
	}

}
