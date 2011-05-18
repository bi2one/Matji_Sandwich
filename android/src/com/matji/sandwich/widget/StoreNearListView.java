package com.matji.sandwich.widget;

import android.app.Activity;
import android.view.ViewGroup;
import android.view.View;
import android.content.Context;
import android.util.Log;
import android.util.AttributeSet;

import com.matji.sandwich.Requestable;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.adapter.StoreAdapter;
import com.matji.sandwich.http.request.StoreHttpRequest;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.exception.MatjiException;

import java.util.ArrayList;

public class StoreNearListView extends RequestableMListView {
	private StoreHttpRequest storeRequest;
	private int page = 1;
	private final static int STORE_LIMIT = 10;
	private double lat_sw=37.3;
	private double lat_ne=126.804;
	private double lng_sw=37.4;
	private double lng_ne=126.828;
	
	public StoreNearListView(Context context, AttributeSet attrs) {
		super(context, attrs, new StoreAdapter(context), STORE_LIMIT);
		storeRequest = new StoreHttpRequest();
	}

	public void start(Activity activity) {
		super.start(activity);
	}
	
//	public void setPosition(double lat_sw, double lat_ne, double lng_sw, double lng_ne) {
//		this.lat_ne = lat_ne;
//		this.lat_sw = lat_sw;
//		this.lng_ne = lng_ne;
//		this.lng_sw = lng_sw;
//	}
	
	
	public void requestNext() {
		storeRequest.actionNearbyList(lat_sw, lat_ne, lng_sw, lng_ne, page, STORE_LIMIT);
		page++;
		getHttpRequestManager().request(getActivity(), storeRequest, 0);
	}

	public void requestReload() {
		page = 1;
		requestNext();
	}

	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		super.requestCallBack(tag, data);
	}

	public void requestExceptionCallBack(int tag, MatjiException e) {
		super.requestExceptionCallBack(tag, e);
	}
}