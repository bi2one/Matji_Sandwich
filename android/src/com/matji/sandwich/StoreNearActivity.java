package com.matji.sandwich;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.widget.StoreNearContents;
import com.matji.sandwich.widget.title.HomeTitle;

public class StoreNearActivity extends BaseActivity implements StoreNearContents.OnClickListener {
	public static final int INTENT_WRITE_STORE = 1;
	public static final String DATA_STORE = "StoreNearActivity.store";
	public static final String LAT = "StoreNearActivity.lat";
	public static final String LNG = "StoreNearActivity.lng";	
	public static final int BASIC_LAT = 0;
	public static final int BASIC_LNG = 0;

	private int latNe;
	private int latSw;
	private int lngNe;
	private int lngSw;
	private int lat;
	private int lng; 
	private HomeTitle titleBar;
	private StoreNearContents storeContents;
	private Intent passedIntent;
	
	public int setMainViewId() {
		return R.id.activity_store_near;
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_near);

		titleBar = (HomeTitle) findViewById(R.id.activity_store_near_title);
		titleBar.setTitle(R.string.select_store_activity_near_title);

		storeContents = (StoreNearContents) findViewById(R.id.activity_store_near_contents);
		storeContents.init(this);
		storeContents.setOnClickListener(this);

		passedIntent = getIntent();
		latNe = passedIntent.getIntExtra(SelectMapPositionActivity.DATA_NE_LAT, SelectMapPositionActivity.BASIC_NE_LAT);
		latSw = passedIntent.getIntExtra(SelectMapPositionActivity.DATA_SW_LAT, SelectMapPositionActivity.BASIC_SW_LAT);
		lngNe = passedIntent.getIntExtra(SelectMapPositionActivity.DATA_NE_LNG, SelectMapPositionActivity.BASIC_NE_LNG);
		lngSw = passedIntent.getIntExtra(SelectMapPositionActivity.DATA_SW_LNG, SelectMapPositionActivity.BASIC_SW_LNG);
		lat = passedIntent.getIntExtra(SelectMapPositionActivity.DATA_LAT, SelectMapPositionActivity.BASIC_SEARCH_LOC_LAT);
		lng = passedIntent.getIntExtra(SelectMapPositionActivity.DATA_LNG, SelectMapPositionActivity.BASIC_SEARCH_LOC_LNG);
		storeContents.refresh(latNe, lngNe, latSw, lngSw);
	}

	public void onWriteStoreClick(View v) {
		Intent intent = new Intent(this, WriteStoreActivity.class);
		intent.putExtra(LAT, lat);
		intent.putExtra(LNG, lng);
		startActivityForResult(intent, INTENT_WRITE_STORE);
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK)
			return ;

		switch(requestCode) {
		case INTENT_WRITE_STORE:
			Store store = (Store)data.getParcelableExtra(WriteStoreActivity.DATA_STORE);
			Intent intent = new Intent(this, StoreMainActivity.class);
			intent.putExtra(StoreMainActivity.STORE, (Parcelable)store);
			startActivity(intent);
			finish();
			break;
		}
	}

}