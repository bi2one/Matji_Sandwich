package com.matji.sandwich.map;

import java.util.ArrayList;
import java.util.Collections;
import java.lang.ref.WeakReference;

import android.content.Context;
import android.location.Location;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.RelativeLayout;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.matji.sandwich.Requestable;
import com.matji.sandwich.base.BaseMapActivity;
import com.matji.sandwich.data.CoordinateRegion;
import com.matji.sandwich.data.GeocodeAddress;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.GeocodeHttpRequest;
import com.matji.sandwich.http.request.StoreHttpRequest;
import com.matji.sandwich.http.spinner.SpinnerFactory.SpinnerType;
import com.matji.sandwich.location.GpsManager;
import com.matji.sandwich.location.MatjiLocationListener;
import com.matji.sandwich.overlay.StoreItemizedOverlay;
import com.matji.sandwich.overlay.OverlayClickListener;
import com.matji.sandwich.session.SessionMapUtil;
import com.matji.sandwich.util.GeocodeUtil;
import com.matji.sandwich.util.adapter.LocationToGeoPointAdapter;

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
    private RelativeLayout addressWrapper;
    private WeakReference<BaseMapActivity> activityRef;
    private HttpRequestManager requestManager;
    private StoreItemizedOverlay storeItemizedOverlay;
    private SessionMapUtil sessionUtil;
    private ArrayList<MatjiData> stores;
    private GpsManager gpsManager;
    private Location prevLocation;
    private ViewGroup spinnerLayout;
    
    public MainMatjiMapView(Context context, AttributeSet attrs) {
	super(context, attrs);
	this.context = context;
	setMapCenterListener(this);
	setOnTouchListener(this);
    }

    public void init(RelativeLayout addressWrapper, TextView addressView, BaseMapActivity activity, ViewGroup spinnerLayout) {
	setAddressView(addressView);
	setBaseMapActivity(activity);

	this.spinnerLayout = spinnerLayout;
	this.addressWrapper = addressWrapper;
	storeItemizedOverlay = new StoreItemizedOverlay(context, this);
	mapController = getController();
	// requestManager = HttpRequestManager.getInstance(context);
	requestManager = HttpRequestManager.getInstance();
	sessionUtil = new SessionMapUtil(context);
	gpsManager = new GpsManager(context, this);

	mapController.zoomToSpan(LAT_SPAN, LNG_SPAN);
	moveToGpsCenter();
    }

    public void setOverlayClickListener(OverlayClickListener listener) {
	storeItemizedOverlay.setOverlayClickListener(listener);
    }

    public void reload() {
	Runnable runnable = new MapRunnable(this);
	activityRef.get().runOnUiThread(runnable);
	setCenter(sessionUtil.getCenter());
	startMapCenterThreadNotFirstLoading();
    }

    public void moveToGpsCenter() {
	stopMapCenterThread();
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

    public void setCenterNotAnimate(Location location) {
	setCenterNotAnimate(new LocationToGeoPointAdapter(location));
    }
    
    public void onMapCenterChanged(GeoPoint point) {
	sessionUtil.setBound(getBound(BoundType.MAP_BOUND_NE),
			     getBound(BoundType.MAP_BOUND_SW));
	sessionUtil.setCenter(point);

	Runnable runnable = new MapRunnable(this);
	activityRef.get().runOnUiThread(runnable);
    }

    public void updatePopupOverlay(Store store) {
	storeItemizedOverlay.updateLastPopupOverlay(store);
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
		startMapCenterThread(new LocationToGeoPointAdapter(location));
		gpsManager.stop();
	    }
	}

	sessionUtil.setNearBound(new LocationToGeoPointAdapter(location));
	mapController.zoomToSpan(sessionUtil.getBasicLatSpan(), sessionUtil.getBasicLngSpan());
	
	if (prevLocation == null) {
	    setCenterNotAnimate(location);
	} else {
	    setCenter(location);
	}
	prevLocation = location;
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
	    startMapCenterThread();
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
	this.activityRef = new WeakReference(activity);
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
	    geocodeRequest.actionReverseGeocodingByGeoPoint(sessionUtil.getCenter(), sessionUtil.getCurrentCountry());
	    requestManager.request(getContext(), addressWrapper, SpinnerType.SMALL, geocodeRequest, GEOCODE, requestable);
	    requestManager.request(getContext(), spinnerLayout, SpinnerType.NORMAL, request, NEARBY_STORE, requestable);
	    // try {
	    // 	Log.d("=====", "request start");
	    // 	requestCallBack(GEOCODE, geocodeRequest.request());
	    // 	requestCallBack(NEARBY_STORE, request.request());
	    // 	Log.d("=====", "request end");
	    // } catch(MatjiException e) {
	    // 	e.performExceptionHandling(context);
	    // }
	}
    }
}