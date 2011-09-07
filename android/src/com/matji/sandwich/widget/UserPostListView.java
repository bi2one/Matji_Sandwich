package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.R;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest;
import com.matji.sandwich.util.MatjiConstants;

public class UserPostListView extends PostListView {
    private HttpRequest request;
    private User user;

    public UserPostListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void init() {
        request = new PostHttpRequest(getContext());
        setPage(1);
    }

    public void setUser(User user) {
        this.user = user;
        super.init();
    }

    @Override
    public HttpRequest request() {
        ((PostHttpRequest) request).actionUserList(user.getId(), getPage(), getLimit());
        return request;
    }

    @Override
    protected String getSubtitle() {
        return String.format(
                MatjiConstants.string(R.string.subtitle_user_post),
                user.getPostCount(),
                user.getNick());
    }
}