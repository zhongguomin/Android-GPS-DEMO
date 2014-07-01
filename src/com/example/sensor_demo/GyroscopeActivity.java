package com.example.sensor_demo;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class GyroscopeActivity extends Activity {
	
	private static final String TAG = "SENSOR-DEMO-GYROSCOPE";
	
	private SensorManager sensorManager = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_gyroscope);
		
		sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		sensorManager.registerListener(sensorEventListener, 
										sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), 
										SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	
	final SensorEventListener sensorEventListener = new SensorEventListener() {
		
		@Override
		public void onSensorChanged(SensorEvent sensorEvent) {
			// TODO Auto-generated method stub
			if(sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
				float x = sensorEvent.values[0];
				float y = sensorEvent.values[1];
				float z = sensorEvent.values[2];
			} else {
				
			}
		}
		
		@Override
		public void onAccuracyChanged(Sensor arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}
	};

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		sensorManager.unregisterListener(sensorEventListener);
		super.onDestroy();
	}


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		sensorManager.unregisterListener(sensorEventListener);
		super.onPause();
	}
	
	

}
