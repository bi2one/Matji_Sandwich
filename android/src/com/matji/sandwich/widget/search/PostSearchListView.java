package com.matji.sandwich.widget.search;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.SearchResult;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest;
import com.matji.sandwich.widget.SimplePostListView;
import com.matji.sandwich.widget.search.SearchInputBar.Searchable;

public class PostSearchListView extends SimplePostListView implements Searchable {
    private PostHttpRequest postRequest;
    private String keyword = "";
    private SearchHighlightHeader indexbar;

    public PostSearchListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void init() {
        super.init();

        postRequest = new PostHttpRequest(getContext());
        indexbar = new SearchHighlightHeader(getContext());
        addHeaderView(indexbar);
    }

    public HttpRequest request() {
        postRequest.actionSearch(keyword, getPage(), getLimit());
        return postRequest;
    }

    public void search(String keyword) {
        Log.d("refresh", "Search: " + keyword);
        this.keyword = keyword;
        requestReload();
        indexbar.search(keyword);
    }
    
    @Override
    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
        SearchResult result = (SearchResult) data.get(0);
        super.requestCallBack(tag, result.getData());
        indexbar.setCount(result.getTotalCount());
    }
}