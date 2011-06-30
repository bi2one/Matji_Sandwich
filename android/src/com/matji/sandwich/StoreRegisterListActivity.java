package com.matji.sandwich;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.content.Intent;
import android.content.Context;
import android.widget.Button;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.StoreNearRadiusListView;

public class StoreRegisterListActivity extends BaseActivity {
    private static final int GET_POSITION_TAG = 0;
    private static final double BASIC_CENTER_LATITUDE = 10;
    private static final double BASIC_CENTER_LONGITUDE = 10;
    
    private StoreNearRadiusListView storeList;
    private Context mContext;
    private double mCenterLat;
    private double mCenterLng;
    private String mCenterAddr;
    
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_store_register_list);
    	mContext = getApplicationContext();
    	storeList = (StoreNearRadiusListView)findViewById(R.id.store_list);
    	storeList.setActivity(this);
    	Intent getPositionIntent = new Intent(mContext, GetMapPositionActivity.class);
    	startActivityForResult(getPositionIntent, GET_POSITION_TAG);
    }

    protected void onResume() {
    	super.onResume();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);

	if (resultCode == Activity.RESULT_OK) {
	    switch(requestCode) {
	    case GET_POSITION_TAG:
		mCenterLat = data.getDoubleExtra(GetMapPositionActivity.RETURN_KEY_LATITUDE, BASIC_CENTER_LATITUDE);
		mCenterLng = data.getDoubleExtra(GetMapPositionActivity.RETURN_KEY_LONGITUDE, BASIC_CENTER_LONGITUDE);
		mCenterAddr = data.getStringExtra(GetMapPositionActivity.RETURN_KEY_ADDRESS);
		
		storeList.setCenter(mCenterLat, mCenterLng);
		storeList.requestConditionally();
		break;
	    default:
	    }
	}
    }

    public void onNewStoreClicked(View v) {
	Intent registerIntent = new Intent(mContext, StoreRegisterActivity.class);
	registerIntent.putExtra(StoreRegisterActivity.RETURN_KEY_CENTER_LAT, mCenterLat);
	registerIntent.putExtra(StoreRegisterActivity.RETURN_KEY_CENTER_LNG, mCenterLng);
	registerIntent.putExtra(StoreRegisterActivity.RETURN_KEY_ADDRESS, mCenterAddr);
	startActivity(registerIntent);
    }

    @Override
	protected String titleBarText() {
	return "StoreRegisterListActivity";
    }

    @Override
	protected boolean setTitleBarButton(Button button) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
	protected void onTitleBarItemClicked(View view) {
	// TODO Auto-generated method stub
    }
}
