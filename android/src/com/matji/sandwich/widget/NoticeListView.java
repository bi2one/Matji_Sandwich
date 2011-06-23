package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.adapter.NoticeAdapter;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.NoticeHttpRequest;

public class NoticeListView extends RequestableMListView {
	private HttpRequest request;

	public NoticeListView(Context context, AttributeSet attrs) {
		super(context, attrs, new NoticeAdapter(context), 10);

		setPage(1);
	}

	@Override
	public HttpRequest request() {
		if (request == null || !(request instanceof NoticeHttpRequest)) {
			request = new NoticeHttpRequest(getActivity());
		}

		((NoticeHttpRequest) request).actionList(getPage(), getLimit());

		return request;
	}

	@Override
	public void onListItemClick(int position) {
		// TODO Auto-generated method stub
		
	}
}