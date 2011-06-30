package com.matji.sandwich;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;

import android.content.Intent;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import com.matji.sandwich.base.BaseMapActivity;
import com.matji.sandwich.map.MatjiMapView;
import com.matji.sandwich.map.MatjiMapCenterListener;
import com.matji.sandwich.location.MatjiLocationListener;
import com.matji.sandwich.location.GpsManager;
import com.matji.sandwich.http.request.GeocodeHttpRequest;
import com.matji.sandwich.overlay.CenterOverlay;
import com.matji.sandwich.data.AddressMatjiData;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.exception.MatjiException;

public class GetMapPositionActivity extends BaseMapActivity implements MatjiLocationListener,
								       MatjiMapCenterListener,
								       Requestable {
    public static final String RETURN_KEY_ADDRESS = "GetMapPositionActivity.address";
    public static final String RETURN_KEY_LATITUDE = "GetMapPositionActivity.latitude";
    public static final String RETURN_KEY_LONGITUDE = "GetMapPositionActivity.longitude";
    
    private static final int GEOCODE_REQUEST_TAG = 1;
    private static final int LAT_SPAN = (int)(0.005 * 1E6);
    private static final int LNG_SPAN = (int)(0.005 * 1E6);

    private TextView addressText;
    private MatjiMapView mMapView;
    private MapController mMapController;
    private Context mContext;
    private GpsManager mGpsManager;
    private HttpRequestManager mRequestManager;
    private GeocodeHttpRequest geocodeRequest;
    private Location prevLocation;
    private CenterOverlay centerOverlay;
    private boolean isMapClicked;
    private String lastAddress;
    private double lastLatitude;
    private double lastLongitude;

    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_get_map_position);
	
	addressText = (TextView)findViewById(R.id.address);
	mMapView = (MatjiMapView)findViewById(R.id.map_view);
	mMapView.setMapCenterListener(this);
	mMapView.startMapCenterThread();
	mMapController = mMapView.getController();
	mContext = getApplicationContext();
	// mMapController.stopPanning();
	mGpsManager = new GpsManager(mContext, this);
	mRequestManager = new HttpRequestManager(mContext, this);
	geocodeRequest = new GeocodeHttpRequest(mContext);
	centerOverlay = new CenterOverlay(mContext, mMapView);
	centerOverlay.drawOverlay();
	mGpsManager.start();
    }

    private String getAddressString(Address addr) {
	// don't remove this function...
	String addrString = addr.getAddressLine(0);
	int spaceIndex = 0;
	spaceIndex = addrString.indexOf(' ');
	return addrString.substring(spaceIndex);
    }

    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
	switch(tag) {
	case GEOCODE_REQUEST_TAG:
	    Address address = ((AddressMatjiData)data.get(0)).getAddress();
	    // don't uncomment this line...
	    // String addressString = getAddressString(address);
	    lastAddress = address.getAddressLine(0);
	    addressText.setText(lastAddress);
	    break;
	}
    }

    public void onLocationChanged(Location location) {
	if (prevLocation != null) {
	    if (prevLocation.getAccuracy() <= location.getAccuracy()) {
		mGpsManager.stop();
	    }
	}

	requestGeocodeByLocation(location);
	prevLocation = location;
	setCenter(prevLocation);
    }

    private void setCenter(Location loc) {
	int geoLat = (int)(loc.getLatitude() * 1E6);
	int geoLng = (int)(loc.getLongitude() * 1E6);
	GeoPoint geoPoint = new GeoPoint(geoLat, geoLng);
	mMapController.animateTo(geoPoint);
	mMapController.zoomToSpan(LAT_SPAN, LNG_SPAN);
    }

    private void requestGeocodeByLocation(Location loc) {
	geocodeRequest.actionFromLocation(loc, 1);
	mRequestManager.request(this, geocodeRequest, GEOCODE_REQUEST_TAG);
    }

    private void requestGeocodeByGeoPoint(GeoPoint point) {
	geocodeRequest.actionFromGeoPoint(point, 1);
	mRequestManager.request(this, geocodeRequest, GEOCODE_REQUEST_TAG);
    }
    
    protected void onPause() {
	super.onPause();
	mGpsManager.stop();
    }
    
    protected void onDestroy() {
	super.onDestroy();
	mGpsManager.stop();
    }

    public void onMapCenterChanged(GeoPoint point) {
    	Runnable runnable = new GeocodeRunnable(point);
	lastLongitude = (double)point.getLongitudeE6() / 1E6;
	lastLatitude = (double)point.getLatitudeE6() / 1E6;
	runOnUiThread(runnable);
    }

    public void requestExceptionCallBack(int tag, MatjiException e) {
	e.performExceptionHandling(mContext);
    }

    public void onLocationExceptionDelivered(MatjiException e) {
    	e.performExceptionHandling(mContext);
    }

    public void onCurrentLocationClicked(View v) {
	mGpsManager.start();
    }

    public void onSubmitClicked(View v) {
	Intent data = new Intent();
	data.putExtra(RETURN_KEY_ADDRESS, lastAddress);
	data.putExtra(RETURN_KEY_LATITUDE, lastLatitude);
	data.putExtra(RETURN_KEY_LONGITUDE, lastLongitude);
	setResult(Activity.RESULT_OK, data);
	finish();
    }

    protected void onTitleBarItemClicked(View view) { }
    protected boolean setTitleBarButton(Button button) { return false; }
    protected String titleBarText() {
	return "GetMapPositionActivity";
    }

    private class GeocodeRunnable implements Runnable {
	private GeoPoint point;

	public GeocodeRunnable(GeoPoint point) {
	    this.point = point;
	}

	public void run() {
	    requestGeocodeByGeoPoint(point);
	}
    }
}