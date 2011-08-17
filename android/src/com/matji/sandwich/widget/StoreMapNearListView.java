package com.matji.sandwich.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.location.Location;
import android.os.Parcelable;
import android.widget.TextView;
import android.view.View;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;

import com.matji.sandwich.MainMapActivity;
import com.matji.sandwich.StoreMainActivity;
import com.matji.sandwich.Requestable;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.util.GeocodeUtil;
import com.matji.sandwich.util.adapter.LocationToGeoPointAdapter;
import com.matji.sandwich.util.adapter.GeoPointToLocationAdapter;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.GeocodeAddress;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.RequestCommand;
import com.matji.sandwich.http.request.GeocodeHttpRequest;
import com.matji.sandwich.http.request.NearBookmarkStoreRequest;
import com.matji.sandwich.session.SessionMapUtil;
import com.matji.sandwich.location.GpsManager;
import com.matji.sandwich.location.MatjiLocationListener;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.adapter.MapStoreSectionedAdapter;

import com.google.android.maps.GeoPoint;

import java.util.ArrayList;

public class StoreMapNearListView extends RequestableMListView implements MatjiLocationListener,
									  OnTouchListener,
									  Requestable {
    private static final GeocodeHttpRequest.Country COUNTRY = GeocodeHttpRequest.Country.KOREA;
    private static final int LAT_HALVE_SPAN = (int)(0.005 * 1E6) / 2;
    private static final int LNG_HALVE_SPAN = (int)(0.005 * 1E6) / 2;
    private static final int GPS_START_TAG = 1;
    private static final int GEOCODE = 1001;
    private SessionMapUtil sessionUtil;
    private GpsManager gpsManager;
    private Location prevLocation;
    private Context context;
    private MainMapActivity activity;
    private TextView addressView;
    private HttpRequestManager requestManager;
    private NearBookmarkStoreRequest storeRequest;
    private MapStoreSectionedAdapter adapter;

    public StoreMapNearListView(Context context, AttributeSet attrs) {
	super(context, attrs, new MapStoreSectionedAdapter(context), 10);
	this.context = context;
	sessionUtil = new SessionMapUtil(context);
	storeRequest = new NearBookmarkStoreRequest(context, 10);
	gpsManager = new GpsManager(context, this);
	requestManager = HttpRequestManager.getInstance(context);
	setOnTouchListener(this);
	setPage(1);
	adapter = (MapStoreSectionedAdapter)getMBaseAdapter();
	adapter.init(storeRequest);
    }

    public void init(TextView addressView, MainMapActivity activity) {
	setActivity(activity);
	this.addressView = addressView;
	this.activity = activity;
    }

    public boolean onTouch(View v, MotionEvent e) {
	switch(e.getAction()) {
	case MotionEvent.ACTION_DOWN:
	    gpsManager.stop();
	    break;
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

    public RequestCommand request() {
	GeoPoint neBound = sessionUtil.getNEBound();
	GeoPoint swBound = sessionUtil.getSWBound();
	
	storeRequest.actionCurrentUserNearBookmarkedList((double) swBound.getLatitudeE6() / 1E6,
							 (double) neBound.getLatitudeE6() / 1E6,
							 (double) swBound.getLongitudeE6() / 1E6,
							 (double) neBound.getLongitudeE6() / 1E6,
							 getPage());
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

    public void setCenter(GeoPoint point) {
	setCenter(new GeoPointToLocationAdapter(point));
    }

    public void setCenter(Location location) {
	GeoPoint locationPoint = new LocationToGeoPointAdapter(location);
	GeocodeHttpRequest geocodeRequest = new GeocodeHttpRequest(context);
	
	sessionUtil.setCenter(locationPoint);
	sessionUtil.setNearBound(locationPoint);
	geocodeRequest.actionReverseGeocodingByGeoPoint(locationPoint, COUNTRY);
	requestManager.cancelTask();
	requestManager.request(activity, geocodeRequest, GEOCODE, this);
    }

    public void onListItemClick(int position) {
    	// activity.setIsFlow(true);
    	// Store store = (Store) getAdapterData().get(position);
    	// Intent intent = new Intent(getActivity(), StoreMainActivity.class);
    	// intent.putExtra(StoreMainActivity.STORE, (Parcelable) store);
    	// getActivity().startActivity(intent);
    }
}