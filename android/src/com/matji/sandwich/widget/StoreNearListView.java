package com.matji.sandwich.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.data.Store;
import com.matji.sandwich.adapter.StoreAdapter;
import com.matji.sandwich.http.request.StoreHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.exception.MatjiException;

import java.util.ArrayList;

public class StoreNearListView extends RequestableMListView<Store> {
    private StoreHttpRequest storeRequest;
    private double lat_sw=37.3;
    private double lat_ne=126.804;
    private double lng_sw=37.4;
    private double lng_ne=126.828;
	
    public StoreNearListView(Context context, AttributeSet attrs) {
	super(context, attrs, new StoreAdapter(context), 10);
	storeRequest = new StoreHttpRequest(context);
    }

    public void start(Activity activity) {
	super.start(activity);
    }

    public HttpRequest request() {
	storeRequest.actionNearbyList(lat_sw, lat_ne, lng_sw, lng_ne, getPage(), getLimit());
	return storeRequest;
    }
    
    public void requestCallBack(int tag, ArrayList<Store> data) {
	super.requestCallBack(tag, data);
    }

    public void requestExceptionCallBack(int tag, MatjiException e) {
	super.requestExceptionCallBack(tag, e);
    }

    public void onListItemClick(int position) {
    }
}