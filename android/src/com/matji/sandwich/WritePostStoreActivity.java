package com.matji.sandwich;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.util.Log;

import com.google.android.maps.GeoPoint;

import com.matji.sandwich.map.RadiusMatjiMapView;
import com.matji.sandwich.base.BaseMapActivity;
import com.matji.sandwich.util.KeyboardUtil;
import com.matji.sandwich.widget.WritePostStoreView;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.adapter.WritePostStoreAdapter.StoreElement;
import com.matji.sandwich.session.SessionWritePostUtil;
import com.matji.sandwich.session.SessionMapUtil;

public class WritePostStoreActivity extends BaseMapActivity implements WritePostStoreView.StoreSelectListener,
								       WritePostStoreView.OnClickListener,
								       RadiusMatjiMapView.OnClickListener {
    private Context context;
    private WritePostTabActivity parentActivity;
    private SessionWritePostUtil sessionUtil;
    private WritePostStoreView storeListView;
    private RadiusMatjiMapView radiusMapView;
    private SessionMapUtil sessionMapUtil;
    private View currentView;

    public int setMainViewId() {
	return R.id.activity_write_post_store;
    }
    
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_write_post_store);

	context = getApplicationContext();
	sessionUtil = new SessionWritePostUtil(context);
	sessionMapUtil = new SessionMapUtil(context);

	radiusMapView = (RadiusMatjiMapView)findViewById(R.id.activity_write_post_store_mapview);
	storeListView = (WritePostStoreView)findViewById(R.id.activity_write_post_store_listview);
	radiusMapView.init(this);
	storeListView.init(this);
	storeListView.refresh(sessionMapUtil.getNEBound(), sessionMapUtil.getSWBound());
	storeListView.setStoreSelectListener(this);
	storeListView.setOnClickListener(this);
	radiusMapView.setOnClickListener(this);

	parentActivity = (WritePostTabActivity)getParent();
	currentView = storeListView;
    }

    protected void onResume() {
	super.onResume();
	KeyboardUtil.hideKeyboard(this);
    }

    protected void onPause() {
	super.onPause();
	Store selectedStore = storeListView.getSelectedStore();
	if (selectedStore != null)
	    sessionUtil.setStoreId(selectedStore.getId());
    }

    public void onSearchLocationClick(View v) {
	showRadiusMapView();
    }

    public void onWriteStoreClick(View v) {
	Log.d("=====", "write store");
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)  {
	if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
	    if (currentView.getId() == storeListView.getId())
		finish();
	    else
		showStoreListView();
	    return true;
	}
	return super.onKeyDown(keyCode, event);
    }    

    public void onSubmitClick(View v, GeoPoint neBound, GeoPoint swBound) {
	storeListView.refresh(neBound, swBound);
	showStoreListView();
    }
    
    public void onStoreSelect(Store store) {
	parentActivity.onChecked(WritePostTabActivity.TAB_ID_STORE, true);
    }

    private void showRadiusMapView() {
	storeListView.setVisibility(View.GONE);
	radiusMapView.startMapCenterThread();
	radiusMapView.setVisibility(View.VISIBLE);
	currentView = radiusMapView;
    }

    private void showStoreListView() {
	radiusMapView.setVisibility(View.GONE);
	radiusMapView.stopMapCenterThread();
	storeListView.setVisibility(View.VISIBLE);
	currentView = storeListView;
    }
}