package com.matji.sandwich.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
        setBackgroundColor(MatjiConstants.color(R.color.matji_white));
        setDivider(new ColorDrawable(MatjiConstants.color(R.color.listview_divider1_gray)));
        setDividerHeight(MatjiConstants.dimenInt(R.dimen.default_divider_size));
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