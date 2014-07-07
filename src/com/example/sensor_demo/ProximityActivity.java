package com.example.sensor_demo;

import android.app.Activity;
import android.os.Bundle;

public class ProximityActivity extends Activity {

	/*
	 * Proximity sensor
	 * 		Sensor.TYPE_PROXIMITY
	 * 		values[0]: Proximity sensor distance measured 
	 *			in centimeters (sometimes binary near-far)
	 * 
	 */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_proximity);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
