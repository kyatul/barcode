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

public class QRBookmark extends Activity implements OnClickListener {

	Button btCreate,btPickBookmark;
	TextView tvbackQR;
	Typeface tf;
	EditText etBookmark;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.qr_bookmarks);

		tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");

		tvbackQR = (TextView) findViewById(R.id.tvbackQRBookmark);
		tvbackQR.setTypeface(tf);
		tvbackQR.setOnClickListener(this);

		btCreate = (Button) findViewById(R.id.btCreateBookmark);
		btCreate.setOnClickListener(this);
		btCreate.setTypeface(tf);
		

		btPickBookmark = (Button) findViewById(R.id.btPickBookmark);
		btPickBookmark.setOnClickListener(this);
		btPickBookmark.setTypeface(tf);

		etBookmark = (EditText) findViewById(R.id.etBookmark);
		etBookmark.setTypeface(tf);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvbackQRBookmark:
			finish();
			break;
		case R.id.btPickBookmark:
			startActivity(new Intent(getApplicationContext(), QRPickBookmark.class));
			finish();
			break;
		case R.id.btCreateBookmark:
			String text = etBookmark.getText().toString();
			if (text.length() == 0) {
				Toast.makeText(getApplicationContext(),"Please enter some bookmark url.", Toast.LENGTH_SHORT).show();
			} else {
				Intent i = new Intent(getApplicationContext(), QR.class);
				i.putExtra("data", text);
				i.putExtra("name", "bookmark_");
				startActivity(i);

				finish();
			}
		}
	}

}
