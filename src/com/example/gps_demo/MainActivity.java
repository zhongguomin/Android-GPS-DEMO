package com.example.gps_demo;

import java.util.Iterator;

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
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText editText;
	private TextView logText;

	private LocationManager mLocationManager;

	private static final String TAG = "GPS-DEMO";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		editText = (EditText)findViewById(R.id.editText);
		logText = (TextView)findViewById(R.id.logText);

		mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

		if(mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			Toast.makeText(this, "Please open GPS ...", Toast.LENGTH_SHORT).show();
			
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivityForResult(intent, 0);
			return;
		}
		
		String bestProvider = mLocationManager.getBestProvider(getCriteria(), true);
		Location location = mLocationManager.getLastKnownLocation(bestProvider);
		updateView(location);
		
		mLocationManager.addGpsStatusListener(gpsStatusListener);
		mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
	}
	
	private LocationListener locationListener = new LocationListener() {
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			switch (status) {
			case LocationProvider.AVAILABLE:
				logText.setText("LocationProvider.AVAILABLE");
				break;
			case LocationProvider.OUT_OF_SERVICE:
				logText.setText("LocationProvider.OUT_OF_SERVICE");
				break;
			case LocationProvider.TEMPORARILY_UNAVAILABLE:
				logText.setText("LocationProvider.TEMPORARILY_UNAVAILABLE");
				break;
			default:
				logText.setText("unkown status ... ");
				break;
			}
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			Location location = mLocationManager.getLastKnownLocation(provider);
			updateView(location);
		}
		
		@Override
		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub
			updateView(null);
		}
		
		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			updateView(location);
		}
	};

	GpsStatus.Listener gpsStatusListener = new GpsStatus.Listener() {
		
		@Override
		public void onGpsStatusChanged(int event) {
			// TODO Auto-generated method stub
			switch (event) {
			case GpsStatus.GPS_EVENT_FIRST_FIX:
				logText.setText("event: GPS first fix");
				break;
			case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
				logText.setText("event: GPS satellite status");
				
				GpsStatus gpsStatus = mLocationManager.getGpsStatus(null);
				int maxSatellites = gpsStatus.getMaxSatellites();
				Iterator<GpsSatellite> iterator = gpsStatus.getSatellites().iterator();
				int count = 0;
				while(iterator.hasNext() && count <= maxSatellites) {
					GpsSatellite s = iterator.next();
					count++;
				}
				logText.setText("Search " + count + " GpsSatellite");
				break;
			case GpsStatus.GPS_EVENT_STARTED:
				logText.setText("event: GPS started");
				break;
			case GpsStatus.GPS_EVENT_STOPPED:
				logText.setText("event: GPS stopped");
				break;
			default:
				logText.setText("No match ...");
				break;
			}
		}
	};

	private void updateView(Location location) {
		if(location != null) {
			editText.setText("Longitude = ");
			editText.append(String.valueOf(location.getLongitude()));
			editText.append("\n Latitude = ");
			editText.append(String.valueOf(location.getLatitude()));
			editText.append("\n altitude = ");
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
		criteria.setAltitudeRequired(false);
		criteria.setPowerRequirement(Criteria.POWER_LOW);

		return criteria;
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
