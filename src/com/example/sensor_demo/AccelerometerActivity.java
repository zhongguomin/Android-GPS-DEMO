package com.example.sensor_demo;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class AccelerometerActivity extends Activity {

	private TextView titleTextView = null;
	private TextView xTextView = null;
	private TextView yTextView = null;
	private TextView zTextView = null;
	private TextView speedTextView = null;
	
	private SensorManager sensorManager = null;
	private Sensor sensor = null;
	
	private static final double SHAKE_SHRESHOLD = 7000d;
	private long lastTime = 0;
	private float last_x = 0;
	private float last_y = 0;
	private float last_z = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accelerometer);
		
		titleTextView = (TextView)findViewById(R.id.titleText);
		xTextView = (TextView)findViewById(R.id.text_x);
		yTextView = (TextView)findViewById(R.id.text_y);
		zTextView = (TextView)findViewById(R.id.text_z);
		speedTextView = (TextView)findViewById(R.id.text_speed);
		
		titleTextView.setText("Accelerometer Sensor");
		
		sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	}

	private SensorEventListener sensorEventListener = new SensorEventListener() {
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			// TODO Auto-generated method stub
			if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
				long curTime = System.currentTimeMillis();
				if((curTime - lastTime) > 10 ) {
					long diffTime = curTime - lastTime;
					lastTime = curTime;
					
					float x = event.values[0];
					float y = event.values[1];
					float z = event.values[2];
					
					float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;
					if(speed > SHAKE_SHRESHOLD) {
						
					}
					
					last_x = x;
					last_y = y;
					last_z = z;
				}
				
				xTextView.setText("x = " + event.values[0]);
				yTextView.setText("y = " + event.values[1]);
				zTextView.setText("z = " + event.values[2]);
			}
		}
		
		@Override
		public void onAccuracyChanged(Sensor arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}
	};
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_UI);
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		sensorManager.unregisterListener(sensorEventListener);
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
