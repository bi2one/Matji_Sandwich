package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.http.request.StoreHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.session.Session;

public class StoreNearListView extends StoreListView {
    private StoreHttpRequest storeRequest;
    private int BASIC_LATITUDE_SW = 37000000;
    private int BASIC_LATITUDE_NE = 126600000;
    private int BASIC_LONGITUDE_SW = 37400000;
    private int BASIC_LONGITUDE_NE = 126900000;
    private int latSW;
    private int latNE;
    private int lngSW;
    private int lngNE;
    
    private Session session;

    public StoreNearListView(Context context, AttributeSet attrs) {
	super(context, attrs);
	storeRequest = new StoreHttpRequest(context);
	session = Session.getInstance(context);

	latSW = session.getPreferenceProvider().getInt(Session.MAP_BOUND_LATITUDE_SW, BASIC_LATITUDE_SW);
	latNE = session.getPreferenceProvider().getInt(Session.MAP_BOUND_LATITUDE_NE, BASIC_LATITUDE_NE);
	lngSW = session.getPreferenceProvider().getInt(Session.MAP_BOUND_LONGITUDE_SW, BASIC_LONGITUDE_SW);
	lngNE = session.getPreferenceProvider().getInt(Session.MAP_BOUND_LONGITUDE_NE, BASIC_LONGITUDE_NE);
    }

    public HttpRequest request() {
	storeRequest.actionNearbyList((double) latSW / 1E6,
				      (double) latNE / 1E6,
				      (double) lngSW / 1E6,
				      (double) lngNE / 1E6,
				      getPage(), getLimit());
	return storeRequest;
    }
}