package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.http.request.FollowingHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.session.Session;

public class RankingFriendListView extends RankingListView {
    private FollowingHttpRequest followingRequest;
    private Session session;

    public RankingFriendListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        followingRequest = new FollowingHttpRequest(context);
        session = Session.getInstance(context);
    }

    public HttpRequest request() {
        if (session.getCurrentUser() != null) {
            followingRequest.actionFollowingRankingList(session.getCurrentUser().getId(), getPage(), getLimit());
        }
        return followingRequest;
    }
}
