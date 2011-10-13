package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.data.Store;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.StoreHttpRequest;


public class StoreBookmarkListView extends SimpleStoreListView {

    public StoreBookmarkListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        
    }

    @Override
    public HttpRequest request() {
        ((StoreHttpRequest) request).actionBookmarkList(getPage(), getLimit());
        
        return request;
    }
    
    public void setStore(int position, Store store) {
        getAdapterData().remove(position);
        getAdapterData().add(position, store);
    }
}