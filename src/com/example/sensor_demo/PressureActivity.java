package com.example.sensor_demo;

import android.app.Activity;
import android.os.Bundle;

public class PressureActivity extends Activity {

	/*
	 * Pressure sensor
	 * 		Sensor.TYPE_PRESSURE
	 * 		values[0] = pressure
	 * 
	 */
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pressure);
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
