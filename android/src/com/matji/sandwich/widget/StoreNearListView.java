package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.http.request.StoreHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;

public class StoreNearListView extends StoreListView {
	private StoreHttpRequest storeRequest;
	private double lat_sw=37.;
	private double lat_ne=126.6;
	private double lng_sw=37.4;
	private double lng_ne=126.9;

	public StoreNearListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		storeRequest = new StoreHttpRequest(context);
	}

	public HttpRequest request() {
		storeRequest.actionNearbyList(lat_sw, lat_ne, lng_sw, lng_ne, getPage(), getLimit());
		return storeRequest;
	}	
}