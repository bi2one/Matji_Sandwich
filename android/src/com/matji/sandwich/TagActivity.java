package com.matji.sandwich;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.GeocodeAddress;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.GeocodeHttpRequest;
import com.matji.sandwich.session.SessionMapUtil;
import com.matji.sandwich.widget.RecentChangedLocationView;

public class TagActivity extends BaseActivity implements Requestable, TextView.OnEditorActionListener {
    public static final String INTENT_KEY_LATITUDE = "ChangeLocationActivity.intent_key_latitude";
    public static final String INTENT_KEY_LONGITUDE = "ChangeLocationActivity.intent_key_longitude";
    public static final String INTENT_KEY_LOCATION_NAME = "ChangeLocationActivity.intent_key_location_name";
    public static final int BASIC_SEARCH_LOC_LAT = 0;
    public static final int BASIC_SEARCH_LOC_LNG = 0;
    private static final int REQUEST_GEOCODING = 1;
    private Context context;
    private EditText locationInput;
    private RecentChangedLocationView locationView;
    private HttpRequestManager requestManager;
    private GeocodeHttpRequest request;
    private AlertDialog inputEmptyStringDialog;
    private SessionMapUtil sessionMapUtil;
    private String lastSearchSeed;

    public int setMainViewId() {
	return R.id.activity_change_location;
    }
    
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_change_location);
	context = getApplicationContext();

	locationInput = (EditText)findViewById(R.id.activity_change_location_input);
	locationInput.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
	locationInput.setOnEditorActionListener(this);
	locationView = (RecentChangedLocationView)findViewById(R.id.activity_change_location_recent_view);
	locationView.init(this);
	requestManager = HttpRequestManager.getInstance();
	request = new GeocodeHttpRequest(context);
	sessionMapUtil = new SessionMapUtil(context);

	inputEmptyStringDialog = new AlertDialog.Builder(this)
	    .setMessage(R.string.change_location_activity_input_empty_string)
	    .setNeutralButton(R.string.default_string_confirm, null)
	    .create();
    }

    public void searchLocation() {
	if (requestManager.isRunning()) {
	    return ;
	}
	String input = locationInput.getText().toString().trim();
	if (input.equals("")) {
	    inputEmptyStringDialog.show();
	    return ;
	}

	lastSearchSeed = input;
	request.actionGeocoding(input, sessionMapUtil.getCurrentCountry());
	requestManager.request(getApplicationContext(), getMainView(), request, REQUEST_GEOCODING, this);
    }

    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
	switch(tag) {
	case REQUEST_GEOCODING:
	    GeocodeAddress address = (GeocodeAddress)data.get(0);
	    Intent result = new Intent();
	    int addrLat = address.getLocationLat();
	    int addrLng = address.getLocationLng();
	    result.putExtra(INTENT_KEY_LATITUDE, addrLat);
	    result.putExtra(INTENT_KEY_LONGITUDE, addrLng);
	    result.putExtra(INTENT_KEY_LOCATION_NAME, lastSearchSeed);
	    setResult(Activity.RESULT_OK, result);
	    finish();
	}
    }

    public void requestExceptionCallBack(int tag, MatjiException e) {
	e.performExceptionHandling(context);
    }

    public boolean onEditorAction(TextView v, int action, KeyEvent e) {
	if ((action == EditorInfo.IME_ACTION_DONE) ||
	    (e != null && e.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
	    searchLocation();
	}
	return false;
    }

    public void onCancelClicked(View v) {
	
    }
}