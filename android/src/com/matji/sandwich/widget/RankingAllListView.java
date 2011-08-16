package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.matji.sandwich.http.request.UserHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.session.SessionMapUtil;
import com.matji.sandwich.data.User;
import com.matji.sandwich.session.Session;

public class RankingAllListView extends RankingListView {
    private UserHttpRequest userRequest;

    public RankingAllListView(Context context, AttributeSet attrs) {
	super(context, attrs);
	userRequest = new UserHttpRequest(context);
    }

    public HttpRequest request() {
	userRequest.actionRankingList(getPage(), getLimit());
	return userRequest;
    }
}
