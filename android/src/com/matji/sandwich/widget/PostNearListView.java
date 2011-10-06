package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.google.android.maps.GeoPoint;
import com.matji.sandwich.R;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest;
import com.matji.sandwich.session.SessionMapUtil;
import com.matji.sandwich.util.MatjiConstants;

public class PostNearListView extends PostListView {
    private PostHttpRequest postRequest;
    private SessionMapUtil sessionUtil;
    private GeoPoint neBound;
    private GeoPoint swBound;
    private HttpRequestManager requestManager;

    public PostNearListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        requestManager = HttpRequestManager.getInstance();
        postRequest = new PostHttpRequest(context);
        sessionUtil = new SessionMapUtil(context);

        neBound = sessionUtil.getNEBound();
        swBound = sessionUtil.getSWBound();
        
        setSubtitle(MatjiConstants.string(R.string.subtitle_nearby_post));
    }

    public void requestReload() {
        if (!requestManager.isRunning()) {
            neBound = sessionUtil.getNEBound();
            swBound = sessionUtil.getSWBound();

            super.requestReload();
        }
    }

    public void forceReload() {
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
