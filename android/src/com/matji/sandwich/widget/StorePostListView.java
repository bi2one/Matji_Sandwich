package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.R;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest;
import com.matji.sandwich.util.MatjiConstants;

public class StorePostListView extends PostListView {
    private HttpRequest request;
    private Store store;	

    public StorePostListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {
        request = new PostHttpRequest(getContext());
        setPage(1);
    }

    public void setStore(Store store) {
        this.store = store;
        super.init();
        setSubtitle(String.format(
                MatjiConstants.string(R.string.subtitle_store_post), 
                store.getPostCount()));
    }

    @Override
    public HttpRequest request() {
        ((PostHttpRequest) request).actionStoreList(store.getId(), getPage(), getLimit());
        return request;
    }
    
    @Override
    public void refresh() {
        if (store.getPostCount() > 0)
            super.refresh();
    }
}