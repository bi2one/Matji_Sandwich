package com.matji.sandwich.widget.search;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.SearchResult;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.UserHttpRequest;
import com.matji.sandwich.widget.SimpleUserListView;
import com.matji.sandwich.widget.search.SearchInputBar.Searchable;

public class UserSearchListView extends SimpleUserListView implements Searchable {
	private UserHttpRequest userRequest;
	private String keyword = "";
    private SearchHighlightHeader indexbar;
	
	public UserSearchListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void init() {
	    super.init();
        userRequest = new UserHttpRequest(getContext());
        indexbar = new SearchHighlightHeader(getContext());
        addHeaderView(indexbar);
	}
	
	public HttpRequest request() {
		userRequest.actionSearch(keyword, getPage(), getLimit());
		return userRequest;
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