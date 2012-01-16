package com.matji.sandwich;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.matji.sandwich.base.BaseMapActivity;
import com.matji.sandwich.map.LocationMatjiMapView;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.title.CompletableTitle;

public class SelectMapPositionActivity extends BaseMapActivity implements CompletableTitle.Completable,
LocationMatjiMapView.OnClickListener {
	public static final String DATA_FROM_ACTIVITY = "SelectMapPositionActivity";
	public static final String DATA_NE_LAT = "SelectMapPositionActivity.ne_lat";
	public static final String DATA_NE_LNG = "SelectMapPositionActivity.ne_lng";
	public static final String DATA_SW_LAT = "SelectPositionActivity.sw_lat";
	public static final String DATA_SW_LNG = "SelectMapPositionActivity.sw_lng";
	public static final String DATA_LAT = "SelectPositionActivity.lat";
	public static final String DATA_LNG = "SelectMapPositionActivity.lng";
	public static final int BASIC_NE_LAT = 0;
	public static final int BASIC_NE_LNG = 0;
	public static final int BASIC_SW_LAT = 0;
	public static final int BASIC_SW_LNG = 0;
	public static final int BASIC_SEARCH_LOC_LAT = 0;
	public static final int BASIC_SEARCH_LOC_LNG = 0;

	private static final int INTENT_LOCATION = 0;
	private int searchedLat;
	private int searchedLng;
	private LocationMatjiMapView mapView;
	private CompletableTitle titleBar;
	private TextView footerText;
	private Context context;
	
		public int setMainViewId() {
		return R.id.activity_select_map_position;
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_map_position);
		
		context = getApplicationContext();
		titleBar = (CompletableTitle) findViewById(R.id.activity_select_map_position_title);
		titleBar.setTitle(R.string.select_map_position_activity_title);
		titleBar.setCompletable(this);
		
		mapView = (LocationMatjiMapView) findViewById(R.id.activity_select_map_position_mapview);
		mapView.init(this);
		mapView.setOnClickListener(this);
		
		footerText = (TextView) findViewById(R.id.activity_select_map_position_footer_text);
		footerText.setText(MatjiConstants.string(R.string.select_map_position_activity_footer_text));
	}

	protected void onResume() {
		super.onResume();
		mapView.startMapCenterThread();
	}

	protected void onStop() {
		mapView.stopMapCenterThread();
		mapView.stop();
		super.onStop();
	}

	public void complete() {
		GeoPoint neBound = mapView.getBound(LocationMatjiMapView.BoundType.MAP_BOUND_NE);
		GeoPoint swBound = mapView.getBound(LocationMatjiMapView.BoundType.MAP_BOUND_SW);
		Intent intent = new Intent(this, StoreNearActivity.class);
		intent.putExtra(DATA_FROM_ACTIVITY, "SelectMapPositionActivity");
		intent.putExtra(DATA_NE_LAT, neBound.getLatitudeE6());
		intent.putExtra(DATA_NE_LNG, neBound.getLongitudeE6());
		intent.putExtra(DATA_SW_LAT, swBound.getLatitudeE6());
		intent.putExtra(DATA_SW_LNG, swBound.getLongitudeE6());
		intent.putExtra(DATA_LAT, searchedLat);
		intent.putExtra(DATA_LNG, searchedLng);
		startActivity(intent);
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
			searchedLat = data.getIntExtra(ChangeLocationActivity.INTENT_KEY_LATITUDE, BASIC_SEARCH_LOC_LAT);
			searchedLng = data.getIntExtra(ChangeLocationActivity.INTENT_KEY_LONGITUDE, BASIC_SEARCH_LOC_LNG);
			mapView.setCenter(searchedLat, searchedLng);
		}
	}

}
