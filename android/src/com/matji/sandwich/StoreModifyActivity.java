package com.matji.sandwich;

import java.util.ArrayList;

import android.location.Location;
import android.os.Bundle;
import android.view.View;

import com.matji.sandwich.base.BaseMapActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.DialogAsyncTask;
import com.matji.sandwich.http.request.StoreModifyHttpRequest;
import com.matji.sandwich.map.StoreModifyMatjiMapView;
import com.matji.sandwich.util.KeyboardUtil;
import com.matji.sandwich.util.adapter.GeoPointToLocationAdapter;
import com.matji.sandwich.widget.dialog.SimpleAlertDialog;
import com.matji.sandwich.widget.dialog.SimpleDialog;
import com.matji.sandwich.widget.title.CompletableTitle;
import com.matji.sandwich.widget.title.CompletableTitle.Completable;

public class StoreModifyActivity extends BaseMapActivity implements Completable, Requestable, StoreModifyMatjiMapView.OnClickListener, SimpleAlertDialog.OnClickListener {
	public static final String STORE = "StoreModifyActivity.store";
	private static final int TAG_STORE_MODIFY = 1;
	private CompletableTitle titleBar;
	private StoreModifyMatjiMapView mapView;
	private SimpleAlertDialog storeAlertDialog;
	private SimpleAlertDialog successDialog;
	private Store store;
	
	public int setMainViewId() {
		return R.id.activity_store_modify;
	}


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_modify);

		store = (Store) getIntent().getParcelableExtra(STORE);
		
		titleBar = (CompletableTitle) findViewById(R.id.activity_store_modify_title);
		mapView = (StoreModifyMatjiMapView) findViewById(R.id.activity_store_modify_mapview);

		titleBar.setTitle(R.string.default_string_store_modify_request);
		titleBar.setCompletable(this);

		mapView.init(this);
		mapView.setOnClickListener(this);

		mapView.setStoreName(store.getName().trim());
		if (store.getAddAddress() != null)
			mapView.setStoreAddAddress(store.getAddAddress().trim());
		mapView.setStorePhoneNumber(store.getTel().trim());
		
		storeAlertDialog = new SimpleAlertDialog(this, R.string.write_store_invalidate_store);
		successDialog = new SimpleAlertDialog(this, R.string.store_modify_success);

		successDialog.setOnClickListener(this);
	}

	public void complete() {
		Location centerPoint = new GeoPointToLocationAdapter(mapView.getMapCenter());
		String storeName = mapView.getStoreName();
		String address = mapView.getAddress();
		String addAddress = mapView.getAddAddress();
		String phoneNumber = mapView.getPhoneNumber();
		double centerLat = centerPoint.getLatitude();
		double centerLng = centerPoint.getLongitude();

		if (isValid(storeName, address, addAddress, phoneNumber, centerLat, centerLng)) {
			StoreModifyHttpRequest request = new StoreModifyHttpRequest(StoreModifyActivity.this);
			request.actionModify(storeName, address, centerLat, centerLng, store.getId());
			DialogAsyncTask requestTask = new DialogAsyncTask(this, this, getMainView(), request, TAG_STORE_MODIFY);
			requestTask.execute();
			titleBar.setCompletable(null);
		}
	}

	private boolean isValid(String storeName, String address, String addAddress, String phoneNumber, double centerLat, double centerLng) {
		if (storeName.trim().equals("")) {
			storeAlertDialog.show();
			return false;
		}
		return true;
	}


	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		switch(tag) {
		case TAG_STORE_MODIFY:
				successDialog.show();
			break;
		}
	}

	
	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.performExceptionHandling(this);
		titleBar.setCompletable(this);
	}

	
	public void onConfirmClick(SimpleDialog dialog) {
        if (dialog == successDialog) {
            setResult(RESULT_OK);
            finish();
        }
	}

	
	public void onShowMapClick(View v) {
		KeyboardUtil.hideKeyboard(this);
		mapView.requestFocus();
	}

	
	public void onMapTouchUp(View v) {}

	
	public void onMapTouchDown(View v) {
		KeyboardUtil.hideKeyboard(this);
		mapView.requestFocus();
	}

	public void finish() {
		mapView.stopMapCenterThread();
		super.finish();
	}

}
