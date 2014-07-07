package com.example.sensor_demo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class LightActivity extends Activity {

	/*
	 * Light Sensor
	 *		Sensor.TYPE_LIGHT
	 *		values[0] = ambient light level in SI lux units
	 * 
	 */

	private TextView titleTextView = null;
	private TextView contentTextView = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_light);
		
		titleTextView = (TextView)findViewById(R.id.titleText);
		contentTextView = (TextView)findViewById(R.id.contentText);
		
		titleTextView.setText("Light Sensor");
		contentTextView.setText("This is light sensor output ... ");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
