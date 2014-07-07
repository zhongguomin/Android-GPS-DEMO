package com.example.sensor_demo;

import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class LocationActivity extends Activity {

	private static final String TAG = "SENSOR-DEMO";
	
	private LocationManager locationManager = null;
	private SensorManager sensorManager = null;
	
	private TextView titleTextView = null;
	private TextView contentTextView = null;
	private TextView logTextView = null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location);
		
		titleTextView = (TextView)findViewById(R.id.titleText);
		contentTextView = (TextView)findViewById(R.id.contentText);
		logTextView = (TextView)findViewById(R.id.logText);
		
		titleTextView.setText("Location Sensor");
		contentTextView.setText("This is location sensor output ...");
		logTextView.setText("This is sensor event log output ...");
		
		locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		// locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		// locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER);
		if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			Toast.makeText(this, "Please open GPS ...", Toast.LENGTH_SHORT).show();
			
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivityForResult(intent, 0);
			return;
		}
		
		String bestProvider = locationManager.getBestProvider(getCriteria(), true);
		if (bestProvider != null) {
			Location location = locationManager.getLastKnownLocation(bestProvider);
			updateView(location);
			
			locationManager.addGpsStatusListener(gpsStatusListener);
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
			// locationManager.requestLocationUpdates(bestProvider, 1000, 1, locationListener);
		} else {
			Log.i(TAG, "bestProvider is null ...");
		}
	}

	private LocationListener locationListener = new LocationListener() {
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			switch (status) {
			case LocationProvider.AVAILABLE:
				setLogText("LocationListener -- LocationProvider.AVAILABLE");
				break;
			case LocationProvider.OUT_OF_SERVICE:
				setLogText("LocationListener -- LocationProvider.OUT_OF_SERVICE");
				break;
			case LocationProvider.TEMPORARILY_UNAVAILABLE:
				setLogText("LocationListener -- LocationProvider.TEMPORARILY_UNAVAILABLE");
				break;
			default:
				setLogText("LocationListener -- unkown status ... ");
				break;
			}
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			Location location = locationManager.getLastKnownLocation(provider);
			setLogText("LocationListener -- onProviderEnabled");
			updateView(location);
		}
		
		@Override
		public void onProviderDisabled(String arg0) {
			setLogText("LocationListener -- onProviderDisabled");
			updateView(null);
		}
		
		@Override
		public void onLocationChanged(Location location) {
			setLogText("LocationListener -- onLocationChanged");
			updateView(location);
		}
	};

	GpsStatus.Listener gpsStatusListener = new GpsStatus.Listener() {
		@Override
		public void onGpsStatusChanged(int event) {
			switch (event) {
			case GpsStatus.GPS_EVENT_FIRST_FIX:
				setLogText("GpsStatus.Listener -- event: GPS first fix");
				break;
			case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
				setLogText("GpsStatus.Listener -- event: GPS satellite status");
				
				GpsStatus gpsStatus = locationManager.getGpsStatus(null);
				int maxSatellites = gpsStatus.getMaxSatellites();
				Iterator<GpsSatellite> iterator = gpsStatus.getSatellites().iterator();
				int count = 0;
				while(iterator.hasNext() && count <= maxSatellites) {
					GpsSatellite s = iterator.next();
					count++;
				}
				setLogText("GpsStatus.Listener -- Search " + count + " GpsSatellite");
				break;
			case GpsStatus.GPS_EVENT_STARTED:
				setLogText("GpsStatus.Listener -- event: GPS started");
				break;
			case GpsStatus.GPS_EVENT_STOPPED:
				setLogText("GpsStatus.Listener -- event: GPS stopped");
				break;
			default:
				setLogText("GpsStatus.Listener -- No match ...");
				break;
			}
		}
	};
	
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
	
	private void updateView(Location location) {
		if(location != null) {
			contentTextView.setText("Longitude = ");
			contentTextView.append(String.valueOf(location.getLongitude()));
			contentTextView.append("\nLatitude = ");
			contentTextView.append(String.valueOf(location.getLatitude()));
			contentTextView.append("\nAltitude = ");
			contentTextView.append(String.valueOf(location.getAltitude()));
			contentTextView.append("\n\n");
		} else {
			contentTextView.setText("");
		}
	}
	
	private void setLogText(String info) {
		logTextView.setText(info);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		locationManager.removeUpdates(locationListener);
	}
}
