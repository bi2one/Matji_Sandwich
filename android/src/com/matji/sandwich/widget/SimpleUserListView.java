package com.matji.sandwich.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;

import com.matji.sandwich.R;
import com.matji.sandwich.adapter.SimpleUserAdapter;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.UserHttpRequest;
import com.matji.sandwich.util.MatjiConstants;

public class SimpleUserListView extends RequestableMListView {
	private HttpRequest request;
	
	public SimpleUserListView(Context context, AttributeSet attrs) {
		super(context, attrs, new SimpleUserAdapter(context), 10);
		init();
	}
	
	protected void init() {
        setDivider(new ColorDrawable(MatjiConstants.color(R.color.listview_divider1_gray)));
        setDividerHeight(1);
    }

    public HttpRequest request() {
        if (request == null || !(request instanceof UserHttpRequest)) {
            request = new UserHttpRequest(getContext());
        }
        ((UserHttpRequest) request).actionList(getPage(), getLimit());
        return request;
    }


	public void onListItemClick(int position) {}
}