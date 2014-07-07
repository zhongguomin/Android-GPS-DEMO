package com.example.sensor_demo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class OrientationActivity extends Activity {

	private TextView titleTextView = null;
	private TextView contentTextView = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orientation);
		
		titleTextView = (TextView)findViewById(R.id.titleText);
		contentTextView = (TextView)findViewById(R.id.contentText);
		
		titleTextView.setText("Orientation Sensor");
		contentTextView.setText("This is orientation sensor output ...");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
