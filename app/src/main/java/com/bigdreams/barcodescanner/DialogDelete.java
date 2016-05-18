package com.bigdreams.barcodescanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigdreams.barcodescanner.CustomButtonTouchListener;
import com.bigdreams.barcodescanner.CustomImageTouchListener;
import com.bigdreams.barcodescanner.HistoryDatabase;
import com.bigdreams.barcodescanner.R;


public class DialogDelete extends Activity implements View.OnClickListener{

    TextView tvHead,tvDesc;
    ImageView ivYes,ivNo;
    Bundle extras;
    String date;
    Typeface tf;
    HistoryDatabase hData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_rate);
        hData = new HistoryDatabase(this);

        tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");

        tvHead = (TextView) findViewById(R.id.tvheadRate1);
        tvDesc = (TextView) findViewById(R.id.tvRate);

        tvHead.setTypeface(tf);
        tvDesc.setTypeface(tf);

        tvHead.setText("Delete");
        tvDesc.setText("Are you sure want to delete this entry ?");
        ivYes = (ImageView) findViewById(R.id.ivRateYes);
        ivNo = (ImageView) findViewById(R.id.ivRateCancel);

        CustomImageTouchListener cbt = new CustomImageTouchListener(getApplicationContext());
        ivYes.setOnTouchListener(cbt);
        ivNo.setOnTouchListener(cbt);

        ivYes.setOnClickListener(this);
        ivNo.setOnClickListener(this);

        extras = getIntent().getExtras();
        if (extras != null) {
            date = extras.getString("date");

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivRateCancel:
                finish();
                break;
            case R.id.ivRateYes:

                hData.open();
                hData.deleteHistoryItem(date);
                hData.close();

                //to call onActivityResult in previous Activity
                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
                break;
        }
    }
}
