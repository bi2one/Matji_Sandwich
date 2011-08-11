package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.matji.sandwich.http.request.PostHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.session.SessionMapUtil;

import com.google.android.maps.GeoPoint;

public class PostNearListView extends PostListView {
    private PostHttpRequest postRequest;
    private SessionMapUtil sessionUtil;
    private GeoPoint neBound;
    private GeoPoint swBound;

    public PostNearListView(Context context, AttributeSet attrs) {
	super(context, attrs);
	postRequest = new PostHttpRequest(context);
	sessionUtil = new SessionMapUtil(context);

	neBound = sessionUtil.getNEBound();
	swBound = sessionUtil.getSWBound();
    }

    public void requestReload() {
	neBound = sessionUtil.getNEBound();
	swBound = sessionUtil.getSWBound();

	super.forceReload();
    }

    public HttpRequest request() {
	postRequest.actionNearbyList((double) neBound.getLatitudeE6() / 1E6,
				     (double) swBound.getLatitudeE6() / 1E6,
				     (double) swBound.getLongitudeE6() / 1E6,
				     (double) neBound.getLongitudeE6() / 1E6,
				     getPage(), getLimit());
	return postRequest;
    }
}
