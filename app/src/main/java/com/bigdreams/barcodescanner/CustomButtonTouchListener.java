package com.bigdreams.barcodescanner;


import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class CustomButtonTouchListener implements View.OnTouchListener {
	
	public boolean onTouch(View view, MotionEvent motionEvent) {
		switch (motionEvent.getAction()) {
		case MotionEvent.ACTION_DOWN:
			((Button) view).setBackgroundColor(0xFFd3d3d3);
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			((Button) view).setBackgroundColor(0xffb9baba);
			break;
		}
		return false;
	}
}