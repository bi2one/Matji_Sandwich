package com.matji.sandwich.widget;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.matji.sandwich.ChangeLocationActivity;
import com.matji.sandwich.R;
import com.matji.sandwich.Requestable;
import com.matji.sandwich.data.GeocodeAddress;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.GeocodeHttpRequest;
import com.matji.sandwich.session.SessionMapUtil;
import com.matji.sandwich.session.SessionRecentLocationUtil;
import com.matji.sandwich.widget.dialog.SimpleAlertDialog;

public class SearchResultView extends RelativeLayout implements MultiRoundButtonView.OnItemClickListener,
Requestable {
	private static final int REQUEST_GEOCODING = 1;
	private static final int RESULT_COUNT = 5;
	private Activity activity;
	private MultiRoundButtonView locationButtons;
	private TextView filledTextView;
	private TextView emptyTextView;
	private SessionMapUtil sessionMapUtil;
	private SessionRecentLocationUtil sessionUtil;
	private GeocodeHttpRequest geocodeRequest;
	private HttpRequestManager requestManager;
	private SimpleAlertDialog inputEmptyStringDialog;

	public SearchResultView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.search_result, this, true);
		locationButtons = (MultiRoundButtonView) findViewById(R.id.search_result_locations);
		locationButtons.init(this);
		filledTextView = (TextView)findViewById(R.id.search_result_filled);
		emptyTextView = (TextView)findViewById(R.id.search_result_empty);

		sessionUtil = new SessionRecentLocationUtil(context);
		sessionMapUtil = new SessionMapUtil(context);
		requestManager = HttpRequestManager.getInstance();
		geocodeRequest = new GeocodeHttpRequest(context);
	}

	public void search(String seed) {
		if (requestManager.isRunning())
			return ;

		String input = seed.trim();
		if (input.equals("")) {
			inputEmptyStringDialog.show();
			return ;
		}
		geocodeRequest.actionGeocoding(input, sessionMapUtil.getCurrentCountry());
		requestManager.request(getContext(), this, geocodeRequest, REQUEST_GEOCODING, this);
	}

	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		switch(tag) {
		case REQUEST_GEOCODING:
			if (data.size() != 0) {
				filledTextView.setVisibility(View.VISIBLE);
				emptyTextView.setVisibility(View.GONE);
			} else {
				filledTextView.setVisibility(View.GONE);
				emptyTextView.setVisibility(View.VISIBLE);
				return ;
			}
			int i = 0;
			locationButtons.clear();
			for (MatjiData addressData : data) {
				if (i >= RESULT_COUNT)
					break;

				i++;
				GeocodeAddress address = (GeocodeAddress)addressData;

				Intent intent = new Intent();
				intent.putExtra(ChangeLocationActivity.INTENT_KEY_LATITUDE, address.getLocationLat());
				intent.putExtra(ChangeLocationActivity.INTENT_KEY_LONGITUDE, address.getLocationLng());
				intent.putExtra(ChangeLocationActivity.INTENT_KEY_LOCATION_NAME, address.getShortenFormattedAddress());
				locationButtons.add(intent, address.getShortenFormattedAddress());
			}
			locationButtons.updateView();
		}
	}

	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.performExceptionHandling(getContext());
	}

	public void init(Activity activity) {
		this.activity = activity;
		inputEmptyStringDialog = new SimpleAlertDialog(activity, R.string.change_location_activity_input_empty_string);
	}

	public void onItemClick(View v, Intent intent) {
		sessionUtil.push(intent.getStringExtra(ChangeLocationActivity.INTENT_KEY_LOCATION_NAME),
				intent.getIntExtra(ChangeLocationActivity.INTENT_KEY_LATITUDE, 0),
				intent.getIntExtra(ChangeLocationActivity.INTENT_KEY_LONGITUDE, 0));
		activity.setResult(Activity.RESULT_OK, intent);
		activity.finish();
	}
}