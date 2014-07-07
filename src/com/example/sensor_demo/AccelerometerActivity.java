package com.example.sensor_demo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AccelerometerActivity extends Activity {

	private TextView titleTextView = null;
	private TextView contenTextView = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accelerometer);
		
		titleTextView = (TextView)findViewById(R.id.titleText);
		contenTextView = (TextView)findViewById(R.id.contentText);
		
		titleTextView.setText("Accelerometer Sensor");
		contenTextView.setText("This is Accelerometer sensor output ... ");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
