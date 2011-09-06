package com.matji.sandwich;

import com.matji.sandwich.widget.BookmarkListView;
import com.matji.sandwich.widget.title.TitleContainer;
import com.matji.sandwich.util.KeyboardUtil;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.RegionHttpRequest;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Region;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONCodeMatjiException;
import com.matji.sandwich.base.BaseActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.content.Intent;
import android.content.Context;
import android.graphics.Color;

import java.util.ArrayList;
public class BookmarkListActivity extends BaseActivity implements Requestable {
    public static final String IF_LAT_NE = "BookmarkListActivity.lat_ne";
    public static final String IF_LNG_NE = "BookmarkListActivity.lng_ne";
    public static final String IF_LAT_SW = "BookmarkListActivity.lat_sw";
    public static final String IF_LNG_SW = "BookmarkListActivity.lng_sw";
    private static final double BASIC_LAT_NE = 100;
    private static final double BASIC_LNG_NE = 100;
    private static final double BASIC_LAT_SW = 100;
    private static final double BASIC_LNG_SW = 100;
    private static final int REGISTER_TAG = 0;
    private BookmarkListView listView;
    private EditText registerDesc;
    private View registerDescWrap;
    private Toast registerAlert;
    private Context mContext;
    private Intent prevIntent;
    private HttpRequestManager mRequestManager;
    private double latNe;
    private double lngNe;
    private double latSw;
    private double lngSw;

    public int setMainViewId() {
	return R.id.layout_main;
    }
    
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_bookmark_list);

	mContext = getApplicationContext();
	registerDesc = (EditText) findViewById(R.id.register_desc);
	registerDescWrap = findViewById(R.id.register_desc_wrap);
	registerAlert = Toast.makeText(mContext, R.string.bookmark_list_register_alert, Toast.LENGTH_SHORT);
	mRequestManager = HttpRequestManager.getInstance(mContext);
	listView = (BookmarkListView) findViewById(R.id.bookmark_list);
	listView.setCacheColorHint(Color.TRANSPARENT);
	prevIntent = getIntent();
	latNe = prevIntent.getDoubleExtra(IF_LAT_NE, BASIC_LAT_NE);
	lngNe = prevIntent.getDoubleExtra(IF_LNG_NE, BASIC_LNG_NE);
	latSw = prevIntent.getDoubleExtra(IF_LAT_SW, BASIC_LAT_SW);
	lngSw = prevIntent.getDoubleExtra(IF_LNG_SW, BASIC_LNG_SW);
	
	listView.setActivity(this);
	listView.requestReload();
    }
	
    protected void onResume() {
	super.onResume();
	listView.dataRefresh();
    }

    public void onRegisterBookmarkClick(View v) {
	if (registerDescWrap.getVisibility() == View.GONE) {
	    registerDescWrap.setVisibility(View.VISIBLE);
	    registerDesc.requestFocus();
	    KeyboardUtil.showKeyboard(this, registerDesc);
	} else {
	    hideDescWrap();
	}
    }

    private void hideDescWrap() {
	registerDesc.setText("");
	registerDescWrap.setVisibility(View.GONE);
	KeyboardUtil.hideKeyboard(this);
    }

    private boolean isValidateDescription() {
	return !registerDesc.getText().toString().trim().equals("");
    }

    public void onRegisterSubmitClick(View v) {
	if (isValidateDescription()) {
	    registerDescription(registerDesc.getText().toString());
	} else {
	    registerAlert.show();
	}
    }

    private void registerDescription(String desc) {
	RegionHttpRequest request = new RegionHttpRequest(mContext);
	request.actionBookmark(latSw, latNe, lngSw, lngNe, desc);
	mRequestManager.request(getMainView(), request, REGISTER_TAG, this);
    }

    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
	switch (tag) {
	case REGISTER_TAG:
	    hideDescWrap();
	    Region region = (Region) data.get(0);
	    listView.addItem(region);
	    break;
	}
    }

    public void requestExceptionCallBack(int tag, MatjiException e) {
	if (e instanceof JSONCodeMatjiException) {
	    String err = ((JSONCodeMatjiException)e).getErrMessage();
	    if (err.equals("Failed to save region")) {
		hideDescWrap();
		registerAlert.setText(R.string.exception_failed_to_save_region);
		registerAlert.show();
	    } else {
		e.performExceptionHandling(mContext);
	    }
	} else {
	    e.performExceptionHandling(mContext);
	}
    }
}