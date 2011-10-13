package com.matji.sandwich.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;

import com.matji.sandwich.R;
import com.matji.sandwich.adapter.SimpleUserAdapter;
import com.matji.sandwich.http.request.FollowingHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.MatjiConstants;

public class ReceivedUserListView extends RequestableMListView {
	private HttpRequest request;
	
	public ReceivedUserListView(Context context, AttributeSet attrs) {
		super(context, attrs, new SimpleUserAdapter(context), 15);
		
		init();
	}

	public void setAdapterOnClickListener(OnClickListener listener) {
        ((SimpleUserAdapter) getMBaseAdapter()).setOnClickListener(listener);
 	}
	
	public void init() {
	    setPage(1);
        setBackgroundColor(MatjiConstants.color(R.color.matji_white));
        setDivider(new ColorDrawable(MatjiConstants.color(R.color.listview_divider1_gray)));
	    setDividerHeight(1);
	    setFadingEdgeLength((int) MatjiConstants.dimen(R.dimen.fade_edge_length));
	    setCacheColorHint(Color.TRANSPARENT);
	    setSelector(android.R.color.transparent);
	}
	
	@Override
	public HttpRequest request() {
		if (request == null || !(request instanceof FollowingHttpRequest)) {
			request = new FollowingHttpRequest(getContext());
		}
		
		((FollowingHttpRequest) request).actionFollowingList(Session.getInstance(getContext()).getCurrentUser().getId(), getPage(), getLimit());
		
		return request;
	}
	
	@Override
	public void onListItemClick(int position) {}
}