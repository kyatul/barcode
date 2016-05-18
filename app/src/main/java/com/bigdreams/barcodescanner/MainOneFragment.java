package com.bigdreams.barcodescanner;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;


import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class MainOneFragment extends Activity implements OnClickListener {
    private AdRequest adRequest;
    private AdView adView;
    private InterstitialAd interstitial;

    TextView tvInfo, tvHeadScan,tvContact;
    Typeface tf, tfHead;
    ImageView iv_button, iv_create,ivIcon0,ivContact;


    static final String RESULT_COUNT = "review_count";
    static final String REVIEW_STATUS = "review_status";

    SharedPreferences pref;
    int resultCount;
    boolean reviewStatus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_one);
        tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");

        tvInfo = (TextView) findViewById(R.id.tvInfo);
        tvInfo.setTypeface(tf);

        tvHeadScan = (TextView) findViewById(R.id.tvHeadScan);
        tvHeadScan.setTypeface(tf);
        tvHeadScan.setOnClickListener(this);

        tvContact = (TextView) findViewById(R.id.tvContact);
        tvContact.setTypeface(tf);
        tvContact.setOnClickListener(this);

        ivContact = (ImageView) findViewById(R.id.ivContact);
        ivContact.setOnClickListener(this);

        iv_button = (ImageView) findViewById(R.id.iv_button);
        iv_button.setOnClickListener(this);

        iv_create = (ImageView) findViewById(R.id.iv_create);
        iv_create.setOnClickListener(this);

        ivIcon0 = (ImageView) findViewById(R.id.ivIcon0);
        ivIcon0.setOnClickListener(this);

        pref = getSharedPreferences("My App", Context.MODE_PRIVATE);
        resultCount = pref.getInt(RESULT_COUNT, 0);
        reviewStatus = pref.getBoolean(REVIEW_STATUS, false);
        pref.edit().putInt(RESULT_COUNT, (++resultCount)).commit();


        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId("ca-app-pub-2074945741246452/7938375727");
        adRequest = (new AdRequest.Builder()).build();
        interstitial.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                finish();
            }

        });


        adView = (AdView)findViewById(R.id.adView);
        adView.loadAd(adRequest);

        resultCountCheck();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_button:
                startActivity(new Intent(this, ScanditBarScan.class));
                break;

            case R.id.ivIcon0:
            case R.id.tvHeadScan:
                startActivity(new Intent(this, MainTwoFragment.class));
                overridePendingTransition(R.animator.slide_in_right,R.animator.slide_out_left);
                break;

            case R.id.ivContact:
            case R.id.tvContact:
                startActivity(new Intent(this, MainThreeFragment.class));
                overridePendingTransition(R.animator.slide_in_left,R.animator.slide_out_right);
                break;

            case R.id.iv_create:
                startActivity(new Intent(this, QRCreateActivity.class));
                overridePendingTransition(R.animator.slide_in_right,R.animator.slide_out_left);
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        interstitial.loadAd(adRequest);
    }

    public void resultCountCheck() {

        if (resultCount % 13 == 0) {
            pref.edit().putInt(RESULT_COUNT, 0).commit();

            boolean a = pref.getBoolean(REVIEW_STATUS, false);
            if (!a) {
                loadReviewDialog();
            }
        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
        else{
            finish();
        }
    }

    public void loadReviewDialog() {
        final Dialog dialog = new Dialog(MainOneFragment.this,
                android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setContentView(R.layout.dialog_rate);

        TextView t = (TextView) dialog.findViewById(R.id.tvRate);
        t.setTypeface(tf);

        ImageView acceptButton = (ImageView) dialog.findViewById(R.id.ivRateYes);
        acceptButton.setOnTouchListener(new CustomImageTouchListener(getApplicationContext()));
        acceptButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                pref.edit().putBoolean(REVIEW_STATUS, true).commit();
                dialog.dismiss();

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri
                        .parse("market://details?id=com.bigdreams.barcodescanner"));
                startActivity(intent);

            }
        });

        ImageView declineButton = (ImageView) dialog.findViewById(R.id.ivRateCancel);
        declineButton.setOnTouchListener(new CustomImageTouchListener(getApplicationContext()));
        declineButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
