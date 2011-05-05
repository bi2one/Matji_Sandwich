package com.matji.sandwich;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.location.LocationListener;
import android.location.Location;
import android.widget.Toast;

import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.location.GpsManager;
import com.matji.sandwich.location.MatjiLocationListener;

public class GpsTestActivity extends Activity implements MatjiLocationListener {
    private GpsManager gpsManager;
    
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);

	gpsManager = new GpsManager(getApplicationContext(), this);
    }

    protected void onResume() {
	super.onResume();
	gpsManager.start();
    }

    protected void onPause() {
	super.onPause();
	gpsManager.stop();
    }

    public void onInitialLocationDelivered(Location location) {
	Log.d("INIT_LOCATION", "lat: " + location.getLatitude() + ", lng: " + location.getLongitude());
    }

    public void onLocationChanged(Location location) {
	Toast.makeText(getApplicationContext(), "lat: " + location.getLatitude() + ", lng: " + location.getLongitude(), Toast.LENGTH_SHORT).show();
	Log.d("LOCATION", "lat: " + location.getLatitude() + ", lng: " + location.getLongitude());
    }

    public void onLocationExceptionDelivered(MatjiException e) {
	e.showToastMsg(getApplicationContext());
    }
}