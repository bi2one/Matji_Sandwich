package com.matji.sandwich.map;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.view.View;
import android.view.LayoutInflater;
import android.location.Location;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.Overlay;

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
import java.util.List;
import java.lang.ref.WeakReference;

public class WriteStoreMatjiMapView extends RelativeLayout implements MatjiMapViewInterface,
								      MatjiMapCenterListener,
								      MatjiLocationListener,
								      Requestable,
								      ClickListenerOverlay.OnClickListener,
								      View.OnClickListener {
    private static final int GPS_START_TAG = 0;
    private static final int REQUEST_REVERSE_GEOCODING = 0;
    private WeakReference<Activity> activityRef;
    private MatjiMapView mapView;
    private MapController mapController;
    private SimpleSubmitLocationBar locationBar;
    private WeakReference<OnClickListener> clickListenerRef;
    private GeocodeRunnable geocodeRunnable;
    private HttpRequestManager requestManager;
    private SessionMapUtil sessionMapUtil;

    private EditText storeNameText;
    private TextView addressText;
    private RelativeLayout spinnerWrapper;
    private EditText addAddressText;
    private EditText phoneNumberText;
    private RelativeLayout downButton;
    private Location prevLocation;
    private GpsManager gpsManager;
    
    public WriteStoreMatjiMapView(Context context, AttributeSet attrs) {
	super(context, attrs);
	
	LayoutInflater.from(context).inflate(R.layout.write_store_matji_mapview, this, true);
	
	mapView = (MatjiMapView)findViewById(R.id.write_store_matji_mapview_map);
	storeNameText = (EditText)findViewById(R.id.write_store_matji_mapview_store_name);
	addressText = (TextView)findViewById(R.id.write_store_matji_mapview_address);
	spinnerWrapper = (RelativeLayout)findViewById(R.id.write_store_matji_mapview_spinner_wrapper);
	addAddressText = (EditText)findViewById(R.id.write_store_matji_mapview_add_address);
	phoneNumberText = (EditText)findViewById(R.id.write_store_matji_mapview_phone_number);
	downButton = (RelativeLayout)findViewById(R.id.write_store_matji_mapview_down_button);

	downButton.setOnClickListener(this);
	geocodeRunnable = new GeocodeRunnable(spinnerWrapper, context, this);
	requestManager = HttpRequestManager.getInstance();
	gpsManager = new GpsManager(context, this);
	sessionMapUtil = new SessionMapUtil(context);
	mapController = mapView.getController();
	setMapCenterListener(this);

	ClickListenerOverlay listenerOverlay = new ClickListenerOverlay();
	listenerOverlay.setOnClickListener(this);
	List<Overlay> overlays = mapView.getOverlays();
	overlays.add(listenerOverlay);
	
	gpsManager.start(GPS_START_TAG, null);
    }

    public void onLocationChanged(int startedFromTag, Location location) {
	if (prevLocation != null) {
	    if (prevLocation.getAccuracy() >= location.getAccuracy()) {
		mapView.startMapCenterThread(new LocationToGeoPointAdapter(location));
		gpsManager.stop();
	    }
	}

	prevLocation = location;
	setCenter(new LocationToGeoPointAdapter(location));
    }

    public void onLocationExceptionDelivered(int startedFromTag, MatjiException e) {
	e.performExceptionHandling(getContext());
    }

    public String getStoreName() {
	return storeNameText.getText().toString();
    }

    public String getAddress() {
	return addressText.getText().toString();
    }

    public String getAddAddress() {
	return addAddressText.getText().toString();
    }

    public String getPhoneNumber() {
	return phoneNumberText.getText().toString();
    }

    public GeoPoint getMapCenter() {
	return mapView.getMapCenter();
    }

    public void moveToGpsCenter() {
	gpsManager.start(GPS_START_TAG, activityRef.get());
    }

    public void init(Activity activity) {
	this.activityRef = new WeakReference(activity);
    }

    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
	switch(tag) {
	case REQUEST_REVERSE_GEOCODING:
	    GeocodeAddress geocodeAddress = GeocodeUtil.getDetailedAddress(data);
	    addressText.setText(geocodeAddress.getCountrySplitedAddress());
	}
    }

    public void requestExceptionCallBack(int tag, MatjiException e) {
	e.performExceptionHandling(getContext());
    }

    public void onMapCenterChanged(GeoPoint point) {
	Log.d("=====", "changed");
	geocodeRunnable.setCenter(point);
	activityRef.get().runOnUiThread(geocodeRunnable);
    }

    public void onMapTouchUp(View v) {
	if (clickListenerRef == null) {
	    return ;
	} else {
	    clickListenerRef.get().onMapTouchUp(v);
	}
    }

    public void onMapTouchDown(View v) {
	if (clickListenerRef == null) {
	    return ;
	} else {
	    clickListenerRef.get().onMapTouchDown(v);
	}
    }

    public void onClick(View v) {
	if (clickListenerRef == null) {
	    return ;
	}
	
	int vId = v.getId();
	if (vId == downButton.getId()) {
	    clickListenerRef.get().onShowMapClick(v);
	}
    }

    public void setCenter(int lat, int lng) {
    	setCenter(new GeoPoint(lat, lng));
    }

    public void setCenter(GeoPoint point) {
    	mapController.zoomToSpan(sessionMapUtil.getBasicLatSpan(), sessionMapUtil.getBasicLngSpan());
    	mapController.animateTo(point);
    }

    public void setOnClickListener(OnClickListener clickListener) {
	this.clickListenerRef = new WeakReference(clickListener);
    }

    public void startMapCenterThread() {
	if (!gpsManager.isRunning()) {
	    mapView.startMapCenterThread();
	}
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

    private class GeocodeRunnable implements Runnable {
	private WeakReference<Context> contextRef;
	private WeakReference<Requestable> requestableRef;
	private GeoPoint center;
	private RelativeLayout spinnerContainer;
	
	public GeocodeRunnable(RelativeLayout spinnerContainer, Context context, Requestable requestable) {
	    this.contextRef = new WeakReference(context);
	    this.requestableRef = new WeakReference(requestable);
	    this.spinnerContainer = spinnerContainer;
	}

	public void setCenter(GeoPoint center) {
	    this.center = center;
	}

	public void run() {
    	    GeocodeHttpRequest geocodeRequest = new GeocodeHttpRequest(contextRef.get());
    	    geocodeRequest.actionReverseGeocodingByGeoPoint(center, sessionMapUtil.getCurrentCountry());
    	    requestManager.request(contextRef.get(), spinnerContainer, SpinnerFactory.SpinnerType.SMALL, geocodeRequest, REQUEST_REVERSE_GEOCODING, requestableRef.get());
	}
    }

    public interface OnClickListener {
	public void onShowMapClick(View v);
	public void onMapTouchUp(View v);
	public void onMapTouchDown(View v);
    }
}