package com.matji.sandwich.widget;

import com.matji.sandwich.R;
import com.matji.sandwich.adapter.PostSectionedAdapter;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

/**
 * View displaying the list with sectioned header.
 * 
 * @author mozziluv
 * 
 */
public class PostListView extends RequestableMListView {
	private HttpRequest request;
	
	public PostListView(Context context, AttributeSet attr) {
		super(context, attr, new PostSectionedAdapter(context), 10);
		init();
	}	

	protected void init() {
		setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.pattern_bg));
		setDivider(null);
		setFadingEdgeLength(getResources().getDimensionPixelSize(R.dimen.fade_edge_length));
		setCacheColorHint(Color.TRANSPARENT);
		setSelector(android.R.color.transparent);
	}

	@Override
	public HttpRequest request() {
		if (request == null || !(request instanceof PostHttpRequest)) {
			request = new PostHttpRequest(getContext());
		}
		((PostHttpRequest) request).actionListWithAttachFiles(getPage(), getLimit());
		return request;
	}

	@Override
	public void onListItemClick(int position) {}
}
