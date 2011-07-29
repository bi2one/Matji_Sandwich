package com.matji.sandwich;

import java.util.ArrayList;

import android.content.Intent;
import android.content.Context;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.app.Activity;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;

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
	// private GeocodeHttpRequest geocodeRequest;
	private Location prevLocation;
	private CenterOverlay centerOverlay;
	//    private boolean isMapClicked;
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
		mRequestManager = HttpRequestManager.getInstance(mContext);
		// geocodeRequest = new GeocodeHttpRequest(mContext);
		centerOverlay = new CenterOverlay(mContext, mMapView);
		centerOverlay.drawOverlay();
		mGpsManager.start(1);
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
			lastAddress = getAddressString(address);
			// lastAddress = address.getAddressLine(0);
			addressText.setText(lastAddress);
			break;
		}
	}

    public void onLocationChanged(int tag, Location location) {
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
		// geocodeRequest.actionFromLocation(loc, 1);
		// mRequestManager.request(this, geocodeRequest, GEOCODE_REQUEST_TAG, this);
	}

	private void requestGeocodeByGeoPoint(GeoPoint point) {
		// geocodeRequest.actionFromGeoPoint(point, 1);
		// mRequestManager.request(this, geocodeRequest, GEOCODE_REQUEST_TAG, this);
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

    public void onLocationExceptionDelivered(int tag, MatjiException e) {
		e.performExceptionHandling(mContext);
	}

	public void onCurrentLocationClicked(View v) {
		mGpsManager.start(1);
	}

	public void onSubmitClicked(View v) {
		Intent data = new Intent();
		data.putExtra(RETURN_KEY_ADDRESS, lastAddress);
		data.putExtra(RETURN_KEY_LATITUDE, lastLatitude);
		data.putExtra(RETURN_KEY_LONGITUDE, lastLongitude);
		setResult(Activity.RESULT_OK, data);
		finish();
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