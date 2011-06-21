package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.adapter.TagAdapter;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.TagHttpRequest;
import com.matji.sandwich.http.util.ModelType;

public class TagListView extends RequestableMListView {
	private HttpRequest request;
	private int id;
	private ModelType type;

	public TagListView(Context context, AttributeSet attrs) {
		super(context, attrs, new TagAdapter(context), 10);
		request = new TagHttpRequest(context);
		setPage(1);
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void setModelType(ModelType type) {
		this.type = type;
	}
	
	public HttpRequest request() {
		switch (type) {
		case STORE:
			((TagHttpRequest) request).actionStoreTagList(id, getPage(), getLimit());
			break;
		case USER:
			((TagHttpRequest) request).actionUserTagList(id, getPage(), getLimit());
			break;
		}
		
		return request;
	}

	public void onListItemClick(int position) {}
}