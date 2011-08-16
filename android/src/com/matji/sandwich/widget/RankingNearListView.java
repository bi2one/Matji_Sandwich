package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.matji.sandwich.http.request.FollowingHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.session.SessionMapUtil;
import com.matji.sandwich.data.User;
import com.matji.sandwich.session.Session;

public class RankingNearListView extends RankingListView {
    private FollowingHttpRequest followingRequest;
    private SessionMapUtil sessionUtil;
    // private GeoPoint neBound;
    // private GeoPoint swBound;
    private User user;
    private Session session;

    public RankingNearListView(Context context, AttributeSet attrs) {
	super(context, attrs);
	followingRequest = new FollowingHttpRequest(context);
	session = Session.getInstance(context);
	user = session.getCurrentUser();
    }

    public HttpRequest request() {
	followingRequest.actionFollowingRankingList(user.getId(), getPage(), getLimit());
	return followingRequest;
    }
}
