package com.matji.sandwich.widget.search;

import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.StoreHttpRequest;
import com.matji.sandwich.widget.StoreListView;
import com.matji.sandwich.widget.search.SearchInputBar.Searchable;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

public class StoreSearchListView extends StoreListView implements Searchable {
    private StoreHttpRequest storeRequest;
    private String keyword = "";

    public StoreSearchListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {
        super.init();
        
        storeRequest = new StoreHttpRequest(getContext());
        addHeaderView(new SearchHighlightHeader(getContext()));
    }

    public void search(String keyword) {
        Log.d("refresh", "Search: " + keyword);
        this.keyword = keyword;
        requestReload();
    }

    public HttpRequest request() {
        storeRequest.actionSearch(keyword, getPage(), getLimit());
        return storeRequest;
    }
}