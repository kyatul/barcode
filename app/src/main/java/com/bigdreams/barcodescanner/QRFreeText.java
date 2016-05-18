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

public class QRFreeText extends Activity implements OnClickListener {

	Button btCreate;
	TextView tvbackQR;
	Typeface tf;
	EditText etFreeText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.qr_free_text);

		tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");

		tvbackQR = (TextView) findViewById(R.id.tvbackQRFreeText);
		tvbackQR.setTypeface(tf);
		tvbackQR.setOnClickListener(this);

		btCreate = (Button) findViewById(R.id.btCreateFreeText);
		btCreate.setOnClickListener(this);
		btCreate.setTypeface(tf);

		etFreeText = (EditText) findViewById(R.id.etFreeText);
		etFreeText.setTypeface(tf);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvbackQRFreeText:
			finish();
			break;
		case R.id.btCreateFreeText:
			String text = etFreeText.getText().toString();
			if (text.length() == 0) {
				Toast.makeText(getApplicationContext(),"Please enter some text.", Toast.LENGTH_SHORT).show();
			} else {
				Intent i = new Intent(getApplicationContext(), QR.class);
				i.putExtra("data", text);
				i.putExtra("name", "free_text_");
				startActivity(i);

				finish();
			}
		}
	}

}
