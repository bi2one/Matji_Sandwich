package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.location.Location;
import android.widget.TextView;
import android.view.View;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;

import com.matji.sandwich.Requestable;
import com.matji.sandwich.base.BaseMapActivity;
import com.matji.sandwich.util.GeocodeUtil;
import com.matji.sandwich.util.adapter.LocationToGeoPointAdapter;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.GeocodeAddress;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.GeocodeHttpRequest;
import com.matji.sandwich.http.request.StoreHttpRequest;
import com.matji.sandwich.session.SessionMapUtil;
import com.matji.sandwich.location.GpsManager;
import com.matji.sandwich.location.MatjiLocationListener;
import com.matji.sandwich.exception.MatjiException;

import com.google.android.maps.GeoPoint;

import java.util.ArrayList;

public class StoreMapNearListView extends StoreListView implements MatjiLocationListener,
								   OnTouchListener,
								   Requestable {
    private static final GeocodeHttpRequest.Country COUNTRY = GeocodeHttpRequest.Country.KOREA;
    private static final int LAT_HALVE_SPAN = (int)(0.005 * 1E6) / 2;
    private static final int LNG_HALVE_SPAN = (int)(0.005 * 1E6) / 2;
    private static final int GPS_START_TAG = 1;
    private static final int GEOCODE = 1001;
    private StoreHttpRequest storeRequest;
    private SessionMapUtil sessionUtil;
    private GpsManager gpsManager;
    private Location prevLocation;
    private Context context;
    private BaseMapActivity activity;
    private TextView addressView;
    private HttpRequestManager requestManager;

    public StoreMapNearListView(Context context, AttributeSet attrs) {
	super(context, attrs);
	this.context = context;
	sessionUtil = new SessionMapUtil(context);
	storeRequest = new StoreHttpRequest(context);
	gpsManager = new GpsManager(context, this);
	requestManager = HttpRequestManager.getInstance(context);
	setOnTouchListener(this);
    }

    public void init(TextView addressView, BaseMapActivity activity) {
	setActivity(activity);
	this.addressView = addressView;
	this.activity = activity;
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

    public void moveToGpsCenter() {
	gpsManager.start(GPS_START_TAG);
    }

    public void onLocationChanged(int startedFromTag, Location location) {
	if (prevLocation != null) {
	    if (prevLocation.getAccuracy() >= location.getAccuracy()) {
		sessionUtil.setNearBound(new LocationToGeoPointAdapter(location));
		setCenter(location);
		gpsManager.stop();
	    }
	}
	
	prevLocation = location;
    }

    public void onLocationExceptionDelivered(int startedFromTag, MatjiException e) {
	e.performExceptionHandling(context);
    }

    public HttpRequest request() {
	GeoPoint neBound = sessionUtil.getNEBound();
	GeoPoint swBound = sessionUtil.getSWBound();
	
	storeRequest.actionNearbyList((double) swBound.getLatitudeE6() / 1E6,
				      (double) neBound.getLatitudeE6() / 1E6,
				      (double) swBound.getLongitudeE6() / 1E6,
				      (double) neBound.getLongitudeE6() / 1E6,
				      getPage(), getLimit());
	return storeRequest;
    }

    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
	super.requestCallBack(tag, data);
	switch (tag) {
	case GEOCODE:
	    GeocodeAddress geocodeAddress = GeocodeUtil.approximateAddress(data,
									   sessionUtil.getNEBound(),
									   sessionUtil.getSWBound());
	    addressView.setText(geocodeAddress.getShortenFormattedAddress());
	    forceReload();
	}
    }

    public void requestExceptionCallBack(int tag, MatjiException e) {
	e.performExceptionHandling(context);
    }

    private void setCenter(Location location) {
	GeocodeHttpRequest geocodeRequest = new GeocodeHttpRequest(context);
	sessionUtil.setCenter(new LocationToGeoPointAdapter(location));
	geocodeRequest.actionReverseGeocodingByGeoPoint(new LocationToGeoPointAdapter(location), COUNTRY);
	requestManager.cancelTask();
	requestManager.request(activity, geocodeRequest, GEOCODE, this);
    }
}