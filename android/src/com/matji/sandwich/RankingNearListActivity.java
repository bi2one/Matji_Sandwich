package com.matji.sandwich;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.data.GeocodeAddress;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.GeocodeHttpRequest;
import com.matji.sandwich.http.spinner.SpinnerFactory;
import com.matji.sandwich.location.GpsManager;
import com.matji.sandwich.location.MatjiLocationListener;
import com.matji.sandwich.session.SessionMapUtil;
import com.matji.sandwich.util.GeocodeUtil;
import com.matji.sandwich.util.adapter.LocationToGeoPointAdapter;
import com.matji.sandwich.widget.RankingListView;

/**
 * 주변 Ranking 리스트를 보여주는 액티비티.
 * 
 * @author bizone
 */
public class RankingNearListActivity extends BaseActivity implements MatjiLocationListener,
Requestable,
ActivityStartable {
	private static final int GPS_START_TAG = 0;
	private static final int GET_ADDRESS_TAG = 0;
	private Context context;
	private RankingListView listView;
	private GpsManager gpsManager;
	private HttpRequestManager requestManager;
	private SessionMapUtil sessionUtil;
	private RelativeLayout addressWrapper;
	private TextView addressView;
	private GeocodeHttpRequest geocodeRequest;
	private Location prevLocation;
	private boolean isFirst = true;

	public int setMainViewId() {
		return R.id.activity_ranking_near_list;
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ranking_near_list);

		context = getApplicationContext();
		gpsManager = new GpsManager(this, this);
		sessionUtil = new SessionMapUtil(context);
		requestManager = HttpRequestManager.getInstance();
		geocodeRequest = new GeocodeHttpRequest(context);

		addressView = (TextView)findViewById(R.id.location_title_bar_address);
		addressWrapper = (RelativeLayout)findViewById(R.id.location_title_bar_address_wrapper);
		listView = (RankingListView) findViewById(R.id.ranking_near_list_view);
		listView.setActivity(this);
		setCenter(sessionUtil.getCenter());
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case USER_MAIN_ACTIVITY:
			setIsFlow(true);
		}
	}

	private void setCenter(GeoPoint centerPoint) {
		geocodeRequest.actionReverseGeocodingByGeoPoint(centerPoint, sessionUtil.getCurrentCountry());
		requestManager.request(getApplicationContext(), addressWrapper, SpinnerFactory.SpinnerType.SMALL, geocodeRequest, GET_ADDRESS_TAG, this);
		sessionUtil.setCenter(centerPoint);
		sessionUtil.setNearBound(centerPoint);
		listView.requestReload();
	}

	protected void onNotFlowResume() {
		if (!isFirst) {
			setCenter(sessionUtil.getCenter());
		}
		isFirst = false;
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
		if (!gpsManager.isRunning()) {
			gpsManager.start(GPS_START_TAG, getParent());
		}
	}

	public void onChangeLocationClicked(View v) {
		((BaseTabActivity) getParent()).tabStartActivityForResult(new Intent(context, ChangeLocationActivity.class),
				CHANGE_LOCATION_ACTIVITY,
				this);
	}

	public void onMoveToMapViewClicked(View v) {
		Intent intent = new Intent(context, MainTabActivity.class);
		intent.putExtra(MainTabActivity.IF_INDEX, MainTabActivity.IV_INDEX_MAP);
		startActivity(intent);
		setIsFlow(false);
	}

	@Override
	public void activityResultDelivered(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK)
			return ;

		switch(requestCode) {
		case USER_MAIN_ACTIVITY:
			setIsFlow(true);
			break;
		case CHANGE_LOCATION_ACTIVITY:
			setIsFlow(true);
			int centerLat = data.getIntExtra(ChangeLocationActivity.INTENT_KEY_LATITUDE,
					ChangeLocationActivity.BASIC_SEARCH_LOC_LAT);
			int centerLng = data.getIntExtra(ChangeLocationActivity.INTENT_KEY_LONGITUDE,
					ChangeLocationActivity.BASIC_SEARCH_LOC_LNG);
			setCenter(new GeoPoint(centerLat, centerLng));
			break;
		}
	}

	@Override
	public void setIsFlow(boolean isFlow) {
		if (getParent() instanceof BaseTabActivity) {
			((BaseTabActivity) getParent()).setIsFlow(true);
		}
		super.setIsFlow(isFlow);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_reload:
			listView.refresh();
			return true;
		}
		return false;
	}
}