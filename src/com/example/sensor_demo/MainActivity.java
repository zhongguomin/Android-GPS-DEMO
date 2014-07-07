package com.example.sensor_demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.R.integer;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	private static final String TAG = "SENSOR-DEMO";
	
	private ListView sensorListView = null;
	private ArrayList<Map<String, Object>> sensors = new ArrayList<Map<String, Object>>();

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		sensorListView = getListView();
		initSensorListViewData();
		
		SimpleAdapter adapter = new SimpleAdapter(this, sensors, R.layout.activity_main,
				new String[]{"sensor_name", "sensor_type", "sensor_vendor", "sensor_version"}, 
				new int[]{R.id.sensorNameText, R.id.sensorTypeText, R.id.sensorVendorText, R.id.sensorVersionText});
		setListAdapter(adapter);
		
		sensorListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, 
					int position, long id) {
				int sensorType = Integer.parseInt(sensors.get(position).get("sensor_type").toString());
				handlerItemClickEvent(position, sensorType);
			}
		});
	}
	
	private void handlerItemClickEvent(int position, int sensorType) {
		switch (sensorType) {
		case Sensor.TYPE_ACCELEROMETER:
			Intent accelerometer_intent = new Intent(MainActivity.this, AccelerometerActivity.class);
			startActivity(accelerometer_intent);
			break;
		case Sensor.TYPE_MAGNETIC_FIELD:
			Intent magneticField_intent = new Intent(MainActivity.this, MagneticFieldActivity.class);
			startActivity(magneticField_intent);
			break;
		case Sensor.TYPE_ORIENTATION:
			Intent orientation_intent = new Intent(MainActivity.this, OrientationActivity.class);
			startActivity(orientation_intent);
			break;
		case Sensor.TYPE_GYROSCOPE:
			Intent gyroscope_intent = new Intent(MainActivity.this, GyroscopeActivity.class);
			startActivity(gyroscope_intent);
			break;
		case Sensor.TYPE_LIGHT:
			Intent light_intent = new Intent(MainActivity.this, LightActivity.class);
			startActivity(light_intent);
			break;
		case Sensor.TYPE_PRESSURE:
			Intent pressure_intent = new Intent(MainActivity.this, PressureActivity.class);
			startActivity(pressure_intent);
			break;
		case Sensor.TYPE_TEMPERATURE:
			Intent temperature_intent = new Intent(MainActivity.this, TemperatureActivity.class);
			startActivity(temperature_intent);
			break;
		case Sensor.TYPE_PROXIMITY:
			Intent proximity_intent = new Intent(MainActivity.this, ProximityActivity.class);
			startActivity(proximity_intent);
			break;
		case Sensor.TYPE_GRAVITY:
			Intent gravity_intent = new Intent(MainActivity.this, GravityActivity.class);
			startActivity(gravity_intent);
			break;
		default:
			Toast.makeText(MainActivity.this, "Unkown Sensor Type ...", 
					Toast.LENGTH_SHORT).show();					
			break;
		}	
	}

	private void initSensorListViewData() {
		List<Sensor> sensorList = getAllSensors();
		
		for(Sensor s : sensorList) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("sensor_name", s.getName());
			item.put("sensor_type", s.getType());
			item.put("sensor_vendor", s.getVendor());
			item.put("sensor_version", s.getVersion());
			sensors.add(item);
		}
	}

	private List<Sensor> getAllSensors(){
		SensorManager sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
		
		return sensorList;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_location:
			Intent intent = new Intent(MainActivity.this, LocationActivity.class);
			startActivity(intent);
			break;
		default:
			Toast.makeText(MainActivity.this, "Unkown Optons Item ...", 
					Toast.LENGTH_SHORT).show();	
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
}
