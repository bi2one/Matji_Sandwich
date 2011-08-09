package com.matji.sandwich;

import android.os.Bundle;
import android.widget.TextView;
import android.location.Location;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.PostNearListView;
import com.matji.sandwich.location.GpsManager;
import com.matji.sandwich.location.MatjiLocationListener;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.GeocodeHttpRequest;
import com.matji.sandwich.session.SessionMapUtil;

import com.google.android.maps.GeoPoint;

import java.util.ArrayList;

/**
 * 전체 Post 리스트를 보여주는 액티비티.
 * 
 * @author mozziluv
 *
 */
public class PostNearListActivity extends BaseActivity implements MatjiLocationListener,
								  Requestable {
    private static final int GPS_START_TAG = 0;
    private static final int GET_ADDRESS_TAG = 1;
    private PostNearListView listView;
    private TextView addressView;
    private boolean isStartActivity;
    private Context context;
    private GpsManager gpsManager;
    private HttpRequestManager requestManager;
    private GeocodeHttpRequest geocodeRequest;
    private SessionMapUtil sessionUtil;

    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_post_near_list);
	context = getApplicationContext();

	gpsManager = new GpsManager(context, this);
	sessionUtil = new SessionMapUtil(context);
	requestManager = HttpRequestManager.getInstance(context);
	geocodeRequest = new GeocodeHttpRequest(context);

	isStartActivity = false;
	listView = (PostNearListView) findViewById(R.id.post_near_list_view);
	listView.setActivity(this);
	listView.requestReload();
	setCenter(sessionUtil.getCenter());
    }

    private void setCenter(GeoPoint centerPoint) {
	geocodeRequest.actionReverseGeocodingByGeoPoint(centerPoint, sessionUtil.getCurrentCountry());
	requestManager.request(this, geocodeRequest, GET_ADDRESS_TAG, this);
    }

    protected void onResume() {
	super.onResume();
	if (!isStartActivity)
	    listView.requestReload();
    }

    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
	switch(tag) {
	case GET_ADDRESS_TAG:
	    // TODO
	}
    }
    
    public void onLocationChanged(int startFromTag, Location location) {
	
    }

    public void onLocationExceptionDelivered(int startFromTag, MatjiException e) {
	e.performExceptionHandling(context);
    }

    public void requestExceptionCallBack(int tag, MatjiException e) {
	e.performExceptionHandling(context);
    }

    public void onCurrentPositionClicked(View v) {
	
    }

    public void onChangeLocationClicked(View v) {
	
    }
}