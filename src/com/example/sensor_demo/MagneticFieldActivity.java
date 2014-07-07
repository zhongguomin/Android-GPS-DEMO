package com.example.sensor_demo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MagneticFieldActivity extends Activity {

	private TextView titleTextView = null;
	private TextView contentTextView = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_magneticfield);
		
		titleTextView = (TextView)findViewById(R.id.titleText);
		contentTextView = (TextView)findViewById(R.id.contentText);
		
		titleTextView.setText("MagneticField Sensor");
		contentTextView.setText("This is magneticField Sensor");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
}
