package com.matji.sandwich;

import android.util.Log;
import android.view.View;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.location.GpsManager;
import com.matji.sandwich.location.MatjiLocationListener;

public class GpsTestActivity extends BaseActivity implements MatjiLocationListener {

	private GpsManager gpsManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	protected void onResume() {
		super.onResume();
		gpsManager.start();
	}

	protected void onPause() {
		super.onPause();
		gpsManager.stop();
	}

	public void onLocationChanged(Location location) {
		Toast.makeText(getApplicationContext(), "lat: " + location.getLatitude() + ", lng: " + location.getLongitude(), Toast.LENGTH_SHORT).show();
		Log.d("LOCATION", "lat: " + location.getLatitude() + ", lng: " + location.getLongitude());
	}

	public void onLocationExceptionDelivered(MatjiException e) {
		e.performExceptionHandling(getApplicationContext());
	}

<<<<<<< HEAD
=======
	@Override
	protected String titleBarText() {
		return "GpsTestActivity";
	}

	@Override
	protected boolean setTitleBarButton(Button button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void onTitleBarItemClicked(View view) {
		// TODO Auto-generated method stub
		
	}
>>>>>>> e71624aacaf456a4885c664beb0095e175f3625d
}