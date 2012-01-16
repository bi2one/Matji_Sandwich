package com.matji.sandwich.map;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.location.Location;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.matji.sandwich.R;
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
import com.matji.sandwich.overlay.OverlayClickListener;
import com.matji.sandwich.overlay.StoreItemizedOverlay;
import com.matji.sandwich.session.SessionMapUtil;
import com.matji.sandwich.util.GeocodeUtil;
import com.matji.sandwich.util.adapter.LocationToGeoPointAdapter;
import com.matji.sandwich.widget.StoreMapNearListView;

public class MainMatjiMapView extends MatjiMapView implements MatjiMapCenterListener, MatjiLocationListener, Requestable, OnTouchListener {
	private static final int LAT_SPAN = (int)(0.005 * 1E6);
	private static final int LNG_SPAN = (int)(0.005 * 1E6);
	private static final int GPS_START_TAG = 1;
	private static final int NEARBY_STORE = 1;
	private static final int GEOCODE = 2;
	private WeakReference<BaseMapActivity> activityRef;
	private StoreItemizedOverlay storeItemizedOverlay;
	private HttpRequestManager requestManager;
	private RelativeLayout addressWrapper;
	private StoreMapNearListView listView;
	private MapController mapController;
	private ArrayList<MatjiData> stores;
	private SessionMapUtil sessionUtil;
	private ViewGroup spinnerLayout;
	private GpsManager gpsManager;
	private Location prevLocation;
	private TextView addressView;
	private Context context;
	private int limit;

	public MainMatjiMapView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		setMapCenterListener(this);
		setOnTouchListener(this);
	}

	public void setListView(StoreMapNearListView listView) {
		this.listView = listView;
	}

	public void init(RelativeLayout addressWrapper, TextView addressView, BaseMapActivity activity, ViewGroup spinnerLayout) {
		setAddressView(addressView);
		setBaseMapActivity(activity);
		this.spinnerLayout = spinnerLayout;
		this.addressWrapper = addressWrapper;
		storeItemizedOverlay = new StoreItemizedOverlay(context, this);
		mapController = getController();
		requestManager = HttpRequestManager.getInstance();
		sessionUtil = new SessionMapUtil(context);
		gpsManager = new GpsManager(context, this);
		mapController.zoomToSpan(LAT_SPAN, LNG_SPAN);
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public void setOverlayClickListener(OverlayClickListener listener) {
		storeItemizedOverlay.setOverlayClickListener(listener);
	}

	public void setStartConfigListener(GpsManager.StartConfigListener listener) {
		gpsManager.setStartConfigListener(listener);
	}

	public void reload() {
		Runnable runnable = new MapRunnable(this);
		activityRef.get().runOnUiThread(runnable);
		setCenter(sessionUtil.getCenter());
		startMapCenterThreadNotFirstLoading();
	}

	public void moveToGpsCenter() {
		stopMapCenterThread();
		gpsManager.start(GPS_START_TAG, activityRef.get());
		Toast.makeText(getContext(), R.string.main_map_find_center, Toast.LENGTH_LONG).show();
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
			listView.setStores(stores);
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

		if (prevLocation == null)
			setCenterNotAnimate(location);
		else
			setCenter(location);
		prevLocation = location;
//		if (listView != null)
//			listView.refresh();
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
		List<Overlay> overlays = getOverlays();
		for (int i = 0; i < overlays.size(); i++) {
			Overlay overlay = overlays.get(i);
			if (!(overlay instanceof MyLocationOverlay)) overlays.remove(i); 
		}

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
		this.activityRef = new WeakReference<BaseMapActivity>(activity);
	}

	public void stop() {
		gpsManager.stop();	
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
			CoordinateRegion cr = new CoordinateRegion(getMapCenter(), getLatitudeSpan(), getLongitudeSpan());
			GeoPoint swPoint = cr.getSWGeoPoint();
			GeoPoint nePoint = cr.getNEGeoPoint();

			double lat_sw = (double)(swPoint.getLatitudeE6()) / (double)1E6;
			double lng_sw = (double)(swPoint.getLongitudeE6()) / (double)1E6;
			double lat_ne = (double)(nePoint.getLatitudeE6()) / (double)1E6;
			double lng_ne = (double)(nePoint.getLongitudeE6()) / (double)1E6;

			StoreHttpRequest request = new StoreHttpRequest(context);
			request.actionNearbyList(lat_sw, lat_ne, lng_sw, lng_ne, 1, limit);
			GeocodeHttpRequest geocodeRequest = new GeocodeHttpRequest(context);
			geocodeRequest.actionReverseGeocodingByGeoPoint(sessionUtil.getCenter(), sessionUtil.getCurrentCountry());

			requestManager.request(getContext(), spinnerLayout, SpinnerType.NORMAL, request, NEARBY_STORE, requestable);
			requestManager.request(getContext(), addressWrapper, SpinnerType.SMALL, geocodeRequest, GEOCODE, requestable);
		}
	}
}