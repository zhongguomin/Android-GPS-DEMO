package com.example.sensor_demo;

import java.util.Iterator;
import java.util.List;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText editText;
	private TextView logText;

	private LocationManager mLocationManager;

	private static final String TAG = "SENSOR-DEMO";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		editText = (EditText)findViewById(R.id.editText);
		logText = (TextView)findViewById(R.id.logText);

		mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

		/*
		 
		mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		mLocationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER);

		if(!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			Toast.makeText(this, "Please open GPS ...", Toast.LENGTH_SHORT).show();
			
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivityForResult(intent, 0);
			return;
		}
		
		*/
		
		
		/*
		String bestProvider = mLocationManager.getBestProvider(getCriteria(), true);
		if (bestProvider != null) {
			Location location = mLocationManager.getLastKnownLocation(bestProvider);
			updateView(location);
			
			mLocationManager.addGpsStatusListener(gpsStatusListener);
			
			//mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
			mLocationManager.requestLocationUpdates(bestProvider, 1000, 1, locationListener);
		} else {
			Log.i(TAG, "bestProvider is null ...");
		}
		*/
		
		// For test
		printAllSensors();
		
	}
	
	private LocationListener locationListener = new LocationListener() {
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			switch (status) {
			case LocationProvider.AVAILABLE:
				setLogText("--- LocationProvider.AVAILABLE");
				break;
			case LocationProvider.OUT_OF_SERVICE:
				setLogText("--- LocationProvider.OUT_OF_SERVICE");
				break;
			case LocationProvider.TEMPORARILY_UNAVAILABLE:
				setLogText("--- LocationProvider.TEMPORARILY_UNAVAILABLE");
				break;
			default:
				setLogText("--- unkown status ... ");
				break;
			}
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			Location location = mLocationManager.getLastKnownLocation(provider);
			setLogText("--- onProviderEnabled");
			updateView(location);
		}
		
		@Override
		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub
			setLogText("--- onProviderDisabled");
			updateView(null);
		}
		
		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			setLogText("--- onLocationChanged");
			updateView(location);
		}
	};

	GpsStatus.Listener gpsStatusListener = new GpsStatus.Listener() {
		
		@Override
		public void onGpsStatusChanged(int event) {
			// TODO Auto-generated method stub
			switch (event) {
			case GpsStatus.GPS_EVENT_FIRST_FIX:
				setLogText("event: GPS first fix");
				break;
			case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
				setLogText("event: GPS satellite status");
				
				GpsStatus gpsStatus = mLocationManager.getGpsStatus(null);
				int maxSatellites = gpsStatus.getMaxSatellites();
				Iterator<GpsSatellite> iterator = gpsStatus.getSatellites().iterator();
				int count = 0;
				while(iterator.hasNext() && count <= maxSatellites) {
					GpsSatellite s = iterator.next();
					count++;
				}
				setLogText("Search " + count + " GpsSatellite");
				break;
			case GpsStatus.GPS_EVENT_STARTED:
				setLogText("event: GPS started");
				break;
			case GpsStatus.GPS_EVENT_STOPPED:
				setLogText("event: GPS stopped");
				break;
			default:
				setLogText("No match ...");
				break;
			}
		}
	};
	
	private void setLogText(String loginfo) {
		logText.setText(loginfo + "\n=====================================\n" + logText.getText());
	}

	private void updateView(Location location) {
		if(location != null) {
			editText.setText("Longitude = ");
			editText.append(String.valueOf(location.getLongitude()));
			editText.append("\nLatitude = ");
			editText.append(String.valueOf(location.getLatitude()));
			editText.append("\naltitude = ");
			editText.append(String.valueOf(location.getAltitude()));
		} else {
			editText.getEditableText().clear();
		}
	}

	private Criteria getCriteria() {
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setSpeedRequired(false);
		criteria.setCostAllowed(false);
		criteria.setBearingRequired(false);
		criteria.setAltitudeRequired(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);

		return criteria;
	}
	
	private void printAllSensors(){
		SensorManager sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		List<Sensor> allSensor = sensorManager.getSensorList(Sensor.TYPE_ALL);
		
		logText.setText("Have " + allSensor.size() + " sensor \n\n");
		
		for(Sensor s : allSensor) {
			String sensorInfo = "\n" + "device name = " + s.getName() +
								"\n" + "device version = " + s.getVersion() +
								"\n" + "device vendor = " + s.getVendor() + "\n\n";
			
			switch (s.getType()) {
			case Sensor.TYPE_ACCELEROMETER:
				logText.setText(logText.getText().toString() + s.getType() + " ACCELEROMETER" + sensorInfo);
				break;
			case Sensor.TYPE_GYROSCOPE:
				logText.setText(logText.getText().toString() + s.getType() + " GYROSCOPE" + sensorInfo);
				break;
			case Sensor.TYPE_LIGHT:
				logText.setText(logText.getText().toString() + s.getType() + " LIGHT" + sensorInfo);
				break;
			case Sensor.TYPE_MAGNETIC_FIELD:
				logText.setText(logText.getText().toString() + s.getType() + " MAGNETIC_FIELD" + sensorInfo);
				break;
			case Sensor.TYPE_ORIENTATION:
				logText.setText(logText.getText().toString() + s.getType() + " ORIENTATION" + sensorInfo);
				break;
			case Sensor.TYPE_PRESSURE:
				logText.setText(logText.getText().toString() + s.getType() + " PRESSURE" + sensorInfo);
				break;
			case Sensor.TYPE_PROXIMITY:
				logText.setText(logText.getText().toString() + s.getType() + " PROXIMITY" + sensorInfo);
				break;
			case Sensor.TYPE_TEMPERATURE:
				logText.setText(logText.getText().toString() + s.getType() + " TEMPERATURE" + sensorInfo);
				break;
			default:
				logText.setText("Unkown Sensor ...");
				break;
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		mLocationManager.removeUpdates(locationListener);
	}

}
