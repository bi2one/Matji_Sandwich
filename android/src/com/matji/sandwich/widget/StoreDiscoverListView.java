package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.data.User;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.StoreHttpRequest;


public class StoreDiscoverListView extends SimpleStoreListView {
    private User user;
    
    public StoreDiscoverListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    @Override
    public HttpRequest request() {
        ((StoreHttpRequest) request).actionDiscoverList(user.getId(), getPage(), getLimit());
        
        return request;
    }
}