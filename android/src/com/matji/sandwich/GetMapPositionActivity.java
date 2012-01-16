package com.matji.sandwich;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.maps.GeoPoint;
import com.matji.sandwich.base.BaseMapActivity;
import com.matji.sandwich.map.RadiusMatjiMapView;
import com.matji.sandwich.widget.title.CompletableTitle;

public class GetMapPositionActivity extends BaseMapActivity implements CompletableTitle.Completable,
RadiusMatjiMapView.OnClickListener {
	public static final String DATA_NE_LAT = "GetMapPositionActivity.ne_lat";
	public static final String DATA_NE_LNG = "GetMapPositionActivity.ne_lng";
	public static final String DATA_SW_LAT = "GetMapPositionActivity.sw_lat";
	public static final String DATA_SW_LNG = "GetMapPositionActivity.sw_lng";
	public static final int BASIC_NE_LAT = 0;
	public static final int BASIC_NE_LNG = 0;
	public static final int BASIC_SW_LAT = 0;
	public static final int BASIC_SW_LNG = 0;
	
	private static final int BASIC_SEARCH_LOC_LAT = 0;
	private static final int BASIC_SEARCH_LOC_LNG = 0;
	private static final int INTENT_LOCATION = 0;
	private Context context;
	private CompletableTitle titleBar;
	private RadiusMatjiMapView mapView;

	public int setMainViewId() {
		return R.id.activity_get_map_position;
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_map_position);

		context = getApplicationContext();
		titleBar = (CompletableTitle)findViewById(R.id.activity_get_map_position_title);
		titleBar.setTitle(R.string.get_map_position_title);
		titleBar.setCompletable(this);

		mapView = (RadiusMatjiMapView)findViewById(R.id.activity_get_map_position_mapview);
		mapView.init(this);

		mapView.setOnClickListener(this);
	}

	protected void onResume() {
		super.onResume();
		mapView.startMapCenterThread();
	}

	protected void onPause() {
		mapView.stopMapCenterThread();
		super.onPause();
	}

	public void complete() {
		GeoPoint neBound = mapView.getBound(RadiusMatjiMapView.BoundType.MAP_BOUND_NE);
		GeoPoint swBound = mapView.getBound(RadiusMatjiMapView.BoundType.MAP_BOUND_SW);
		Intent result = new Intent();
		result.putExtra(DATA_NE_LAT, neBound.getLatitudeE6());
		result.putExtra(DATA_NE_LNG, neBound.getLongitudeE6());
		result.putExtra(DATA_SW_LAT, swBound.getLatitudeE6());
		result.putExtra(DATA_SW_LNG, swBound.getLongitudeE6());
		setResult(RESULT_OK, result);
		finish();
	}

	public void onSubmitClick(View v, GeoPoint neBound, GeoPoint swBound) {
		startActivityForResult(new Intent(context, ChangeLocationActivity.class), INTENT_LOCATION);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK)
			return ;
		if (requestCode == INTENT_LOCATION) {
			int searchedLat = data.getIntExtra(ChangeLocationActivity.INTENT_KEY_LATITUDE, BASIC_SEARCH_LOC_LAT);
			int searchedLng = data.getIntExtra(ChangeLocationActivity.INTENT_KEY_LONGITUDE, BASIC_SEARCH_LOC_LNG);
			mapView.setCenter(searchedLat, searchedLng);
		}
	}
}