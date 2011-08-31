package com.matji.sandwich;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.location.Location;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.util.Log;

import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.widget.PostNearListView;
import com.matji.sandwich.location.GpsManager;
import com.matji.sandwich.location.MatjiLocationListener;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.GeocodeAddress;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.GeocodeHttpRequest;
import com.matji.sandwich.session.SessionMapUtil;
import com.matji.sandwich.session.SessionRecentLocationUtil;
import com.matji.sandwich.util.GeocodeUtil;
import com.matji.sandwich.util.adapter.LocationToGeoPointAdapter;

import com.google.android.maps.GeoPoint;

import java.util.ArrayList;

/**
 * 전체 Post 리스트를 보여주는 액티비티.
 * 
 * @author mozziluv
 *
 */
public class PostNearListActivity extends BaseActivity implements MatjiLocationListener,
Requestable,
ActivityStartable {
    private static final int GPS_START_TAG = 0;
    private static final int GET_ADDRESS_TAG = 1;
    private static final int REQUEST_CODE_LOCATION = 1;
    private static final int BASIC_SEARCH_LOC_LAT = 0;
    private static final int BASIC_SEARCH_LOC_LNG = 0;
    private PostNearListView listView;
    private TextView addressView;
    private boolean isStartActivity;
    private Context context;
    private GpsManager gpsManager;
    private HttpRequestManager requestManager;
    private GeocodeHttpRequest geocodeRequest;
    private SessionMapUtil sessionUtil;
    private SessionRecentLocationUtil sessionLocationUtil;
    private Location prevLocation;

    public int setMainViewId() {
        return R.id.activity_post_near_list;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_near_list);
        context = getApplicationContext();

        gpsManager = new GpsManager(context, this);
        sessionUtil = new SessionMapUtil(context);
        sessionLocationUtil = new SessionRecentLocationUtil(context);
        requestManager = HttpRequestManager.getInstance(context);
        geocodeRequest = new GeocodeHttpRequest(context);
        addressView = (TextView)findViewById(R.id.location_title_bar_address);

        isStartActivity = false;
        listView = (PostNearListView) findViewById(R.id.post_near_list_view);
        listView.setActivity(this);
    }

    private void setCenter(GeoPoint centerPoint) {
        geocodeRequest.actionReverseGeocodingByGeoPoint(centerPoint, sessionUtil.getCurrentCountry());
        requestManager.request(getMainView(), geocodeRequest, GET_ADDRESS_TAG, this);
        sessionUtil.setCenter(centerPoint);
        listView.requestReload();
    }

    @Override
    protected void onNotFlowResume() {
        // TODO Auto-generated method stub
        super.onNotFlowResume();

        if (!isStartActivity) {
            setCenter(sessionUtil.getCenter());
        } else {
            isStartActivity = false;
        }
    }
    
    @Override
    protected void onFlowResume() {
        // TODO Auto-generated method stub
        super.onFlowResume();
    }

    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
        switch(tag) {
        case GET_ADDRESS_TAG:
            GeocodeAddress geocodeAddress = GeocodeUtil.approximateAddress(data,
                    sessionUtil.getNEBound(),
                    sessionUtil.getSWBound());
            addressView.setText(geocodeAddress.getShortenFormattedAddress());
        }
    }

    public void onLocationChanged(int startFromTag, Location location) {
        if (prevLocation != null) {
            if (prevLocation.getAccuracy() >= location.getAccuracy()) {
                GeoPoint prevPoint = new LocationToGeoPointAdapter(prevLocation);
                sessionUtil.setNearBound(prevPoint);
                setCenter(prevPoint);
                gpsManager.stop();
            }
        }

        prevLocation = location;
    }

    public void onLocationExceptionDelivered(int startFromTag, MatjiException e) {
        e.performExceptionHandling(context);
    }

    public void requestExceptionCallBack(int tag, MatjiException e) {
        e.performExceptionHandling(context);
    }

    public void onCurrentPositionClicked(View v) {
        gpsManager.start(GPS_START_TAG);
    }

    public void onChangeLocationClicked(View v) {
        isStartActivity = true;
        ((PostTabActivity) getParent()).tabStartActivityForResult(new Intent(context, ChangeLocationActivity.class),
                REQUEST_CODE_LOCATION,
                this);
    }

    public void onMoveToMapViewClicked(View v) {
        Intent intent = new Intent(context, MainTabActivity.class);
        intent.putExtra(MainTabActivity.IF_INDEX, MainTabActivity.IV_INDEX_MAP);
        startActivity(intent);
    }

    public void activityResultDelivered(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
        case REQUEST_CODE_LOCATION:
            if (resultCode == Activity.RESULT_OK) {
                int searchedLat = data.getIntExtra(ChangeLocationActivity.INTENT_KEY_LATITUDE,
                        ChangeLocationActivity.BASIC_SEARCH_LOC_LAT);
                int searchedLng = data.getIntExtra(ChangeLocationActivity.INTENT_KEY_LONGITUDE,
                        ChangeLocationActivity.BASIC_SEARCH_LOC_LNG);
                String searchedLocation = data.getStringExtra(ChangeLocationActivity.INTENT_KEY_LOCATION_NAME);
                sessionLocationUtil.push(searchedLocation, searchedLat, searchedLng);

                sessionUtil.setNearBound(new GeoPoint(searchedLat, searchedLng));
                setCenter(new GeoPoint(searchedLat, searchedLng));
            }
            break;
        case POST_ACTIVITY:
            if (resultCode == Activity.RESULT_OK) {
                ArrayList<MatjiData> posts = data.getParcelableArrayListExtra(PostActivity.POSTS);
                listView.setPosts(posts);
                listView.dataRefresh();
                if (getParent() instanceof BaseTabActivity) {
                    ((BaseTabActivity) getParent()).setIsFlow(true);
                }
                setIsFlow(true);
            }
            break;
        }        
    }
}