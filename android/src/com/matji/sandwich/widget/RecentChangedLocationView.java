package com.matji.sandwich.widget;

import android.app.Activity;
import android.widget.LinearLayout;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.LayoutInflater;
// import android.view.View;
// import android.view.View.OnClickListener;
import android.util.AttributeSet;
import android.util.Log;

import com.matji.sandwich.R;
// import com.matji.sandwich.LoginActivity;
import com.matji.sandwich.Requestable;
import com.matji.sandwich.ChangeLocationActivity;
import com.matji.sandwich.data.LocationSearchToken;
import com.matji.sandwich.session.SessionRecentLocationUtil;

import java.util.ArrayList;

public class RecentChangedLocationView extends LinearLayout implements MultiRoundButtonView.OnItemClickListener {
    private Activity activity;
    private Context context;
    private MultiRoundButtonView locationButtons;
    private SessionRecentLocationUtil sessionUtil;
    private ArrayList<LocationSearchToken> recentSearchedList;

    public RecentChangedLocationView(Context context, AttributeSet attrs) {
	super(context, attrs);
	this.context = context;
	LayoutInflater.from(context).inflate(R.layout.recent_changed_location, this, true);
	locationButtons = (MultiRoundButtonView) findViewById(R.id.recent_changed_location_locations);
	locationButtons.init(this);
	sessionUtil = new SessionRecentLocationUtil(context);
	recentSearchedList = sessionUtil.getRecentList();

	if (recentSearchedList.size() != 0)
	    setVisibility(View.VISIBLE);
	else
	    setVisibility(View.GONE);
	
	for (LocationSearchToken token : recentSearchedList) {
	    Intent intent = new Intent();
	    intent.putExtra(ChangeLocationActivity.INTENT_KEY_LATITUDE, token.getLatitude());
	    intent.putExtra(ChangeLocationActivity.INTENT_KEY_LONGITUDE, token.getLongitude());
	    intent.putExtra(ChangeLocationActivity.INTENT_KEY_LOCATION_NAME, token.getSeed());
	    locationButtons.add(intent, token.getSeed());
	}
	locationButtons.updateView();
    }

    public void init(Activity activity) {
	this.activity = activity;
    }

    public void onItemClick(View v, Intent intent) {
	sessionUtil.push(intent.getStringExtra(ChangeLocationActivity.INTENT_KEY_LOCATION_NAME),
			 intent.getIntExtra(ChangeLocationActivity.INTENT_KEY_LATITUDE, 0),
			 intent.getIntExtra(ChangeLocationActivity.INTENT_KEY_LONGITUDE, 0));
	activity.setResult(Activity.RESULT_OK, intent);
    	activity.finish();
    }
}