package com.matji.sandwich.widget.search;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.SearchResult;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.StoreHttpRequest;
import com.matji.sandwich.widget.SimpleStoreListView;
import com.matji.sandwich.widget.search.SearchInputBar.Searchable;
import com.matji.sandwich.session.SessionRecentSearchUtil;

public class StoreSearchListView extends SimpleStoreListView implements Searchable {
    private StoreHttpRequest storeRequest;
    private String keyword = "";
    private SearchHighlightHeader indexbar;
	private SessionRecentSearchUtil sessionUtil;
 
    public StoreSearchListView(Context context, AttributeSet attrs) {
        super(context, attrs);
		sessionUtil = new SessionRecentSearchUtil(context);
    }

    @Override
    protected void init() {
        super.init();
        storeRequest = new StoreHttpRequest(getContext());
        indexbar = new SearchHighlightHeader(getContext());
        addHeaderView(indexbar);
    }

    public void search(String keyword) {
    	this.keyword = keyword;
        requestReload();
        indexbar.search(keyword);
        sessionUtil.push(keyword);
    }

    public HttpRequest request() {
        storeRequest.actionSearch(keyword, getPage(), getLimit());
        return storeRequest;
    }
    
    @Override
    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
        SearchResult result = (SearchResult) data.get(0);
        super.requestCallBack(tag, result.getData());
        indexbar.setCount(result.getTotalCount());
    }
    

}