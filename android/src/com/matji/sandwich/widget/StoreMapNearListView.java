package com.matji.sandwich.widget;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.matji.sandwich.MainMapActivity;
import com.matji.sandwich.R;
import com.matji.sandwich.Requestable;
import com.matji.sandwich.adapter.SimpleStoreAdapter;
import com.matji.sandwich.data.GeocodeAddress;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.GeocodeHttpRequest;
import com.matji.sandwich.http.request.RequestCommand;
import com.matji.sandwich.http.request.StoreHttpRequest;
import com.matji.sandwich.http.spinner.SpinnerFactory.SpinnerType;
import com.matji.sandwich.location.GpsManager;
import com.matji.sandwich.location.MatjiLocationListener;
import com.matji.sandwich.session.SessionMapUtil;
import com.matji.sandwich.util.GeocodeUtil;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.util.adapter.GeoPointToLocationAdapter;
import com.matji.sandwich.util.adapter.LocationToGeoPointAdapter;

public class StoreMapNearListView extends RequestableMListView implements MatjiLocationListener,
OnTouchListener,
Requestable {
	private static final int GPS_START_TAG = 1;
	private static final int GEOCODE = 1001;
	private int limit;
	private HttpRequestManager requestManager;
	private StoreHttpRequest storeRequest;
	private RelativeLayout addressWrapper;
	private SessionMapUtil sessionUtil;
	private GpsManager gpsManager;
	private Location prevLocation;
	private TextView addressView;
	private Context context;

	public StoreMapNearListView(Context context, AttributeSet attrs) {
		super(context, attrs, new SimpleStoreAdapter(context), 10);
		this.context = context;
		sessionUtil = new SessionMapUtil(context);
		storeRequest = new StoreHttpRequest(context);
		gpsManager = new GpsManager(context, this);
		requestManager = HttpRequestManager.getInstance();
		setOnTouchListener(this);
		setSelector(R.color.transparent);
		setPage(1);
	}

	public void init(RelativeLayout addressWrapper, TextView addressView, MainMapActivity activity) {
		setActivity(activity);
		this.addressView = addressView;
		this.addressWrapper = addressWrapper;
		setDivider(new ColorDrawable(MatjiConstants.color(R.color.listview_divider1_gray)));
		setDividerHeight((int) MatjiConstants.dimen(R.dimen.default_divider_size));
		addHeaderView(new HighlightHeader(activity, MatjiConstants.string(R.string.default_string_store)));
		((SimpleStoreAdapter) getMBaseAdapter()).visibleBookmarkView();
	}

	public boolean onTouch(View v, MotionEvent e) {
		switch(e.getAction()) {
		case MotionEvent.ACTION_DOWN:
			gpsManager.stop();
			break;
		}
		return false;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public void moveToGpsCenter() {
		gpsManager.start(GPS_START_TAG, null);
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

	public void setStartConfigListener(GpsManager.StartConfigListener listener) {
		gpsManager.setStartConfigListener(listener);
	}

	public void onLocationExceptionDelivered(int startedFromTag, MatjiException e) {
		e.performExceptionHandling(context);
	}

	public RequestCommand request() {
		GeoPoint neBound = sessionUtil.getNEBound();
		GeoPoint swBound = sessionUtil.getSWBound();

		storeRequest.actionNearbyList((double) swBound.getLatitudeE6() / 1E6,
				(double) neBound.getLatitudeE6() / 1E6,
				(double) swBound.getLongitudeE6() / 1E6,
				(double) neBound.getLongitudeE6() / 1E6,
				getPage(), limit);
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
		geocodeRequest.actionReverseGeocodingByGeoPoint(locationPoint, sessionUtil.getCurrentCountry());
		requestManager.cancelTask();
		requestManager.request(getContext(), addressWrapper, SpinnerType.SMALL, geocodeRequest, GEOCODE, this);
	}

	public void setStores(ArrayList<MatjiData> stores) {
		getAdapterData().removeAll(getAdapterData());
		getAdapterData().addAll(stores);
		setPage((stores.size() / limit) + 1);
	}

	public void onListItemClick(int position) {}

	public void dataRefresh() {
		getMBaseAdapter().notifyDataSetChanged();
	}
}