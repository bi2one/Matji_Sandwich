package com.matji.sandwich;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.RelativeLayout;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.matji.sandwich.base.BaseMapActivity;
import com.matji.sandwich.map.MainMatjiMapView;
import com.matji.sandwich.session.SessionRecentLocationUtil;
import com.matji.sandwich.session.SessionMapUtil;
import com.matji.sandwich.widget.StoreMapNearListView;

public class MainMapActivity extends BaseMapActivity {
    private static final int REQUEST_CODE_LOCATION = 1;
    private static final int BASIC_SEARCH_LOC_LAT = 0;
    private static final int BASIC_SEARCH_LOC_LNG = 0;
    private Context context;
    private MainMatjiMapView mapView;
    private StoreMapNearListView storeListView;
    private TextView addressView;
    private View flipButton;
    private boolean currentViewIsMap;
    private SessionRecentLocationUtil sessionLocationUtil;
    private SessionMapUtil sessionMapUtil;

    private Drawable flipMapViewImage;
    private Drawable flipNearStoreImage;

    public int setMainViewId() {
	return R.id.activity_main_map;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_map);

        context = getApplicationContext();
        sessionLocationUtil = new SessionRecentLocationUtil(context);
	sessionMapUtil = new SessionMapUtil(context);
        addressView = (TextView)findViewById(R.id.map_title_bar_address);
        mapView = (MainMatjiMapView)findViewById(R.id.map_view);
        mapView.init(addressView, this, getMainView());
        storeListView = (StoreMapNearListView)findViewById(R.id.main_map_store_list);
        storeListView.init(addressView, this);

        flipButton = findViewById(R.id.map_title_bar_flip_button);
        currentViewIsMap = true;

        flipMapViewImage = getResources().getDrawable(R.drawable.icon_location_map_selector);
        flipNearStoreImage = getResources().getDrawable(R.drawable.icon_location_list_selector);
    }

    protected void onResume() {
        super.onResume();
        mapView.startMapCenterThread();
	mapView.setCenterNotAnimate(sessionMapUtil.getCenter());
    }

    protected void onNotFlowResume() {
        if (!currentViewIsMap) {
            storeListView.requestReload();
        }
    }

    protected void onPause() {
        super.onPause();
	// Log.d("=====", "mainmap activity pause");
        mapView.stopMapCenterThread();
    }

    public void onCurrentPositionClicked(View v) {
        if (currentViewIsMap) {
            mapView.moveToGpsCenter();
        } else {
            storeListView.moveToGpsCenter();
        }
    }

    public void onFlipClicked(View v) {
        if (currentViewIsMap) {
            showStoreListView();
        } else {
            showMapView();
        }
        currentViewIsMap = !currentViewIsMap;
    }

    public void onChangeLocationClicked(View v) {
        startActivityForResult(new Intent(context, ChangeLocationActivity.class), REQUEST_CODE_LOCATION);
    }

    public void onMoveToNearPostClicked(View v) {
        Intent intent = new Intent(context, MainTabActivity.class);
        intent.putExtra(MainTabActivity.IF_INDEX, MainTabActivity.IV_INDEX_POST);
        intent.putExtra(MainTabActivity.IF_SUB_INDEX, 1);
        startActivity(intent);
    }

    private void showMapView() {
        ((ImageButton) flipButton).setImageDrawable(flipNearStoreImage);
        mapView.setVisibility(View.VISIBLE);
        storeListView.setVisibility(View.GONE);
        mapView.setCenter(sessionMapUtil.getCenter());
    }

    private void showStoreListView() {
        ((ImageButton) flipButton).setImageDrawable(flipMapViewImage);
        storeListView.requestReload();
        mapView.setVisibility(View.GONE);
        storeListView.setVisibility(View.VISIBLE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
        case REQUEST_CODE_LOCATION:
            if (resultCode == Activity.RESULT_OK) {
                int searchedLat = data.getIntExtra(ChangeLocationActivity.INTENT_KEY_LATITUDE, BASIC_SEARCH_LOC_LAT);
                int searchedLng = data.getIntExtra(ChangeLocationActivity.INTENT_KEY_LONGITUDE, BASIC_SEARCH_LOC_LNG);
                String searchedLocation = data.getStringExtra(ChangeLocationActivity.INTENT_KEY_LOCATION_NAME);
                sessionLocationUtil.push(searchedLocation, searchedLat, searchedLng);
                if (currentViewIsMap) 
                    mapView.setCenter(new GeoPoint(searchedLat, searchedLng));
                else {
                    storeListView.setCenter(new GeoPoint(searchedLat, searchedLng));
                    storeListView.forceReload();
                }
            }
        }
    }
}
