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

public class QRPhone extends Activity implements OnClickListener {

	Button btCreate,btPickPhone;
	TextView tvbackQR;
	Typeface tf;
	EditText etPhone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.qr_phone);

		tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");

		tvbackQR = (TextView) findViewById(R.id.tvbackQRPhone);
		tvbackQR.setTypeface(tf);
		tvbackQR.setOnClickListener(this);

		btCreate = (Button) findViewById(R.id.btCreatePhone);
		btCreate.setOnClickListener(this);
		btCreate.setTypeface(tf);
		

		btPickPhone = (Button) findViewById(R.id.btPickContact);
		btPickPhone.setOnClickListener(this);
		btPickPhone.setTypeface(tf);

		etPhone = (EditText) findViewById(R.id.etPhone);
		etPhone.setTypeface(tf);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvbackQRPhone:
			finish();
			break;
		case R.id.btPickContact:
			startActivity(new Intent(getApplicationContext(), QRPickPhone.class));
			finish();
			break;
		case R.id.btCreatePhone:
			String text = etPhone.getText().toString();
			if (text.length() == 0) {
				Toast.makeText(getApplicationContext(),"Please enter some phone number.", Toast.LENGTH_SHORT).show();
			} else {
				Intent i = new Intent(getApplicationContext(), QR.class);
				i.putExtra("data", text);
				i.putExtra("name", "phone_");
				startActivity(i);

				finish();
			}
		}
	}

}
