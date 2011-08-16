package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.matji.sandwich.http.request.UserHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.session.SessionMapUtil;
import com.matji.sandwich.data.User;
import com.matji.sandwich.session.Session;

import com.google.android.maps.GeoPoint;

public class RankingNearListView extends RankingListView {
    private UserHttpRequest userRequest;
    private SessionMapUtil sessionUtil;
    private GeoPoint neBound;
    private GeoPoint swBound;

    public RankingNearListView(Context context, AttributeSet attrs) {
	super(context, attrs);
	userRequest = new UserHttpRequest(context);
	sessionUtil = new SessionMapUtil(context);

	loadBounds();
    }

    public void requestReload() {
	loadBounds();
	super.forceReload();
    }

    public HttpRequest request() {
	userRequest.actionNearByRankingList((double) swBound.getLatitudeE6() / 1E6,
					    (double) neBound.getLatitudeE6() / 1E6,
					    (double) swBound.getLongitudeE6() / 1E6,
					    (double) neBound.getLongitudeE6() / 1E6,
					    getPage(), getLimit());
	return userRequest;
    }

    private void loadBounds() {
	neBound = sessionUtil.getNEBound();
	swBound = sessionUtil.getSWBound();
    }
}
