package com.example.sensor_demo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TemperatureActivity extends Activity {

	/*
	 * Temperature sensor
	 * 		Sensor.TYPE_TEMPERATURE
	 * 		values[0] = temperature
	 * 
	 */
	
	private TextView titleTextView = null;
	private TextView contentTextView = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_temperature);
		
		titleTextView = (TextView)findViewById(R.id.titleText);
		contentTextView = (TextView)findViewById(R.id.contentText);
		
		titleTextView.setText("Temperature Sensor");
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
