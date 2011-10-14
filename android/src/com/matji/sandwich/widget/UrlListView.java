package com.matji.sandwich.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.AttributeSet;

import com.matji.sandwich.R;
import com.matji.sandwich.adapter.UrlAdapter;
import com.matji.sandwich.data.Url;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.UrlHttpRequest;
import com.matji.sandwich.http.request.UrlHttpRequest.UrlType;
import com.matji.sandwich.util.MatjiConstants;

public class UrlListView extends RequestableMListView {
	private HttpRequest request;
	private int id;
	private UrlType type;
	
	public UrlListView(Context context, AttributeSet attrs) {
		super(context, attrs, new UrlAdapter(context), 10);

		init();
	}
	
	protected void init() {
        request = new UrlHttpRequest(getContext());
        setPage(1);
        setBackgroundResource(R.drawable.bg_01);
        setDivider(null);
        setFadingEdgeLength((int) MatjiConstants.dimen(R.dimen.fade_edge_length));
        setCacheColorHint(Color.TRANSPARENT);
        setSelector(android.R.color.transparent);
	}

	public void setUrlType(UrlType type) {
	    this.type = type;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public HttpRequest request() {
		((UrlHttpRequest) request).actionUrlList(type, id, getPage(), getLimit());
		
		return request;
	}

	public void onListItemClick(int position) {
	    Intent intent = new Intent(Intent.ACTION_VIEW);
	    Uri uri = Uri.parse(((Url) getAdapterData().get(position)).getUrl());
	    intent.setData(uri);
	    getContext().startActivity(intent);
	}
}