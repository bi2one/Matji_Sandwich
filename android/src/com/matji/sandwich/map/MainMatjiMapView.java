package com.matji.sandwich.map;

import android.content.Context;
import android.widget.TextView;
import android.location.Location;
import android.view.View;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.util.AttributeSet;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;

import com.matji.sandwich.Requestable;
import com.matji.sandwich.base.BaseMapActivity;
import com.matji.sandwich.util.GeocodeUtil;
import com.matji.sandwich.util.adapter.LocationToGeoPointAdapter;
import com.matji.sandwich.data.GeocodeAddress;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.CoordinateRegion;
import com.matji.sandwich.session.SessionMapUtil;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.GeocodeHttpRequest;
import com.matji.sandwich.http.request.StoreHttpRequest;
import com.matji.sandwich.overlay.StoreItemizedOverlay;
import com.matji.sandwich.location.GpsManager;
import com.matji.sandwich.location.MatjiLocationListener;
import com.matji.sandwich.exception.MatjiException;

import java.util.ArrayList;
import java.util.Collections;

public class MainMatjiMapView extends MatjiMapView implements MatjiMapCenterListener,
							      MatjiLocationListener,
							      Requestable,
							      OnTouchListener {
    private static final int LAT_SPAN = (int)(0.005 * 1E6);
    private static final int LNG_SPAN = (int)(0.005 * 1E6);
    private static final int MAX_STORE_COUNT = 60;
    private static final int GPS_START_TAG = 1;
    private static final int NEARBY_STORE = 1;
    private static final int GEOCODE = 2;
    private MapController mapController;
    private Context context;
    private TextView addressView;
    private BaseMapActivity activity;
    private HttpRequestManager requestManager;
    private StoreItemizedOverlay storeItemizedOverlay;
    private SessionMapUtil sessionUtil;
    private ArrayList<MatjiData> stores;
    private GpsManager gpsManager;
    private Location prevLocation;
    
    public MainMatjiMapView(Context context, AttributeSet attrs) {
	super(context, attrs);
	this.context = context;
	setMapCenterListener(this);
	setOnTouchListener(this);
    }

    public void init(TextView addressView, BaseMapActivity activity) {
	setAddressView(addressView);
	setBaseMapActivity(activity);

	storeItemizedOverlay = new StoreItemizedOverlay(context, activity, this);
	mapController = getController();
	requestManager = HttpRequestManager.getInstance(context);
	sessionUtil = new SessionMapUtil(context);
	gpsManager = new GpsManager(context, this);

	mapController.zoomToSpan(LAT_SPAN, LNG_SPAN);
	gpsManager.start(GPS_START_TAG);
    }

    public void moveToGpsCenter() {
	gpsManager.start(GPS_START_TAG);
    }

    public void setCenter(Location location) {
	setCenter(new LocationToGeoPointAdapter(location));
    }

    public void setCenter(GeoPoint point) {
	mapController.animateTo(point);
    }

    public void setCenterNotAnimate(GeoPoint point) {
	mapController.setCenter(point);
    }
    
    public void onMapCenterChanged(GeoPoint point) {
	sessionUtil.setBound(getBound(BoundType.MAP_BOUND_NE),
			     getBound(BoundType.MAP_BOUND_SW));
	sessionUtil.setCenter(point);

	Runnable runnable = new MapRunnable(this);
	activity.runOnUiThread(runnable);
    }

    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
	switch (tag) {
	case NEARBY_STORE:
	    stores = data;
	    Collections.reverse(stores);
	    drawOverlays();
	    break;
	case GEOCODE:
	    GeocodeAddress geocodeAddress = GeocodeUtil.approximateAddress(data,
									   sessionUtil.getNEBound(),
									   sessionUtil.getSWBound());
	    addressView.setText(geocodeAddress.getShortenFormattedAddress());
	}
    }

    public void onLocationChanged(int startedFromTag, Location location) {
	if (prevLocation != null) {
	    if (prevLocation.getAccuracy() >= location.getAccuracy()) {
		gpsManager.stop();
	    }
	}

	prevLocation = location;
	sessionUtil.setNearBound(new LocationToGeoPointAdapter(location));
	mapController.zoomToSpan(sessionUtil.getBasicLatSpan(), sessionUtil.getBasicLngSpan());
	setCenter(location);
    }

    public void onLocationExceptionDelivered(int startedFromTag, MatjiException e) {
	e.performExceptionHandling(context);
    }

    public void requestExceptionCallBack(int tag, MatjiException e) {
	e.performExceptionHandling(context);
    }

    public boolean onTouch(View v, MotionEvent e) {
	switch(e.getAction()) {
	case MotionEvent.ACTION_DOWN:
	    gpsManager.stop();
	    requestManager.turnOff();
	    break;
	case MotionEvent.ACTION_UP:
	    requestManager.turnOn();
	}
	return false;
    }

    private void drawOverlays() {
	getOverlays().clear();
	storeItemizedOverlay.getOverlayItems().clear();

	for (MatjiData storeData : stores) {
	    Store store = (Store)storeData;

	    storeItemizedOverlay.addOverlay(store);
	}
	storeItemizedOverlay.mPopulate();
	postInvalidate();
    }

    private void setAddressView(TextView addressView) {
	this.addressView = addressView;
    }

    private void setBaseMapActivity(BaseMapActivity activity) {
	this.activity = activity;
    }

    public class MapRunnable implements Runnable{
	private Requestable requestable;

	public MapRunnable(Requestable requestable) {
	    this.requestable = requestable;
	}

	public void run() {
	    loadStoresOnMap();
	}

	private void loadStoresOnMap(){
	    StoreHttpRequest request = new StoreHttpRequest(context);
	    GeocodeHttpRequest geocodeRequest = new GeocodeHttpRequest(context);
	    
	    CoordinateRegion cr = new CoordinateRegion(getMapCenter(), getLatitudeSpan(), getLongitudeSpan());
	    GeoPoint swPoint = cr.getSWGeoPoint();
	    GeoPoint nePoint = cr.getNEGeoPoint();

	    double lat_sw = (double)(swPoint.getLatitudeE6()) / (double)1E6;
	    double lng_sw = (double)(swPoint.getLongitudeE6()) / (double)1E6;
	    double lat_ne = (double)(nePoint.getLatitudeE6()) / (double)1E6;
	    double lng_ne = (double)(nePoint.getLongitudeE6()) / (double)1E6;

	    request.actionNearbyList(lat_sw, lat_ne, lng_sw, lng_ne, 1, MAX_STORE_COUNT);
	    geocodeRequest.actionReverseGeocodingByGeoPoint(getMapCenter(), sessionUtil.getCurrentCountry());
	    requestManager.request(activity, request, NEARBY_STORE, requestable);
	    requestManager.request(activity, geocodeRequest, GEOCODE, requestable);
	}
    }
}