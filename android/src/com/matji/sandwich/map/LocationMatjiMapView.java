package com.matji.sandwich.map;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.location.Location;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;

import com.matji.sandwich.R;
import com.matji.sandwich.Requestable;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.GeocodeAddress;
import com.matji.sandwich.widget.SimpleSubmitLocationBar;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.spinner.SpinnerFactory;
import com.matji.sandwich.http.request.GeocodeHttpRequest;
import com.matji.sandwich.util.GeocodeUtil;
import com.matji.sandwich.util.adapter.LocationToGeoPointAdapter;
import com.matji.sandwich.session.SessionMapUtil;
import com.matji.sandwich.location.GpsManager;
import com.matji.sandwich.location.MatjiLocationListener;

import java.util.ArrayList;

public class LocationMatjiMapView extends RelativeLayout implements MatjiMapViewInterface,
SimpleSubmitLocationBar.OnClickListener,
MatjiMapCenterListener,
Requestable,
MatjiLocationListener {
	private static final int GPS_START_TAG = 0;
	private static final int REQUEST_REVERSE_GEOCODING = 0;
	private Context context;
	private Activity activity;
	private MatjiMapView mapView;
	private MapController mapController;
	private SimpleSubmitLocationBar locationBar;
	private OnClickListener clickListener;
	private GeocodeRunnable geocodeRunnable;
	private HttpRequestManager requestManager;
	private GeoPoint lastNEBound;
	private GeoPoint lastSWBound;
	private Location prevLocation;
	private SessionMapUtil sessionMapUtil;
	private GpsManager gpsManager;

	public LocationMatjiMapView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

		LayoutInflater.from(context).inflate(R.layout.location_matji_mapview, this, true);
		mapView = (MatjiMapView)findViewById(R.id.location_matji_mapview_map);
		locationBar = (SimpleSubmitLocationBar)findViewById(R.id.location_matji_mapview_location_bar);
		geocodeRunnable = new GeocodeRunnable(locationBar.getSpinnerContainer(), context, this);
		requestManager = HttpRequestManager.getInstance();
		sessionMapUtil = new SessionMapUtil(context);
		gpsManager = new GpsManager(context, this);

		mapController = mapView.getController();
		locationBar.setOnClickListener(this);
		setMapCenterListener(this);

		gpsManager.start(GPS_START_TAG, null);
	}

	public void init(Activity activity) {
		this.activity = activity;
	}

	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		switch(tag) {
		case REQUEST_REVERSE_GEOCODING:
			GeocodeAddress geocodeAddress = GeocodeUtil.approximateAddress(data, lastNEBound, lastSWBound);
			locationBar.setAddress(geocodeAddress.getShortenFormattedAddress());
		}
	}

	public void onLocationChanged(int startedFromTag, Location location) {
		if (prevLocation != null) {
			if (prevLocation.getAccuracy() >= location.getAccuracy()) {
				gpsManager.stop();
			}
		}
		prevLocation = location;
		setCenter(new LocationToGeoPointAdapter(location));
	}

	public void onLocationExceptionDelivered(int startedFromTag, MatjiException e) {
		e.performExceptionHandling(context);
	}

	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.performExceptionHandling(context);
	}

	public void onMapCenterChanged(GeoPoint point) {
		lastNEBound = getBound(BoundType.MAP_BOUND_NE);
		lastSWBound = getBound(BoundType.MAP_BOUND_SW);
		geocodeRunnable.setCenter(point);
		activity.runOnUiThread(geocodeRunnable);
	}

	public void setCenter(int lat, int lng) {
		setCenter(new GeoPoint(lat, lng));
	}

	public void setCenter(GeoPoint point) {
		mapController.zoomToSpan(sessionMapUtil.getBasicLatSpan(), sessionMapUtil.getBasicLngSpan());
		mapController.animateTo(point);
	}

	public void setOnClickListener(OnClickListener clickListener) {
		this.clickListener = clickListener;
	}

	public void startMapCenterThread() {
		mapView.startMapCenterThread();
	}

	public void stopMapCenterThread() {
		mapView.stopMapCenterThread();
	}

	public void setMapCenterListener(MatjiMapCenterListener listener) {
		mapView.setMapCenterListener(listener);
	}

	public GeoPoint getBound(BoundType type) {
		return mapView.getBound(type);
	}

	public MapController getController() {
		return mapController;
	}

	public int getLatitudeSpan() {
		return mapView.getLatitudeSpan();
	}

	public int getLongitudeSpan() {
		return mapView.getLongitudeSpan();
	}

	public void onSubmitClick() {
		if (clickListener != null)
			clickListener.onSubmitClick(this, lastNEBound, lastSWBound);
	}

	public void onLocationClick() {
		gpsManager.start(GPS_START_TAG, null);
	}
	
	public void stop() {
		gpsManager.stop();
	}

	private class GeocodeRunnable implements Runnable {
		private Context context;
		private Requestable requestable;
		private GeoPoint center;
		private ViewGroup spinnerContainer;

		public GeocodeRunnable(ViewGroup spinnerContainer, Context context, Requestable requestable) {
			this.context = context;
			this.requestable = requestable;
			this.spinnerContainer = spinnerContainer;
		}

		public void setCenter(GeoPoint center) {
			this.center = center;
		}

		public void run() {
			GeocodeHttpRequest geocodeRequest = new GeocodeHttpRequest(context);
			geocodeRequest.actionReverseGeocodingByGeoPoint(center, sessionMapUtil.getCurrentCountry());
			requestManager.request(context, spinnerContainer, SpinnerFactory.SpinnerType.SMALL, geocodeRequest, REQUEST_REVERSE_GEOCODING, requestable);
		}
	}

	public interface OnClickListener {
		public void onSubmitClick(View v, GeoPoint neBound, GeoPoint swBound);
	}
}