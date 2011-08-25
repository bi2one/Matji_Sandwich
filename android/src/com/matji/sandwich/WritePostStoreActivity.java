package com.matji.sandwich;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.util.Log;

import com.google.android.maps.GeoPoint;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.util.KeyboardUtil;
import com.matji.sandwich.widget.WritePostStoreListView;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.adapter.WritePostStoreAdapter.StoreElement;
import com.matji.sandwich.session.SessionWritePostUtil;
import com.matji.sandwich.session.SessionMapUtil;

public class WritePostStoreActivity extends Activity implements OnItemClickListener {
    private static final int INTENT_CHANGE_LOCATION = 0;
    private Context context;
    private WritePostStoreListView listView;
    private TextView selectedText;
    private WritePostStoreActivityGroup parentActivity;
    private SessionWritePostUtil sessionUtil;
    private SessionMapUtil sessionMapUtil;
    private GeoPoint mapNePoint;
    private GeoPoint mapSwPoint;
    
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_write_post_store);

	context = getApplicationContext();
	sessionUtil = new SessionWritePostUtil(context);
	sessionMapUtil = new SessionMapUtil(context);
	mapNePoint = sessionMapUtil.getNEBound();
	mapSwPoint = sessionMapUtil.getSWBound();
	
	selectedText = (TextView)findViewById(R.id.activity_write_post_store_selected);
	listView = (WritePostStoreListView)findViewById(R.id.activity_write_post_store_listview);
	listView.init(this, mapNePoint, mapSwPoint);
	listView.setOnItemClickListener(this);
	parentActivity = (WritePostStoreActivityGroup)getParent();
    }

    protected void onResume() {
	super.onResume();
	listView.requestReload();
	KeyboardUtil.hideKeyboard(this);
    }

    public void onItemClick(AdapterView parent, View view, int position, long id) {
	StoreElement element = (StoreElement)view.getTag();
	Store store = element.getStore();
	
	selectedText.setText(store.getName());
	selectedText.setTag(store);
	sessionUtil.setStoreId(store.getId());
	parentActivity.onChecked(WritePostTabActivity.TAB_ID_STORE, true);
    }

    public void onChangeLocationClick(View view) {
	Intent intent = new Intent(this, GetMapPositionRadiusActivity.class);
	parentActivity.startChildActivity("GetMapPositionActivity", intent);
    }
    
    public void onNewStoreClick(View view) { }
}