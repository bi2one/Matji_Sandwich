package com.matji.sandwich.widget;

import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.StoreHttpRequest;

import android.content.Context;
import android.util.AttributeSet;


public class StoreBookmarkListView extends SimpleStoreListView {

    public StoreBookmarkListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public HttpRequest request() {
        ((StoreHttpRequest) request).actionBookmarkList(getPage(), getLimit());
        
        return request;
    }
}