package com.matji.sandwich.widget.search;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.SearchResult;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest;
import com.matji.sandwich.session.SessionRecentSearchUtil;
import com.matji.sandwich.widget.SimplePostListView;
import com.matji.sandwich.widget.search.SearchInputBar.Searchable;

public class PostSearchListView extends SimplePostListView implements Searchable {
    private PostHttpRequest postRequest;
    private String keyword = "";
    private SearchHighlightHeader indexbar;
    private SessionRecentSearchUtil sessionUtil;

    public PostSearchListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        sessionUtil = new SessionRecentSearchUtil(context);
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
        this.keyword = keyword;
        requestReload();
        indexbar.search(keyword);
        sessionUtil.push(keyword);
    }
    
    @Override
    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
        SearchResult result = (SearchResult) data.get(0);
        super.requestCallBack(tag, result.getData());
        indexbar.setCount(result.getTotalCount());
    }
}