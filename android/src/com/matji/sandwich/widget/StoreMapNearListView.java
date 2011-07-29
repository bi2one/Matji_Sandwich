package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.location.Location;

import com.matji.sandwich.data.provider.PreferenceProvider;
import com.matji.sandwich.http.request.StoreHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.location.GpsManager;
import com.matji.sandwich.location.MatjiLocationListener;
import com.matji.sandwich.exception.MatjiException;

public class StoreMapNearListView extends StoreListView implements MatjiLocationListener {
    private static final int BASIC_LATITUDE_SW = 37000000;
    private static final int BASIC_LATITUDE_NE = 126600000;
    private static final int BASIC_LONGITUDE_SW = 37400000;
    private static final int BASIC_LONGITUDE_NE = 126900000;
    private static final int LAT_HALVE_SPAN = (int)(0.005 * 1E6) / 2;
    private static final int LNG_HALVE_SPAN = (int)(0.005 * 1E6) / 2;
    private static final int GPS_START_TAG = 1;
    private StoreHttpRequest storeRequest;
    private int latSW;
    private int latNE;
    private int lngSW;
    private int lngNE;
    private PreferenceProvider provider;
    private Session session;
    private GpsManager gpsManager;
    private Location prevLocation;
    private Context context;

    public StoreMapNearListView(Context context, AttributeSet attrs) {
	super(context, attrs);
	this.context = context;
	storeRequest = new StoreHttpRequest(context);
	session = Session.getInstance(context);
	provider = session.getPreferenceProvider();
	gpsManager = new GpsManager(context, this);
    }

    public void fillVariables() {
	latSW = provider.getInt(Session.MAP_BOUND_LATITUDE_SW, BASIC_LATITUDE_SW);
	latNE = provider.getInt(Session.MAP_BOUND_LATITUDE_NE, BASIC_LATITUDE_NE);
	lngSW = provider.getInt(Session.MAP_BOUND_LONGITUDE_SW, BASIC_LONGITUDE_SW);
	lngNE = provider.getInt(Session.MAP_BOUND_LONGITUDE_NE, BASIC_LONGITUDE_NE);
    }

    public void fillVariablesFromCenterLocation(Location centerLocation) {
	// GeoPoint centerPoint = 
    }

    public void moveToGpsCenter() {
	gpsManager.start(GPS_START_TAG);
    }

    public void onLocationChanged(int startedFromTag, Location location) {
	if (prevLocation != null) {
	    if (prevLocation.getAccuracy() >= location.getAccuracy()) {
		gpsManager.stop();
	    }
	}

	prevLocation = location;
    }

    public void onLocationExceptionDelivered(int startedFromTag, MatjiException e) {
	e.performExceptionHandling(context);
    }

    public void requestReload() {
	fillVariables();
	super.requestReload();
    }

    public HttpRequest request() {
	storeRequest.actionNearbyList((double) latSW / 1E6,
				      (double) latNE / 1E6,
				      (double) lngSW / 1E6,
				      (double) lngNE / 1E6,
				      getPage(), getLimit());
	return storeRequest;
    }
}