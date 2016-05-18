package com.bigdreams.barcodescanner;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class HistoryItem extends Activity implements OnClickListener {
	TextView tvType, tvBarcode, tvDate, tvName, tvCategory,tvOrigin,tvHeadOrigin;
	Button searchGoogle,Hshare;
	String date, barcode,type;
	Typeface tf;

	HistoryDatabase hData;
    ImageView ivIcon3,ivHshare;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.history_item);

		hData=new HistoryDatabase(this);
		

		tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");

		tvType = (TextView) findViewById(R.id.HtvType);
		tvBarcode = (TextView) findViewById(R.id.HtvBarcode);
		tvDate = (TextView) findViewById(R.id.HtvDate);
		tvName = (TextView) findViewById(R.id.HtvName);
		tvCategory = (TextView) findViewById(R.id.HtvCategory);
		tvOrigin = (TextView) findViewById(R.id.HtvOrigin);
		tvHeadOrigin = (TextView) findViewById(R.id.HtvHeadOrigin);

        ivIcon3 = (ImageView) findViewById(R.id.ivIcon3);
        ivHshare = (ImageView) findViewById(R.id.HivShare);

        ivIcon3.setOnClickListener(this);
        ivHshare.setOnClickListener(this);

		tvOrigin.setTypeface(tf);
		tvType.setTypeface(tf);
		tvBarcode.setTypeface(tf);
		tvDate.setTypeface(tf);
		tvName.setTypeface(tf);
		tvCategory.setTypeface(tf);
		

		searchGoogle = (Button) findViewById(R.id.HsearchGoogle);
		searchGoogle.setOnClickListener(this);
		searchGoogle.setTypeface(tf);

        Hshare = (Button) findViewById(R.id.Hshare);
        Hshare.setOnClickListener(this);
        Hshare.setTypeface(tf);


		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			date = extras.getString("date");
			//barcode = extras.getString("barcode");
		}

		//tvBarcode.setText(barcode);
		//tvDate.setText(ScanDate.getDate());
		
		//edited code
		hData.open();
		Cursor cr=hData.getHistoryItemCursor(date);
		
		cr.moveToFirst();
		tvType.setText(cr.getString(cr.getColumnIndex(HistoryDatabase.KEY_TYPE)));
		tvBarcode.setText(cr.getString(cr.getColumnIndex(HistoryDatabase.KEY_CONTENT)));
		tvDate.setText(cr.getString(cr.getColumnIndex(HistoryDatabase.KEY_TIME)));
		tvName.setText(cr.getString(cr.getColumnIndex(HistoryDatabase.KEY_NAME)));
		tvOrigin.setText(cr.getString(cr.getColumnIndex(HistoryDatabase.KEY_STATUS)));
		tvCategory.setText(cr.getString(cr.getColumnIndex(HistoryDatabase.KEY_CATEGORY)));

        barcode = cr.getString(cr.getColumnIndex(HistoryDatabase.KEY_CONTENT));
        type = cr.getString(cr.getColumnIndex(HistoryDatabase.KEY_TYPE));

        if(barcode.substring(0,4).equals("http")){
            tvBarcode.setOnClickListener(this);
            tvBarcode.setTextColor(0xff40bfea);
            tvBarcode.setPaintFlags(tvBarcode.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

            searchGoogle.setText("Open Browser");
        }

		String temp=cr.getString(cr.getColumnIndex(HistoryDatabase.KEY_STATUS));
		if(temp.contains("ISBN") || temp.contains("ISSN")){
			tvHeadOrigin.setText("Origin/Category");
		}
		
		if(temp.equals("No Match Found")){
			tvOrigin.setVisibility(View.GONE);
			tvHeadOrigin.setVisibility(View.GONE);
		}
		
		hData.close();

        AdView adView = (AdView)findViewById(R.id.Hads);
        AdRequest adRequest = (new AdRequest.Builder()).build();
        adView.loadAd(adRequest);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {


        case R.id.HtvBarcode:
             startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(tvBarcode.getText().toString())));
             break;

        case R.id.ivIcon3:
		case R.id.HsearchGoogle:

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

        case R.id.HivShare:
        case R.id.Hshare:

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
        overridePendingTransition(R.animator.slide_in_left,R.animator.slide_out_right);
    }

}
