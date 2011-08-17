package com.matji.sandwich.widget.search;

import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.UserHttpRequest;
import com.matji.sandwich.widget.SimpleUserListView;
import com.matji.sandwich.widget.search.SearchInputBar.Searchable;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

public class UserSearchListView extends SimpleUserListView implements Searchable {
	private UserHttpRequest userRequest;
	private String keyword = "";
	
	public UserSearchListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void init() {
	    super.init();
        userRequest = new UserHttpRequest(getContext());
        addHeaderView(new SearchHighlightHeader(getContext()));
	}
	
	public HttpRequest request() {
		userRequest.actionSearch(keyword, getPage(), getLimit());
		return userRequest;
	}

	
	public void search(String keyword) {
		Log.d("refresh", "Search: " + keyword);
		this.keyword = keyword;
		requestReload();
	}
}