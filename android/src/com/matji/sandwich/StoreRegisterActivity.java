package com.matji.sandwich;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.StoreHttpRequest;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.RegistStoreNameInvalidMatjiException;

import java.util.ArrayList;

public class StoreRegisterActivity extends BaseActivity implements Requestable {
    public static final String RETURN_KEY_CENTER_LAT = "StoreRegisterActivity.center_latitude";
    public static final String RETURN_KEY_CENTER_LNG = "StoreRegisterActivity.center_longitude";
    public static final String RETURN_KEY_ADDRESS = "StoreRegisterActivity.address";
    public static final double BASIC_CENTER_LATITUDE = 10;
    public static final double BASIC_CENTER_LONGITUDE = 10;
    private static final int GET_POSITION_TAG = 0;
    private static final int REGIST_STORE = 0;

    private Context mContext;
    private Intent deliveredIntent;
    private HttpRequestManager mRequestManager;
    private double centerLatitude;
    private double centerLongitude;
    private String centerAddress;

    private TextView addressText;
    private EditText addAddressText;
    private EditText nameText;
    private EditText telText;
    private EditText websiteText;
    private EditText coverText;
    
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_store_register);
	mContext = getApplicationContext();
	mRequestManager = new HttpRequestManager(mContext, this);
	deliveredIntent = getIntent();
	centerLatitude = deliveredIntent.getDoubleExtra(RETURN_KEY_CENTER_LAT, BASIC_CENTER_LATITUDE);
	centerLongitude = deliveredIntent.getDoubleExtra(RETURN_KEY_CENTER_LNG, BASIC_CENTER_LONGITUDE);
	centerAddress = deliveredIntent.getStringExtra(RETURN_KEY_ADDRESS);

	addressText = (TextView)findViewById(R.id.address_text);
	addAddressText = (EditText)findViewById(R.id.add_address_textbox);
	nameText = (EditText)findViewById(R.id.name_textbox);
	telText = (EditText)findViewById(R.id.tel_textbox);
	websiteText = (EditText)findViewById(R.id.website_textbox);
	coverText = (EditText)findViewById(R.id.cover_textbox);
	addressText.setText(centerAddress);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);

	if (resultCode == Activity.RESULT_OK) {
	    switch(requestCode) {
	    case GET_POSITION_TAG:
		centerLatitude = data.getDoubleExtra(GetMapPositionActivity.RETURN_KEY_LATITUDE, BASIC_CENTER_LATITUDE);
		centerLongitude = data.getDoubleExtra(GetMapPositionActivity.RETURN_KEY_LONGITUDE, BASIC_CENTER_LONGITUDE);
		centerAddress = data.getStringExtra(GetMapPositionActivity.RETURN_KEY_ADDRESS);
		
		addressText.setText(centerAddress);
		break;
	    default:
	    }
	}
    }

    public void requestExceptionCallBack(int tag, MatjiException e) {
	e.performExceptionHandling(mContext);
    }

    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
	switch (tag) {
	case REGIST_STORE:
	    Toast.makeText(mContext, R.string.store_register_ok, Toast.LENGTH_SHORT).show();
	    Store store = (Store)data.get(0);
	    Intent intent = new Intent(mContext, StoreTabActivity.class);
	    startActivityWithMatjiData(intent, store);
	    break;
	}
    }

    public void onChangeAddressClick(View v) {
	Intent getPositionIntent = new Intent(mContext, GetMapPositionActivity.class);
	startActivityForResult(getPositionIntent, GET_POSITION_TAG);
    }

    public void onRegisterSubmitClick(View v) {
	try {
	    isValidValues();
	    StoreHttpRequest request = new StoreHttpRequest(mContext);
	    request.actionNew(nameText.getText().toString(),
			      centerAddress,
			      centerLatitude,
			      centerLongitude,
			      addAddressText.getText().toString(),
			      telText.getText().toString(),
			      websiteText.getText().toString(),
			      coverText.getText().toString());
	    mRequestManager.request(this, request, REGIST_STORE);
	} catch(MatjiException e) {
	    e.performExceptionHandling(mContext);
	}
    }

    private void isValidValues() throws MatjiException {
	String name = nameText.getText().toString();
	String addAddress = addAddressText.getText().toString();
	String tel = telText.getText().toString();
	String website = websiteText.getText().toString();
	String cover = coverText.getText().toString();

	if (name.trim().equals("")) {
	    throw new RegistStoreNameInvalidMatjiException();
	}
    }

    @Override
	protected String titleBarText() {
	return "StoreRegisterActivity";
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