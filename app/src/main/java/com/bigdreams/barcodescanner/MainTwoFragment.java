package com.bigdreams.barcodescanner;

import java.util.ArrayList;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainTwoFragment extends Activity implements OnClickListener{
	ListView lv;
	TextView tvHeadHistory,tvHome4,tvNoScan;
	Typeface tf;
	HistoryDatabase hData;
	ArrayList<String> history_name;
    ArrayList<String> history_date;
    ImageView ivHome4,ivIcon1;
	
	@Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
    
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_two);
        tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        
        hData = new HistoryDatabase(this);

        tvNoScan = (TextView) findViewById(R.id.tvNoScan);
        tvHeadHistory = (TextView) findViewById(R.id.tvHeadHistory);
        tvHeadHistory.setTypeface(tf);
        tvNoScan.setTypeface(tf);

        ivHome4 = (ImageView) findViewById(R.id.ivHome4);
        ivIcon1 = (ImageView) findViewById(R.id.ivIcon1);
        tvHome4 = (TextView) findViewById(R.id.tvHome4);

        tvHeadHistory.setOnClickListener(this);
        tvHome4.setOnClickListener(this);
        ivHome4.setOnClickListener(this);
        ivIcon1.setOnClickListener(this);

        tvHome4.setTypeface(tf);

        lv = (ListView) findViewById(R.id.listView);
        
        initializeListView();

        AdView adView = (AdView)findViewById(R.id.adView2);
        AdRequest adRequest = (new AdRequest.Builder()).build();
        adView.loadAd(adRequest);
	
	}

    public void initializeListView(){
        history_name = new ArrayList<String>();
        history_date = new ArrayList<String>();

        //INITIALIZING THE LISTVIEW FROM DATABASE
        hData.open();
        history_name = hData.getHistoryDataName();
        if (history_name.size() == 0) {
            tvNoScan.setVisibility(View.VISIBLE);
            lv.setVisibility(View.GONE);
        }
        else{
            tvNoScan.setVisibility(View.GONE);
            lv.setVisibility(View.VISIBLE);
            history_date = hData.getHistoryDataDate();

            AdapterHistoryList adapterHis = new AdapterHistoryList(this,history_date,history_name);
            lv.setAdapter(adapterHis);

            lv.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View viewList,
                                        int position, long id) {

                    Intent i = new Intent(MainTwoFragment.this, HistoryItem.class);
                    i.putExtra("date", history_date.get(position));

                    startActivity(i);
                    overridePendingTransition(R.animator.slide_in_right,R.animator.slide_out_left);


                }
            });
            lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { //for deletion purpose

                public boolean onItemLongClick(AdapterView<?> arg0, View view,int position, long id) {

                    Intent i = new Intent(MainTwoFragment.this, DialogDelete.class);
                    i.putExtra("date",history_date.get(position));
                    startActivityForResult(i,1);
                    return true;
                }
            });


        }

        hData.close();

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (1) : {
                if (resultCode == Activity.RESULT_OK) {
                    initializeListView();
                }
                break;
            }
        }
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()){

            case R.id.ivIcon1:
        case R.id.tvHeadHistory:
			
			hData.open();
			if (hData.getRowCount() > 0) {
				showDeleteConfirmationDialog();
			} else {
				Toast.makeText(this, "No history data present",
						Toast.LENGTH_SHORT).show();
			}
			hData.close();
			break;

        case R.id.ivHome4:
		case R.id.tvHome4:
			
			hData.open();
			if (hData.getRowCount() > 0) {
				SendHistory sh = new SendHistory(this);
				sh.mailHistory();
			} else {
				Toast.makeText(this, "No history data present",
						Toast.LENGTH_SHORT).show();
			}
			hData.close();
			break;
		}
		
		

	}  
	
	public void showDeleteConfirmationDialog() {
		final Dialog dialog = new Dialog(this,
				android.R.style.Theme_Translucent_NoTitleBar);
		dialog.setContentView(R.layout.dialog_rate);   //we are using rate UI dialog box for deletion 

		TextView t=(TextView) dialog.findViewById(R.id.tvRate);
		t.setTypeface(tf);
		t.setText("Are you sure ?");
		
		TextView t1=(TextView) dialog.findViewById(R.id.tvheadRate1);
		t1.setTypeface(tf);
		t1.setText("Delete All");
		
		ImageView acceptButton = (ImageView) dialog.findViewById(R.id.ivRateYes);
		acceptButton.setOnTouchListener(new CustomImageTouchListener(getApplicationContext()));
		acceptButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {				
				hData.open();
				hData.deleteAll();
				hData.close();

                tvNoScan.setVisibility(View.VISIBLE);
                lv.setVisibility(View.GONE);
                dialog.dismiss();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.animator.slide_in_left,R.animator.slide_out_right);
    }

}
