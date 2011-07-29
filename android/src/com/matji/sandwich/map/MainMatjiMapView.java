package com.matji.sandwich.map;

import android.content.Context;
import android.widget.TextView;
import android.location.Location;
import android.view.View;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.util.AttributeSet;

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
import com.matji.sandwich.session.Session;
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
    private static final GeocodeHttpRequest.Country country = GeocodeHttpRequest.Country.KOREA;
    private static final int LAT_SPAN = (int)(0.005 * 1E6);
    private static final int LNG_SPAN = (int)(0.005 * 1E6);
    private static final int MAX_STORE_COUNT = 60;
    private static final int NEARBY_STORE = 1;
    private static final int GEOCODE = 2;
    private MapController mapController;
    private Context context;
    private TextView addressView;
    private BaseMapActivity activity;
    private HttpRequestManager requestManager;
    private StoreItemizedOverlay storeItemizedOverlay;
    private Session session;
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
	session = Session.getInstance(context);
	gpsManager = new GpsManager(context, this);

	mapController.zoomToSpan(LAT_SPAN, LNG_SPAN);
	gpsManager.start(1);
    }

    public void setCenter(Location location) {
	mapController.animateTo(new LocationToGeoPointAdapter(location));
    }

    public void onMapCenterChanged(GeoPoint point) {
	setBoundToSession();
	setCenterToSession(point);

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
									   getBound(BoundType.MAP_BOUND_NE),
									   getBound(BoundType.MAP_BOUND_SW));
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
	setCenter(prevLocation);
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

    private void setBoundToSession() {
	GeoPoint neBound = getBound(BoundType.MAP_BOUND_NE);
	GeoPoint swBound = getBound(BoundType.MAP_BOUND_SW);

	session.getPreferenceProvider().setInt(Session.MAP_BOUND_LATITUDE_NE, neBound.getLatitudeE6());
	session.getPreferenceProvider().setInt(Session.MAP_BOUND_LATITUDE_SW, swBound.getLatitudeE6());
	session.getPreferenceProvider().setInt(Session.MAP_BOUND_LONGITUDE_NE, neBound.getLongitudeE6());
	session.getPreferenceProvider().setInt(Session.MAP_BOUND_LONGITUDE_SW, swBound.getLongitudeE6());
    }

    private void setCenterToSession(GeoPoint centerPoint) {
	session.getPreferenceProvider().setInt(Session.MAP_BOUND_CENTER_LATITUDE, centerPoint.getLatitudeE6());
	session.getPreferenceProvider().setInt(Session.MAP_BOUND_CENTER_LONGITUDE, centerPoint.getLongitudeE6());
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
	    geocodeRequest.actionReverseGeocodingByGeoPoint(getMapCenter(), country);
	    requestManager.request(activity, request, NEARBY_STORE, requestable);
	    requestManager.request(activity, geocodeRequest, GEOCODE, requestable);
	}
    }
}