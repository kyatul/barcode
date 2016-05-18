package com.bigdreams.barcodescanner;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

public class QR extends Activity implements OnClickListener {
	ImageView imageView;
	TextView qrText;
	Bitmap _bitmap;
    ImageView ivShare,ivSave;
	Button btSave, btShare;
	Typeface tf;
	String qrData,qrName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qr);

		tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
		imageView = (ImageView) findViewById(R.id.qrCode);

        ivShare = (ImageView) findViewById(R.id.ivShareFreeText);
        ivSave  = (ImageView) findViewById(R.id.ivSave);

		qrText = (TextView) findViewById(R.id.qrText);
		
		btSave = (Button) findViewById(R.id.btSaveFreeText);
		btShare = (Button) findViewById(R.id.btShareFreeText);

        ivShare.setOnClickListener(this);
        ivSave.setOnClickListener(this);

        btSave.setOnClickListener(this);
		btShare.setOnClickListener(this);
		
		btSave.setTypeface(tf);
		btShare.setTypeface(tf);
		
		qrText.setTypeface(tf);
				
		qrData = "";
		qrName="default";
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			qrData = extras.getString("data");
			qrName= extras.getString("name");
		}
		
		qrText.setText(qrData);
		int qrCodeDimention = 500;

		QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrData, null,
				Contents.Type.TEXT, BarcodeFormat.QR_CODE.toString(),
				qrCodeDimention);

		try {
			Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
			imageView.setImageBitmap(bitmap);
		} catch (WriterException e) {
			e.printStackTrace();
		}

        AdView adView = (AdView)findViewById(R.id.adViewQR);
        AdRequest adRequest = (new AdRequest.Builder()).build();
        adView.loadAd(adRequest);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

        case R.id.ivSave:
		case R.id.btSaveFreeText:
			saveQR(0);
			break;

        case R.id.ivShareFreeText:
        case R.id.btShareFreeText:
			saveQR(1);   //saveQR further calls mailTextFile()
			break;
		}
	}
	
	@Override
	public void onBackPressed(){
		finish();
	}
	
	public void saveQR(int status){  //status=0 when only save  AND status=1 when email
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {


			File sdCard = Environment.getExternalStorageDirectory();
			File barcodeDir = new File(sdCard.getAbsolutePath()+ File.separator+"QuickBarcodeScanner"+ File.separator);
			barcodeDir.mkdirs();
			
			String qrPath = qrName+new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date())+".jpg";
			File qrFile = new File(barcodeDir, qrPath);
			
			try {
				qrFile.createNewFile();

				_bitmap = ((BitmapDrawable) imageView.getDrawable())
						.getBitmap();
				FileOutputStream out = new FileOutputStream(qrFile);
				_bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
				out.flush();
				out.close();

				
				if(status==0){
					Toast.makeText(getApplicationContext(), "Saved",Toast.LENGTH_LONG).show();	
				}
				else{
					mailTextFile(qrFile);
				}
				
			
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "Oops!! Something went wrong",Toast.LENGTH_LONG).show();
			}

		}

	}
	
	public void mailTextFile(File file) {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("image/png");
		i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
		startActivity(Intent.createChooser(i, "Share QR Code"));

	}
	
	
}
