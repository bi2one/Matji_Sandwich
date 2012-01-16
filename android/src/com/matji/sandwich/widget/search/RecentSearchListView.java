package com.matji.sandwich.widget.search;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.matji.sandwich.R;
import com.matji.sandwich.SearchActivity;
import com.matji.sandwich.adapter.SimpleAdapter;
import com.matji.sandwich.data.RecentSearchToken;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.StoreHttpRequest;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.RequestableMListView;
import com.matji.sandwich.session.SessionRecentSearchUtil;

public class RecentSearchListView extends RequestableMListView {
	private SessionRecentSearchUtil sessionUtil;
	private RecentSearchHighlightHeader recentbar;
	private ArrayList<RecentSearchToken> recentSearchedList;
	protected StoreHttpRequest storeRequest;
	private SimpleAdapter adapter;
	private String keyword = "";
	
	public RecentSearchListView(Context context, AttributeSet attrs) {
		super(context, attrs, new SimpleAdapter(context), 15);
		sessionUtil = new SessionRecentSearchUtil(context);
		recentSearchedList = sessionUtil.getRecentList();
		adapter = new SimpleAdapter(context);
		if (recentSearchedList.size() != 0)
			setVisibility(View.VISIBLE);
		else
			setVisibility(View.GONE);

		adapter.setData(recentSearchedList);
		setAdapter(adapter);
		init();
	}


	protected void init() {
		recentbar = new RecentSearchHighlightHeader(getContext());
		addHeaderView(recentbar);

		setBackgroundResource(R.color.matji_white);
		setDivider(new ColorDrawable(MatjiConstants.color(R.color.listview_divider1_gray)));
		setDividerHeight((int) MatjiConstants.dimen(R.dimen.default_divider_size));
		setFadingEdgeLength(0);
		setCacheColorHint(Color.TRANSPARENT);
		setSelector(android.R.color.transparent);
	}

	@Override
	public void onListItemClick(int position) {
		RecentSearchToken data = (RecentSearchToken) getAdapter().getItem(position+1);
		this.keyword = data.getSeed();
		Intent intent = new Intent(getActivity(), SearchActivity.class);
		intent.putExtra(SearchActivity.KEYWORD, keyword);
		if (getActivity().getLocalClassName().equals("StoreSearchActivity")) {
			intent.putExtra(SearchActivity.PAGE, 0);
		} else if (getActivity().getLocalClassName().equals("PostSearchActivity")) {
			intent.putExtra(SearchActivity.PAGE, 1);
		} else if (getActivity().getLocalClassName().equals("UserSearchActivity")) {
			intent.putExtra(SearchActivity.PAGE, 2);
		}
		getActivity().startActivity(intent);
		getActivity().finish();
	}

	@Override
	public HttpRequest request() {
		return null;
	}

}
