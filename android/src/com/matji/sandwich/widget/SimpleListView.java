package com.matji.sandwich.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;

import com.matji.sandwich.R;
import com.matji.sandwich.adapter.SimpleAdapter;
import com.matji.sandwich.data.SearchToken;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.StoreHttpRequest;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.search.SearchInputBar.Searchable;
import com.matji.sandwich.widget.search.StoreSearchListView;

public class SimpleListView extends RequestableMListView implements Searchable {
	protected StoreHttpRequest storeRequest;
	private StoreSearchListView view;
	private String keyword = "";
	
	public SimpleListView(Context context, AttributeSet attrs) {
		super(context,attrs, new SimpleAdapter(context), 10);
		view = new StoreSearchListView(context, attrs);
		init();
	}

	protected void init() {
		setBackgroundResource(R.color.matji_white);
        setDivider(new ColorDrawable(MatjiConstants.color(R.color.listview_divider1_gray)));
        setDividerHeight((int) MatjiConstants.dimen(R.dimen.default_divider_size));
		setFadingEdgeLength(0);
		setCacheColorHint(Color.TRANSPARENT);
		setSelector(android.R.color.transparent);
	}

	@Override
	public void onListItemClick(int position) {
		SearchToken data = (SearchToken) getAdapter().getItem(position+1);
		this.keyword = data.getSeed();
		search(keyword);
	}

	@Override
	public HttpRequest request() {
        storeRequest.actionSearch(keyword, getPage(), getLimit());
        return storeRequest;
	}

	@Override
	public void search(String keyword) {
		view.setActivity(getActivity());
		view.search(keyword);
	}
	
}
