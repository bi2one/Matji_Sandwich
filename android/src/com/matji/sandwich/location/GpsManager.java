package com.matji.sandwich.location;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import com.matji.sandwich.exception.GpsOutOfServiceMatjiException;
import com.matji.sandwich.exception.GpsTemporarilyUnavailableMatjiException;
import com.matji.sandwich.exception.GpsAvailableMatjiException;
import com.matji.sandwich.exception.GpsEnabledMatjiException;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.UseNetworkGpsMatjiException;

public class GpsManager implements LocationListener {
    private final static long NET_PROVIDER_MIN_NOTIFICATION_INTERVAL = 100; /* 1000 * 60 * 5; */
    private final static long GPS_PROVIDER_MIN_NOTIFICATION_INTERVAL = 50;      /* 1000 * 5; */
    
    private Context context;
    private MatjiLocationListener matjiListener;
    private LocationManager locationManager;
    private String majorProvider;
    private boolean gpsPerformed;
    private final static int minDistanceForNotifyInMeters = 10;
    
    public GpsManager(Context context, MatjiLocationListener matjiListener) {
	this.context = context;
	this.matjiListener = matjiListener;
	locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
	gpsPerformed = false;
    }

    public void start() {
	notifyLastKnownLocation();
	try {
	    if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
		requestLocationUpdatesGps();
	    } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
		requestLocationUpdatesNetwork();
	    } else {
		// show gps setting dialog
		startLocationSettingsActivity();
	    }
	} catch(MatjiException e) {
	    matjiListener.onLocationExceptionDelivered(e);
	}
    }

    private void notifyLastKnownLocation() {
	Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	if (loc == null)
	    loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

	notifyLocationChanged(loc);
    }

    private void requestLocationUpdatesGps() throws MatjiException {
	majorProvider = LocationManager.GPS_PROVIDER;
	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
					       GPS_PROVIDER_MIN_NOTIFICATION_INTERVAL, minDistanceForNotifyInMeters, this);
	locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
					       NET_PROVIDER_MIN_NOTIFICATION_INTERVAL, minDistanceForNotifyInMeters, this);
    }

    private void requestLocationUpdatesNetwork() throws MatjiException {
	majorProvider = LocationManager.NETWORK_PROVIDER;
	matjiListener.onLocationExceptionDelivered(new UseNetworkGpsMatjiException());
	locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
					       NET_PROVIDER_MIN_NOTIFICATION_INTERVAL, minDistanceForNotifyInMeters, this);
    }

    private void notifyLocationChanged(Location loc) {
	if (loc != null)
	    matjiListener.onLocationChanged(loc);
	else {
	    Location seoulLocation = new Location("");
	    seoulLocation.setLatitude(37.541);
	    seoulLocation.setLongitude(126.986);
	    matjiListener.onLocationChanged(seoulLocation);
	    // exception !!
	}
    }

    public void stop() {
	locationManager.removeUpdates(this);
    }

    public void onProviderDisabled(String provider) {
	if (provider.equals(majorProvider))
	    startLocationSettingsActivity();
    }

    public void onProviderEnabled(String provider) {
	if (provider.equals(majorProvider))
	    matjiListener.onLocationExceptionDelivered(new GpsEnabledMatjiException());
    }
    
    public void onStatusChanged(String provider, int status, Bundle extras) {
		if (provider.equals(majorProvider)) {
		    switch(status) {
		    	case LocationProvider.OUT_OF_SERVICE:
		    		matjiListener.onLocationExceptionDelivered(new GpsOutOfServiceMatjiException());
		    		break;
		    	case LocationProvider.TEMPORARILY_UNAVAILABLE:
		    		matjiListener.onLocationExceptionDelivered(new GpsTemporarilyUnavailableMatjiException());
		    		break;
		    	case LocationProvider.AVAILABLE:
		    		matjiListener.onLocationExceptionDelivered(new GpsAvailableMatjiException());
		    		break;
		    }
		}
    }

    public void onLocationChanged(Location location) {
		if (!gpsPerformed && location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
		    gpsPerformed = true;
		    // Log.d("LOCATION_CHANGED", "gps_provider");
		    locationManager.removeUpdates(this);
		    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
							   GPS_PROVIDER_MIN_NOTIFICATION_INTERVAL, minDistanceForNotifyInMeters, this);
		} else if (location.getProvider().equals(LocationManager.NETWORK_PROVIDER)) {
		    // Log.d("LOCATION_CHANGED", "network_provider");
		    matjiListener.onLocationExceptionDelivered(new UseNetworkGpsMatjiException());
		}
		// Log.d("LOCATION_CHANGED", "final");
		notifyLocationChanged(location);
    }

    private void startLocationSettingsActivity() {
		Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		context.startActivity(intent);
    }
}