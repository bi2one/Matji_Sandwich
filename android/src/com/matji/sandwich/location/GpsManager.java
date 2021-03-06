package com.matji.sandwich.location;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;

import com.matji.sandwich.R;
import com.matji.sandwich.exception.GpsOutOfServiceMatjiException;
import com.matji.sandwich.exception.GpsTemporarilyUnavailableMatjiException;
import com.matji.sandwich.exception.GpsAvailableMatjiException;
import com.matji.sandwich.exception.GpsEnabledMatjiException;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.UseNetworkGpsMatjiException;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.session.SessionPrivateUtil;
import com.matji.sandwich.widget.dialog.SimpleDialog;
import com.matji.sandwich.widget.dialog.SimpleConfirmDialog;
import java.lang.ref.WeakReference;

public class GpsManager implements LocationListener, SimpleConfirmDialog.OnClickListener {
	private final static long NET_PROVIDER_MIN_NOTIFICATION_INTERVAL = 100; /* 1000 * 60 * 5; */
	private final static long GPS_PROVIDER_MIN_NOTIFICATION_INTERVAL = 50;      /* 1000 * 5; */
	private final static int minDistanceForNotifyInMeters = 0;
	private WeakReference<StartConfigListener> startConfigListenerRef;
	private WeakReference<MatjiLocationListener> matjiListenerRef;
	private SessionPrivateUtil preferenceProvider;
	private SimpleConfirmDialog networkDialog;
	private WeakReference<Context> contextRef;
	private LocationManager locationManager;
	private String majorProvider;
	private boolean gpsPerformed;
	private boolean runningFlag;
	private int startFromTag;

	public GpsManager(final Context context, MatjiLocationListener matjiListener) {
		this.contextRef = new WeakReference(context);
		this.matjiListenerRef = new WeakReference(matjiListener);
		locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		preferenceProvider = Session.getInstance(context).getPrivateUtil();
		gpsPerformed = false;
		runningFlag = false;
	}

	public void setStartConfigListener(StartConfigListener startConfigListener) {
		this.startConfigListenerRef = new WeakReference(startConfigListener);
	}

	public void start(final int startFromTag, final Activity activity) {
		if (activity != null && !preferenceProvider.getUserAgree()) {
			networkDialog = new SimpleConfirmDialog(activity, R.string.location_info_agreement);
			networkDialog.show();
			networkDialog.setOnClickListener(new SimpleConfirmDialog.OnClickListener() {
				@Override
				public void onConfirmClick(SimpleDialog dialog) {
					preferenceProvider.setUserAgree(true);
					notifyLastKnownLocation();
					startWithoutLastLocation(startFromTag);
				}
				@Override
				public void onCancelClick(SimpleDialog dialog) {
					stop();
					networkDialog.cancel();
				}
			});
		} else {
			notifyLastKnownLocation();
			startWithoutLastLocation(startFromTag);
		}
	}

	public void startWithoutLastLocation(int startFromTag) {
		this.startFromTag = startFromTag;
		runningFlag = true;
		try {
			if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
				Log.d("GPS", "start with gps1");
				requestLocationUpdatesGps();
			} else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
				Log.d("GPS", "start with network1");
				requestLocationUpdatesNetwork();
			} else {
				Log.d("GPS", "start with ????");
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

		if (gpsLoc != null && netLoc != null) {
			if (gpsLoc.getTime() > netLoc.getTime())
				lastLoc = gpsLoc;
			else
				lastLoc = netLoc;
		} else if (gpsLoc != null) {
			lastLoc = gpsLoc;	
		} else if (netLoc != null) {
			lastLoc = netLoc;
		}

		if (lastLoc != null){
			lastLoc.setAccuracy(1000);
			notifyLocationChanged(lastLoc);
		}
	}

	private void requestLocationUpdatesGps() throws MatjiException {
		majorProvider = LocationManager.GPS_PROVIDER;
		Log.d("GPS", "start with gps2");
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				GPS_PROVIDER_MIN_NOTIFICATION_INTERVAL, minDistanceForNotifyInMeters, this);
		Log.d("GPS", "start with network2");
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
				NET_PROVIDER_MIN_NOTIFICATION_INTERVAL, minDistanceForNotifyInMeters, this);
	}

	private void requestLocationUpdatesNetwork() throws MatjiException {
		Log.d("GPS", "start with network3");
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
			seoulLocation.setLatitude(37.565100);
			seoulLocation.setLongitude(126.989550);
			matjiListenerRef.get().onLocationChanged(startFromTag, seoulLocation);
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
			locationManager.removeUpdates(this);
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
					GPS_PROVIDER_MIN_NOTIFICATION_INTERVAL, minDistanceForNotifyInMeters, this);
		} else if (location.getProvider().equals(LocationManager.NETWORK_PROVIDER)) {
			matjiListenerRef.get().onLocationExceptionDelivered(startFromTag, new UseNetworkGpsMatjiException());
		}
		notifyLocationChanged(location);
	}

	private void startLocationSettingsActivity() {
		SimpleConfirmDialog dialog = new SimpleConfirmDialog(contextRef.get(), R.string.gps_request_turn_on);
		dialog.setOnClickListener(this);
		dialog.show();
	}

	public void onConfirmClick(SimpleDialog dialog) {
		if (startConfigListenerRef != null && startConfigListenerRef.get() != null)
			startConfigListenerRef.get().onStartConfig(this);

		Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		contextRef.get().startActivity(intent);
	}

	public void onCancelClick(SimpleDialog dialog) { }

	public interface StartConfigListener {
		public void onStartConfig(GpsManager manager);
	}
}