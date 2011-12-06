package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.http.request.StoreHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.session.Session;

public class StoreNearRadiusListView extends SimpleStoreListView {
	private StoreHttpRequest storeRequest;
	private double BASIC_BOUND = 0.03;
	// private int BASIC_LATITUDE_SW = 37000000;
	// private int BASIC_LATITUDE_NE = 126600000;
	// private int BASIC_LONGITUDE_SW = 37400000;
	// private int BASIC_LONGITUDE_NE = 126900000;
	private double latSW;
	private double latNE;
	private double lngSW;
	private double lngNE;

	private Session session;

	public StoreNearRadiusListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		storeRequest = new StoreHttpRequest(context);
		session = Session.getInstance(context);

		// latSW = session.getPreferenceProvider().getInt(Session.MAP_BOUND_LATITUDE_SW, BASIC_LATITUDE_SW);
		// latNE = session.getPreferenceProvider().getInt(Session.MAP_BOUND_LATITUDE_NE, BASIC_LATITUDE_NE);
		// lngSW = session.getPreferenceProvider().getInt(Session.MAP_BOUND_LONGITUDE_SW, BASIC_LONGITUDE_SW);
		// lngNE = session.getPreferenceProvider().getInt(Session.MAP_BOUND_LONGITUDE_NE, BASIC_LONGITUDE_NE);
	}

	public void setCenter(double latitude, double longitude) {
		latSW = latitude - BASIC_BOUND;
		lngSW = longitude - BASIC_BOUND;
		latNE = latitude + BASIC_BOUND;
		lngNE = longitude + BASIC_BOUND;
	}

	public HttpRequest request() {
		storeRequest.actionNearbyList(latSW, latNE, lngSW, lngNE, getPage(), getLimit());
		return storeRequest;
	}
}