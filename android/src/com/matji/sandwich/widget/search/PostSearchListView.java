package com.matji.sandwich.widget.search;

import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest;
import com.matji.sandwich.widget.SimplePostListView;
import com.matji.sandwich.widget.search.SearchInputBar.Searchable;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

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
}