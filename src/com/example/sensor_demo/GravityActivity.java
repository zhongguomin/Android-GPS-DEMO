package com.example.sensor_demo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class GravityActivity extends Activity {

	private TextView titleTextView = null;
	private TextView contentTextView = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gravity);
		
		titleTextView = (TextView)findViewById(R.id.titleText);
		contentTextView = (TextView)findViewById(R.id.contentText);
		
		titleTextView.setText("Gravity Sensor");
		contentTextView.setText("This is gravity sensor output .. ");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
