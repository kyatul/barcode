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

public class QRProfile extends Activity implements OnClickListener {

	Button btCreate;
	TextView tvbackQR;
	Typeface tf;
	EditText etFName,etLName,etTelephone,etEmail,etAddress1,etAddress2,etAddress3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.qr_profile);

		tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");

		tvbackQR = (TextView) findViewById(R.id.tvbackQRProfile);
		tvbackQR.setTypeface(tf);
		tvbackQR.setOnClickListener(this);

		btCreate = (Button) findViewById(R.id.btCreateProfile);
		btCreate.setOnClickListener(this);
		btCreate.setTypeface(tf);

		etFName = (EditText) findViewById(R.id.etFName);
		etLName = (EditText) findViewById(R.id.etLName);
		etTelephone = (EditText) findViewById(R.id.etTelephone);
		etEmail = (EditText) findViewById(R.id.etEmail);
		etAddress1 = (EditText) findViewById(R.id.etAddress1);
		etAddress2 = (EditText) findViewById(R.id.etAddress2);
		etAddress3 = (EditText) findViewById(R.id.etAddress3);
		
		etFName.setTypeface(tf);
		etLName.setTypeface(tf);
		etTelephone.setTypeface(tf);
		etEmail.setTypeface(tf);
		etAddress1.setTypeface(tf);
		etAddress2.setTypeface(tf);
		etAddress3.setTypeface(tf);
		

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvbackQRProfile:
			finish();
			break;
		case R.id.btCreateProfile:
			String text = etFName.getText().toString();
			if (text.length() == 0) {
				Toast.makeText(getApplicationContext(),"Please enter first name.", Toast.LENGTH_SHORT).show();
			} else {
				text="Name: "+text+
						" "+etLName.getText().toString()
						+" \nTel: "+etTelephone.getText().toString()
						 +" \nEmail: "+etEmail.getText().toString()
						  +" \nAddress: "+etAddress1.getText().toString()
						   +" \n          "+etAddress2.getText().toString()
						    +" \n          "+etAddress3.getText().toString();
				
				
				
				Intent i = new Intent(getApplicationContext(), QR.class);
				i.putExtra("data", text);
				i.putExtra("name","my_profile_");
				startActivity(i);

				finish();
			}
		}
	}

}
