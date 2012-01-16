package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.http.request.StoreHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;

public class StoreNearRadiusListView extends SimpleStoreListView {
	private StoreHttpRequest storeRequest;
	private double BASIC_BOUND = 0.03;
	private double latSW;
	private double latNE;
	private double lngSW;
	private double lngNE;

	public StoreNearRadiusListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		storeRequest = new StoreHttpRequest(context);
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