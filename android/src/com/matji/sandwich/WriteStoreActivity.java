package com.matji.sandwich;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.util.Log;

import com.matji.sandwich.base.BaseMapActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.DialogAsyncTask;
import com.matji.sandwich.http.request.StoreHttpRequest;
import com.matji.sandwich.map.WriteStoreMatjiMapView;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.KeyboardUtil;
import com.matji.sandwich.util.adapter.GeoPointToLocationAdapter;
import com.matji.sandwich.widget.dialog.SimpleDialog;
import com.matji.sandwich.widget.dialog.SimpleAlertDialog;
import com.matji.sandwich.widget.title.CompletableTitle;

public class WriteStoreActivity extends BaseMapActivity implements CompletableTitle.Completable,
								   WriteStoreMatjiMapView.OnClickListener,
								   Requestable,
								   SimpleAlertDialog.OnClickListener {
    public static final String DATA_STORE = "WriteStoreActivity.store";
    private static final int TAG_WRITE_STORE = 0;
    private Context context;
    private CompletableTitle titleBar;
    private WriteStoreMatjiMapView mapView;
    private SimpleAlertDialog storeAlertDialog;
    private SimpleAlertDialog successDialog;
    private Store storeData;
    private Session session;

    public int setMainViewId() {
        return R.id.activity_write_store;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_store);

        context = getApplicationContext();
        titleBar = (CompletableTitle)findViewById(R.id.activity_write_store_title);
        mapView = (WriteStoreMatjiMapView)findViewById(R.id.activity_write_store_mapview);

        titleBar.setTitle(R.string.write_store_title);
        titleBar.setCompletable(this);

        mapView.init(this);
        mapView.startMapCenterThread();
        mapView.setOnClickListener(this);

        storeAlertDialog = new SimpleAlertDialog(this, R.string.write_store_invalidate_store);
        successDialog = new SimpleAlertDialog(this, R.string.write_store_success);

        successDialog.setOnClickListener(this);
        // 이 함수를 call하면 지도가 gps중앙으로 이동한다.
        // mapView.moveToGpsCenter();
        
        session = Session.getInstance(this);
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
            StoreHttpRequest request = new StoreHttpRequest(context);
            request.actionNew(storeName, address, centerLat, centerLng, addAddress, phoneNumber);
            DialogAsyncTask requestTask = new DialogAsyncTask(this, this, getMainView(), request, TAG_WRITE_STORE);
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

    public void onShowMapClick(View v) {
        KeyboardUtil.hideKeyboard(this);
        mapView.requestFocus();
    }

    public void onMapTouchDown(View v) {
        KeyboardUtil.hideKeyboard(this);
        mapView.requestFocus();
    }

    public void onMapTouchUp(View v) { }

    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
        switch(tag) {
        case TAG_WRITE_STORE:
            storeData = (Store)data.get(0);
            successDialog.show();
            break;
        }
    }

    public void requestExceptionCallBack(int tag, MatjiException e) {
        e.performExceptionHandling(context);
        titleBar.setCompletable(this);
    }

    public void onConfirmClick(SimpleDialog dialog) {
        if (dialog == successDialog) {
            session.getCurrentUser().setDiscoverStoreCount(session.getCurrentUser().getDiscoverStoreCount() + 1);
            Intent result = new Intent();
            result.putExtra(DATA_STORE, (Parcelable)storeData);
            setResult(RESULT_OK, result);
            finish();
        }
    }
}