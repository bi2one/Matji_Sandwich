package com.matji.sandwich;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.view.View;
import android.content.Context;
import android.content.Intent;
import android.content.DialogInterface;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.GeocodeAddress;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.GeocodeHttpRequest;
import com.matji.sandwich.widget.RecentChangedLocationView;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.session.SessionMapUtil;

import java.util.ArrayList;

public class ChangeLocationActivity extends BaseActivity implements Requestable {
    public static final int REQUEST_CODE_LOCATION = 1;
    public static final String INTENT_KEY_LATITUDE = "ChangeLocationActivity.intent_key_latitude";
    public static final String INTENT_KEY_LONGITUDE = "ChangeLocationActivity.intent_key_longitude";
    private static final int REQUEST_GEOCODING = 1;
    private Context context;
    private EditText locationInput;
    private RecentChangedLocationView locationView;
    private HttpRequestManager requestManager;
    private GeocodeHttpRequest request;
    private AlertDialog inputEmptyStringDialog;
    private SessionMapUtil sessionUtil;
    
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_change_location);
	context = getApplicationContext();

	locationInput = (EditText)findViewById(R.id.activity_change_location_input);
	locationView = (RecentChangedLocationView)findViewById(R.id.activity_change_location_recent_view);
	requestManager = HttpRequestManager.getInstance(context);
	request = new GeocodeHttpRequest(context);
	sessionUtil = new SessionMapUtil(context);

	inputEmptyStringDialog = new AlertDialog.Builder(this)
	    .setMessage(R.string.change_location_activity_input_empty_string)
	    .setNeutralButton(R.string.default_string_confirm, null)
	    .create();
    }

    public void onConfirmClicked(View v) {
	if (requestManager.isRunning()) {
	    return ;
	}
	String input = locationInput.getText().toString().trim();
	if (input.equals("")) {
	    inputEmptyStringDialog.show();
	    return ;
	}
	
	request.actionGeocoding(input, sessionUtil.getCurrentCountry());
	requestManager.request(this, request, REQUEST_GEOCODING, this);
    }

    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
	switch(tag) {
	case REQUEST_GEOCODING:
	    GeocodeAddress address = (GeocodeAddress)data.get(0);
	    Intent result = new Intent();
	    result.putExtra(INTENT_KEY_LATITUDE, address.getLocationLat());
	    result.putExtra(INTENT_KEY_LONGITUDE, address.getLocationLng());
	    setResult(REQUEST_CODE_LOCATION, result);
	    finish();
	}
    }

    public void requestExceptionCallBack(int tag, MatjiException e) {
	e.performExceptionHandling(context);
    }
}