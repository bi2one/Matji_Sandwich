package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.data.provider.PreferenceProvider;
import com.matji.sandwich.http.request.StoreHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.session.Session;

public class StoreMapNearListView extends StoreListView {
    private StoreHttpRequest storeRequest;
    private int BASIC_LATITUDE_SW = 37000000;
    private int BASIC_LATITUDE_NE = 126600000;
    private int BASIC_LONGITUDE_SW = 37400000;
    private int BASIC_LONGITUDE_NE = 126900000;
    private int latSW;
    private int latNE;
    private int lngSW;
    private int lngNE;
    private boolean isFilled;
    private PreferenceProvider provider;
    private Session session;

    public StoreMapNearListView(Context context, AttributeSet attrs) {
	super(context, attrs);
	storeRequest = new StoreHttpRequest(context);
	session = Session.getInstance(context);
	provider = session.getPreferenceProvider();
	isFilled = false;
    }

    public void fillVariables() {
	isFilled = true;
	
	latSW = provider.getInt(Session.MAP_BOUND_LATITUDE_SW, BASIC_LATITUDE_SW);
	latNE = provider.getInt(Session.MAP_BOUND_LATITUDE_NE, BASIC_LATITUDE_NE);
	lngSW = provider.getInt(Session.MAP_BOUND_LONGITUDE_SW, BASIC_LONGITUDE_SW);
	lngNE = provider.getInt(Session.MAP_BOUND_LONGITUDE_NE, BASIC_LONGITUDE_NE);
    }

    public HttpRequest request() {
	if (isFilled) {
	    isFilled = false;
	    initValue();
	} else {
	    // 위치 정보를 새로 받아오기 전에 listview를 call한 경우...
	    // 기존 위치 정보 없이는 절대 이 곳으로 이동할 수
	    // 없다.(MainMapActivity에서 위치가 있을 때만 이
	    // ListView를 최초로 call하게 되어있다.)
	}

	storeRequest.actionNearbyList((double) latSW / 1E6,
				      (double) latNE / 1E6,
				      (double) lngSW / 1E6,
				      (double) lngNE / 1E6,
				      getPage(), getLimit());

	return storeRequest;
    }
}