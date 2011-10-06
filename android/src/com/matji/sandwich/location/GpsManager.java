package com.matji.sandwich.location;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;

import com.matji.sandwich.exception.GpsOutOfServiceMatjiException;
import com.matji.sandwich.exception.GpsTemporarilyUnavailableMatjiException;
import com.matji.sandwich.exception.GpsAvailableMatjiException;
import com.matji.sandwich.exception.GpsEnabledMatjiException;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.UseNetworkGpsMatjiException;

import java.lang.ref.WeakReference;

public class GpsManager implements LocationListener {
    private final static long NET_PROVIDER_MIN_NOTIFICATION_INTERVAL = 100; /* 1000 * 60 * 5; */
    private final static long GPS_PROVIDER_MIN_NOTIFICATION_INTERVAL = 50;      /* 1000 * 5; */
    
    private WeakReference<Context> contextRef;
    private WeakReference<MatjiLocationListener> matjiListenerRef;
    private LocationManager locationManager;
    private String majorProvider;
    private boolean gpsPerformed;
    private boolean runningFlag;
    private int startFromTag;
    private final static int minDistanceForNotifyInMeters = 0;
    
    public GpsManager(Context context, MatjiLocationListener matjiListener) {
	this.contextRef = new WeakReference(context);
	this.matjiListenerRef = new WeakReference(matjiListener);
	locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
	gpsPerformed = false;
	runningFlag = false;
    }

    public void start(int startFromTag) {
	notifyLastKnownLocation();
	startWithoutLastLocation(startFromTag);
    }

    public void startWithoutLastLocation(int startFromTag) {
	this.startFromTag = startFromTag;
	runningFlag = true;
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
	    matjiListenerRef.get().onLocationExceptionDelivered(startFromTag, e);
	}
    }

    private void notifyLastKnownLocation() {
	Location gpsLoc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	Location netLoc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	Location lastLoc = null;
		
	if (gpsLoc != null && netLoc != null){
	    if (gpsLoc.getTime() > netLoc.getTime()){
		lastLoc = gpsLoc;
	    } else {
		lastLoc = netLoc;
	    }
		
	}else if (gpsLoc != null){
	    lastLoc = gpsLoc;	
	}else if (netLoc != null){
	    lastLoc = netLoc;
	}
		
	if (lastLoc != null){
	    lastLoc.setAccuracy(1000);
	    notifyLocationChanged(lastLoc);
	}
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
	matjiListenerRef.get().onLocationExceptionDelivered(startFromTag, new UseNetworkGpsMatjiException());
	locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
					       NET_PROVIDER_MIN_NOTIFICATION_INTERVAL, minDistanceForNotifyInMeters, this);
    }

    private void notifyLocationChanged(Location loc) {
	if (loc != null) {
	    matjiListenerRef.get().onLocationChanged(startFromTag, loc);
	} else {
	    Location seoulLocation = new Location("");
	    seoulLocation.setLatitude(37.541);
	    seoulLocation.setLongitude(126.986);
	    matjiListenerRef.get().onLocationChanged(startFromTag, seoulLocation);
	    // exception !!
	}
    }

    public void stop() {
	runningFlag = false;
	locationManager.removeUpdates(this);
    }

    public boolean isRunning() {
	return runningFlag;
    }

    public void onProviderDisabled(String provider) {
	if (provider.equals(majorProvider))
	    startLocationSettingsActivity();
    }

    public void onProviderEnabled(String provider) {
	if (provider.equals(majorProvider))
	    matjiListenerRef.get().onLocationExceptionDelivered(startFromTag, new GpsEnabledMatjiException());
    }
    
    public void onStatusChanged(String provider, int status, Bundle extras) {
	if (provider.equals(majorProvider)) {
	    switch(status) {
	    case LocationProvider.OUT_OF_SERVICE:
		matjiListenerRef.get().onLocationExceptionDelivered(startFromTag, new GpsOutOfServiceMatjiException());
		break;
	    case LocationProvider.TEMPORARILY_UNAVAILABLE:
		matjiListenerRef.get().onLocationExceptionDelivered(startFromTag, new GpsTemporarilyUnavailableMatjiException());
		break;
	    case LocationProvider.AVAILABLE:
		matjiListenerRef.get().onLocationExceptionDelivered(startFromTag, new GpsAvailableMatjiException());
		break;
	    }
	}
    }

    public void onLocationChanged(Location location) {
    	Log.d("GPS", location.getProvider().toString() + " ; Accuracy is " +location.getAccuracy());
	if (!gpsPerformed && location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
	    gpsPerformed = true;
	    // Log.d("LOCATION_CHANGED", "gps_provider");
	    locationManager.removeUpdates(this);
	    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
						   GPS_PROVIDER_MIN_NOTIFICATION_INTERVAL, minDistanceForNotifyInMeters, this);
	} else if (location.getProvider().equals(LocationManager.NETWORK_PROVIDER)) {
	    // Log.d("LOCATION_CHANGED", "network_provider");
	    matjiListenerRef.get().onLocationExceptionDelivered(startFromTag, new UseNetworkGpsMatjiException());
	}
	// Log.d("LOCATION_CHANGED", "final");
	notifyLocationChanged(location);
    }

    private void startLocationSettingsActivity() {
	Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	intent.addCategory(Intent.CATEGORY_DEFAULT);
	contextRef.get().startActivity(intent);
    }
}