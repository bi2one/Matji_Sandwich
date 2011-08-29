package com.matji.sandwich.map;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.view.View;
import android.view.LayoutInflater;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;

import com.matji.sandwich.R;
import com.matji.sandwich.Requestable;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.GeocodeAddress;
import com.matji.sandwich.widget.SimpleSubmitLocationBar;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.GeocodeHttpRequest;
import com.matji.sandwich.util.GeocodeUtil;
import com.matji.sandwich.session.SessionMapUtil;

import java.util.ArrayList;

public class RadiusMatjiMapView extends RelativeLayout implements MatjiMapViewInterface,
								  SimpleSubmitLocationBar.OnClickListener,
								  MatjiMapCenterListener,
								  Requestable {
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
    private SessionMapUtil sessionMapUtil;
    
    public RadiusMatjiMapView(Context context, AttributeSet attrs) {
	super(context, attrs);
	this.context = context;
	
	LayoutInflater.from(context).inflate(R.layout.radius_matji_mapview, this, true);
	geocodeRunnable = new GeocodeRunnable(this, context, this);
	mapView = (MatjiMapView)findViewById(R.id.radius_matji_mapview_map);
	locationBar = (SimpleSubmitLocationBar)findViewById(R.id.radius_matji_mapview_location_bar);
	requestManager = HttpRequestManager.getInstance(context);
	sessionMapUtil = new SessionMapUtil(context);

	mapController = mapView.getController();
	locationBar.setOnClickListener(this);
	setMapCenterListener(this);
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

    public void requestExceptionCallBack(int tag, MatjiException e) {
	e.performExceptionHandling(context);
    }

    public void onMapCenterChanged(GeoPoint point) {
	lastNEBound = getBound(BoundType.MAP_BOUND_NE);
	lastSWBound = getBound(BoundType.MAP_BOUND_SW);
	geocodeRunnable.setCenter(point);
	activity.runOnUiThread(geocodeRunnable);
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
	if (clickListener != null) {
	    clickListener.onSubmitClick(this, lastNEBound, lastSWBound);
	}
    }

    private class GeocodeRunnable implements Runnable {
	private Context context;
	private Requestable requestable;
	private GeoPoint center;
	private RelativeLayout spinnerContainer;
	
	public GeocodeRunnable(RelativeLayout spinnerContainer, Context context, Requestable requestable) {
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
    	    requestManager.request(spinnerContainer, geocodeRequest, REQUEST_REVERSE_GEOCODING, requestable);
	}
    }

    public interface OnClickListener {
	public void onSubmitClick(View v, GeoPoint neBound, GeoPoint swBound);
    }
}