package com.matji.sandwich;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.matji.sandwich.base.BaseMapActivity;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.location.GpsManager;
import com.matji.sandwich.map.MainMatjiMapView;
import com.matji.sandwich.overlay.OverlayClickListener;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.session.SessionMapUtil;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.StoreMapNearListView;
import com.matji.sandwich.widget.dialog.SimpleNotificationPopup;

public class MainMapActivity extends BaseMapActivity implements OverlayClickListener,
GpsManager.StartConfigListener {
    private static final int REQUEST_CODE_LOCATION = 1;
    private static final int REQUEST_CODE_STORE = 2;
    private static final int BASIC_SEARCH_LOC_LAT = 0;
    private static final int BASIC_SEARCH_LOC_LNG = 0;
    private static final int MAX_STORE_COUNT = 60;
    private MainMatjiMapView mapView;
    private StoreMapNearListView storeListView;
    private TextView addressView;
    private RelativeLayout addressWrapper;
    private View flipButton;
    private boolean currentViewIsMap;
    private boolean isFirst;
    private boolean isStartConfig;
    // private SessionRecentLocationUtil sessionLocationUtil;
    private Session session;
    private SessionMapUtil sessionMapUtil;
    private GeoPoint lastNeBound;
    private GeoPoint lastSwBound;
    // private GeoPoint lastCenter;

    private Drawable flipMapViewImage;
    private Drawable flipNearStoreImage;

    public int setMainViewId() {
        return R.id.activity_main_map;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_map);

        // sessionLocationUtil = new SessionRecentLocationUtil(context);
        session = Session.getInstance(this);
        sessionMapUtil = new SessionMapUtil(this);
        addressView = (TextView)findViewById(R.id.map_title_bar_address);
        addressWrapper = (RelativeLayout)findViewById(R.id.map_title_bar_address_wrapper);
        mapView = (MainMatjiMapView)findViewById(R.id.map_view);
        mapView.init(addressWrapper, addressView, this, getMainView());
        mapView.setOverlayClickListener(this);
        mapView.setStartConfigListener(this);
        mapView.setLimit(MAX_STORE_COUNT);
        storeListView = (StoreMapNearListView)findViewById(R.id.main_map_store_list);
        storeListView.init(addressWrapper, addressView, this);
        storeListView.setLimit(MAX_STORE_COUNT);
        storeListView.setStartConfigListener(this);
        mapView.setListView(storeListView);

        flipButton = findViewById(R.id.map_title_bar_flip_button);
        currentViewIsMap = true;

        flipMapViewImage = getResources().getDrawable(R.drawable.icon_location_map_selector);
        flipNearStoreImage = getResources().getDrawable(R.drawable.icon_location_list_selector);
        // mapView.startMapCenterThread();
        isFirst = true;
        isStartConfig = false;



        CharSequence cs = Html.fromHtml(
                MatjiConstants.string(R.string.popup_main_map),
                new ImageGetter(),
                null);

        new SimpleNotificationPopup(this, getClass().toString(), cs).show();
    }

    private class ImageGetter implements Html.ImageGetter {
        public Drawable getDrawable(String source) {
            source = source.replaceAll(".png", "");
            int id = getResources().getIdentifier(source, "drawable", getPackageName());

            Drawable d = getResources().getDrawable(id);
            int width = d.getIntrinsicWidth();
            int height = d.getIntrinsicHeight();
            d.setBounds(0, 0, width, height); //이미지 크기 설정

            return d;
        }
    };

    protected void onNotFlowResume() {
        if (!currentViewIsMap) {
            storeListView.requestReload();
        }

        // sessionMapUtil.setCenter(lastCenter);
        // mapView.setCenterNotAnimate(lastCenter);
        // mapView.requestMapCenterChanged(lastCenter);
        if (lastNeBound != null && lastSwBound != null) {
            sessionMapUtil.setBound(lastNeBound, lastSwBound);
            lastNeBound = null;
            lastSwBound = null;
        }

        if (!isFirst) {
            mapView.reload();
        }

        isFirst = false;
    }

    protected void onResume() {
        storeListView.dataRefresh();

        if (isStartConfig) {
            onCurrentPositionClicked(null);
            isStartConfig = false;
        }
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
        lastNeBound = sessionMapUtil.getNEBound();
        lastSwBound = sessionMapUtil.getSWBound();

        // lastCenter = mapView.getMapCenter();
        // mapView.stopMapCenterThread();

        // Log.d("=====", "mainmap activity pause");
        mapView.stopMapCenterThread();

        HttpRequestManager.getInstance().cancelAllTask();
    }

    public void onCurrentPositionClicked(View v) {
        mapView.stopMapCenterThread();
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
        startActivityForResult(new Intent(this, ChangeLocationActivity.class), REQUEST_CODE_LOCATION);
    }

    public void onMoveToNearPostClicked(View v) {
        sessionMapUtil.setCenter(mapView.getMapCenter());
        Intent intent = new Intent(this, MainTabActivity.class);
        intent.putExtra(MainTabActivity.IF_INDEX, MainTabActivity.IV_INDEX_POST);

        int subIndex = (session.isLogin())? 1 : 0;
        intent.putExtra(MainTabActivity.IF_SUB_INDEX, subIndex);
        startActivity(intent);
        setIsFlow(false);
    }

    private void showMapView() {
        ((ImageButton) flipButton).setImageDrawable(flipNearStoreImage);
        mapView.setVisibility(View.VISIBLE);
        storeListView.setVisibility(View.GONE);
        mapView.setCenter(sessionMapUtil.getCenter());
    }

    private void showStoreListView() {
        ((ImageButton) flipButton).setImageDrawable(flipMapViewImage);
        //        storeListView.forceReload();
        mapView.setVisibility(View.GONE);
        storeListView.setVisibility(View.VISIBLE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
        case REQUEST_CODE_LOCATION:
            if (resultCode == Activity.RESULT_OK) {
                int searchedLat = data.getIntExtra(ChangeLocationActivity.INTENT_KEY_LATITUDE, BASIC_SEARCH_LOC_LAT);
                int searchedLng = data.getIntExtra(ChangeLocationActivity.INTENT_KEY_LONGITUDE, BASIC_SEARCH_LOC_LNG);
                // String searchedLocation = data.getStringExtra(ChangeLocationActivity.INTENT_KEY_LOCATION_NAME);
                // sessionLocationUtil.push(searchedLocation, searchedLat, searchedLng);
                if (currentViewIsMap)
                    mapView.setCenter(new GeoPoint(searchedLat, searchedLng));
                else {
                    //                    storeListView.setCenter(new GeoPoint(searchedLat, searchedLng));
                    //                    storeListView.forceReload();
                }
            }
            break;
        case REQUEST_CODE_STORE:
            if (resultCode == Activity.RESULT_OK) {
                Store store = (Store)data.getParcelableExtra(StoreMainActivity.STORE);
                mapView.updatePopupOverlay(store);
            }
            break;
        }
    }

    public void onOverlayClick(View v, Object data) {
        Intent intent = new Intent(this, StoreMainActivity.class);
        intent.putExtra(StoreMainActivity.STORE, (Parcelable) data);
        startActivityForResult(intent, REQUEST_CODE_STORE);
    }

    public void onStartConfig(GpsManager gpsManager) {
        isStartConfig = true;
    }
}
