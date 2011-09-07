package com.matji.sandwich.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.matji.sandwich.R;
import com.matji.sandwich.adapter.ImageAdapter;
import com.matji.sandwich.http.request.AttachFilesHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.util.MatjiConstants;


public abstract class ImageListView extends RequestableMListView {
	protected HttpRequest request;

	protected int imageCount;
	
	private static final int LIMIT = 6;
	
	public ImageListView(Context context, AttributeSet attrs) {
	    super(context, attrs, new ImageAdapter(context), LIMIT);
	}
	
	protected void init() {
		imageCount = ((ImageAdapter) getMBaseAdapter()).getImageViewCount();

		setDivider(null);
		setSelector(android.R.color.transparent);
		setCacheColorHint(Color.TRANSPARENT);
		setVerticalScrollBarEnabled(false);
		setBackgroundDrawable(MatjiConstants.drawable(R.drawable.bg_01));
	}
	
	protected void createRequest() {
		if (request == null || !(request instanceof AttachFilesHttpRequest)) {
			request = new AttachFilesHttpRequest(getContext(), imageCount);
		}
	}
		
	public void onListItemClick(int position) {}
}