package com.matji.sandwich.widget;

import com.matji.sandwich.R;
import com.matji.sandwich.adapter.SectionAdapter;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * View displaying the list with sectioned header.
 * 
 * @author mozziluv
 *
 */
public class SectionListView extends RequestableMListView implements View.OnClickListener {
	private HttpRequest request;

	public SectionListView(Context context, AttributeSet attr) {
		super(context, attr, new SectionAdapter(context), 10);
		commonInitialisation();
	}

	protected final void commonInitialisation() {
		setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.pattern_bg));
		setScrollingCacheEnabled(false);
		setDivider(null);
	}

	@Override
	public HttpRequest request() {
		if (request == null || !(request instanceof PostHttpRequest)) {
			request = new PostHttpRequest(getContext());
		}
		((PostHttpRequest) request).actionList(getPage(), getLimit());
		return request;
	}

	@Override
	public void onListItemClick(int position) {}

	@Override
	public void onClick(View arg0) {
		Log.d("Matji", "CLICK");		
	}
}