package com.bigdreams.barcodescanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigdreams.barcodescanner.CustomButtonTouchListener;
import com.bigdreams.barcodescanner.CustomImageTouchListener;
import com.bigdreams.barcodescanner.HistoryDatabase;
import com.bigdreams.barcodescanner.R;


public class DialogSearch extends Activity implements View.OnClickListener{

    TextView tvProductSearch,tvBookSearch,tvCancelSearch;
    Bundle extras;
    String barcode;
    Typeface tf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_search);

        tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");

        tvProductSearch = (TextView) findViewById(R.id.tvProductSearch);
        tvBookSearch = (TextView) findViewById(R.id.tvBookSearch);
        tvCancelSearch = (TextView) findViewById(R.id.tvCancelSearch);

        tvProductSearch.setTypeface(tf);
        tvBookSearch.setTypeface(tf);
        tvCancelSearch.setTypeface(tf);

        tvProductSearch.setOnClickListener(this);
        tvBookSearch.setOnClickListener(this);
        tvCancelSearch.setOnClickListener(this);

        CustomTextTouchListener ctl1 = new CustomTextTouchListener(this,0xfffafafa);
        CustomTextTouchListener ctl2 = new CustomTextTouchListener(this,0xffb9baba);

        tvBookSearch.setOnTouchListener(ctl1);
        tvProductSearch.setOnTouchListener(ctl1);
        tvCancelSearch.setOnTouchListener(ctl2);

        extras = getIntent().getExtras();
        if (extras != null) {
            barcode = extras.getString("barcode");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvCancelSearch:
                finish();
                break;
            case R.id.tvProductSearch:
                String url="http://www.google.com/search?q=" + barcode;
                Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(Intent.createChooser(browser,"Search Barcode on Web"));
                finish();
                break;
            case R.id.tvBookSearch:
                String url1="http://books.google.com/books?vid=isbn" + barcode;
                Intent browser1 = new Intent(Intent.ACTION_VIEW, Uri.parse(url1));
                startActivity(Intent.createChooser(browser1,"Search Book on Web"));
                finish();
                break;
        }
    }
}
