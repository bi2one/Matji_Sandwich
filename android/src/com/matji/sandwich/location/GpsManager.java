package com.matji.sandwich.location;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.matji.sandwich.R;

import com.matji.sandwich.exception.GpsOutOfServiceMatjiException;
import com.matji.sandwich.exception.GpsTemporarilyUnavailableMatjiException;
import com.matji.sandwich.exception.GpsAvailableMatjiException;
import com.matji.sandwich.exception.GpsEnabledMatjiException;
import com.matji.sandwich.exception.InterruptedMatjiException;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.UseNetworkGpsMatjiException;
import com.matji.sandwich.exception.GpsServiceNotPossibleMatjiException;

public class GpsManager {
    private final static long NET_PROVIDER_MIN_NOTIFICATION_INTERVAL = 1000 * 5; /* 1000 * 60 * 5; */
    private final static long GPS_PROVIDER_MIN_NOTIFICATION_INTERVAL = 1000;      /* 1000 * 5; */
    private final static long GPS_PROVIDER_DURATION = 3000;
    
    private Context context;
    private Location currentLocation;
    private Location recentGpsLocation;
    private Location recentNetworkLocation;
    private MatjiLocationListener matjiListener;
    private LocationListener gpsListener;
    private LocationListener networkListener;
    private LocationManager locationManager;
    private String majorProvider;
    
    public GpsManager(Context context, MatjiLocationListener matjiListener) {
	this.context = context;
	this.matjiListener = matjiListener;
	gpsListener = new GpsLocationListener();
	networkListener = new NetworkLocationListener();
	locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
    }

    public void start() {
	try {
	    if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
		requestLocationUpdatesGpsNetwork();
	    } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
		requestLocationUpdatesNetwork();
	    } else {
		// show gps setting dialog
		startLocationSettingsAcvitity();
	    }
	} catch(MatjiException e) {
	    matjiListener.onLocationExceptionDelivered(e);
	}
    }

    private void requestLocationUpdatesGpsNetwork() throws MatjiException {
	majorProvider = LocationManager.GPS_PROVIDER;
	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
					       GPS_PROVIDER_MIN_NOTIFICATION_INTERVAL, 0, gpsListener);
	locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
					       NET_PROVIDER_MIN_NOTIFICATION_INTERVAL, 0, networkListener);
    }

    private void requestLocationUpdatesNetwork() throws MatjiException {
	majorProvider = LocationManager.NETWORK_PROVIDER;
	matjiListener.onLocationExceptionDelivered(new UseNetworkGpsMatjiException());
	locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
					       NET_PROVIDER_MIN_NOTIFICATION_INTERVAL, 0, networkListener);
    }

    public void stop() {
	locationManager.removeUpdates(gpsListener);
	locationManager.removeUpdates(networkListener);
	recentNetworkLocation = null;
	recentGpsLocation = null;
	currentLocation = null;
    }

    public void providerDisabled(String provider) {
	if (provider == majorProvider) {
	    startLocationSettingsAcvitity();
	}
    }

    public void providerEnabled(String provider) {
	if (provider == majorProvider) {
	    matjiListener.onLocationExceptionDelivered(new GpsEnabledMatjiException());
	}
    }
    
    public void statusChanged(String provider, int status, Bundle extras) {
	if (provider == majorProvider) {
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

    public void locationChanged(Location location) {
	if (currentLocation == null) {
	    matjiListener.onInitialLocationDelivered(location);
	}
	matjiListener.onLocationChanged(location);
	currentLocation = location;
    }

    public Location getLastUpdatedLocation() {
	// 이 함수를 이용할 수는 있지만 추천하지
	// 않는다. callback메소드를 이용한 gps이용을 권한다.
	return currentLocation;
    }

    private void startLocationSettingsAcvitity() {
	Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	intent.addCategory(Intent.CATEGORY_DEFAULT);
	context.startActivity(intent);
    }

    private class GpsLocationListener implements LocationListener {
	public void onLocationChanged(Location location) {
	    recentGpsLocation = location;
	    locationChanged(location);
	}

	public void onProviderDisabled(String provider) {
	    providerDisabled(provider);
	}

	public void onProviderEnabled(String provider) {
	    providerEnabled(provider);
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
	    statusChanged(provider, status, extras);
	}
    }

    private class NetworkLocationListener implements LocationListener {
	public void onLocationChanged(Location location) {
	    recentNetworkLocation = location;
	    
	    long now = System.currentTimeMillis();
	    if (recentGpsLocation == null ||
		recentGpsLocation.getTime() < now - GPS_PROVIDER_DURATION ||
		location.getLatitude() != currentLocation.getLatitude() ||
		location.getLongitude() != currentLocation.getLongitude()) {
		locationChanged(location);
	    }
	}

	public void onProviderDisabled(String provider) {
	    providerDisabled(provider);
	}

	public void onProviderEnabled(String provider) {
	    providerEnabled(provider);
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
	    statusChanged(provider, status, extras);
	}
    }
}