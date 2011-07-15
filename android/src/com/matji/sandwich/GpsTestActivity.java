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
import com.matji.sandwich.widget.title.TitleText;

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

	@Override
	protected View setCenterTitleView() {
		return new TitleText(this, "GpsTestActivity");
	}
}