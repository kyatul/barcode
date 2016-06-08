package com.bigdreams.barcodescanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import com.mirasense.scanditsdk.ScanditSDKAutoAdjustingBarcodePicker;
import com.mirasense.scanditsdk.interfaces.ScanditSDK;
import com.mirasense.scanditsdk.interfaces.ScanditSDKListener;

public class ScanditBarScan extends Activity implements ScanditSDKListener {

	private ScanditSDK mBarcodePicker;
	public static final String sScanditSdkAppKey = "z3YezNC";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ScanditSDKAutoAdjustingBarcodePicker picker = new ScanditSDKAutoAdjustingBarcodePicker(
				this, sScanditSdkAppKey,
				ScanditSDKAutoAdjustingBarcodePicker.CAMERA_FACING_BACK);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(picker);
		mBarcodePicker = picker;
		mBarcodePicker.getOverlayView().addListener(this);

		// ENABLING 2D BARCODE RECOGNITION
		mBarcodePicker.setQrEnabled(true);
		mBarcodePicker.setDataMatrixEnabled(true);

		
	}

	@Override
	protected void onPause() {
		super.onPause();
		mBarcodePicker.stopScanning();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mBarcodePicker.startScanning();
	}

	@Override
	public void didCancel() {
		mBarcodePicker.stopScanning();
		finish();
	}

	@Override
	public void didManualSearch(String arg0) {

	}

	@Override
	public void didScanBarcode(String barcode, String type) {

		Intent i = new Intent(this, ScanResult.class);
		i.putExtra("barcode", barcode);
		i.putExtra("type", type);

		startActivity(i);
		finish();

	}

	@Override
	public void onBackPressed() {
		mBarcodePicker.stopScanning();
		finish();

	}

}
