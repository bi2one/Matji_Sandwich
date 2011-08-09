package com.matji.sandwich.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.util.AttributeSet;

import com.matji.sandwich.R;
import com.matji.sandwich.StoreMainActivity;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.adapter.StoreAdapter;
import com.matji.sandwich.http.request.StoreHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.util.MatjiConstants;

public class StoreListView extends RequestableMListView {
	private HttpRequest request;

	public StoreListView(Context context, AttributeSet attrs) {
		super(context, attrs, new StoreAdapter(context), 10);

		init();
	}
	
	protected void init() {
		request = new StoreHttpRequest(getContext());
		setPage(1);

		setBackgroundDrawable(MatjiConstants.drawable(R.drawable.pattern_bg));
		setDivider(null);
		setFadingEdgeLength((int) MatjiConstants.dimen(R.dimen.fade_edge_length));
		setCacheColorHint(Color.TRANSPARENT);
		setSelector(android.R.color.transparent);
	}	

	public HttpRequest request() {
		((StoreHttpRequest) request).actionList(getPage(), getLimit());
		return request;
	}

	public void onListItemClick(int position) {
		Store store = (Store) getAdapterData().get(position);
		Intent intent = new Intent(getActivity(), StoreMainActivity.class);
		intent.putExtra(StoreMainActivity.STORE, (Parcelable) store);
		getActivity().startActivity(intent);
	}
}