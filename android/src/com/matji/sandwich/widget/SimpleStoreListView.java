package com.matji.sandwich.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Parcelable;
import android.util.AttributeSet;

import com.matji.sandwich.R;
import com.matji.sandwich.StoreMainActivity;
import com.matji.sandwich.adapter.SimpleStoreAdapter;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.StoreHttpRequest;
import com.matji.sandwich.util.MatjiConstants;

public class SimpleStoreListView extends RequestableMListView {
	protected HttpRequest request;

	public SimpleStoreListView(Context context, AttributeSet attrs) {
        super(context, attrs, new SimpleStoreAdapter(context), 10);
        init();
    }
	
	protected void init() {
		request = new StoreHttpRequest(getContext());
		setPage(1);

		setBackgroundResource(R.color.matji_white);
        setDivider(new ColorDrawable(MatjiConstants.color(R.color.listview_divider1_gray)));
        setDividerHeight((int) MatjiConstants.dimen(R.dimen.default_divider_size));
		setFadingEdgeLength(0);
		setCacheColorHint(Color.TRANSPARENT);
		setSelector(android.R.color.transparent);
		
		((SimpleStoreAdapter) getMBaseAdapter()).visibleBookmarkView();
	}
	

	public HttpRequest request() {
		((StoreHttpRequest) request).actionList(getPage(), getLimit());
		return request;
	}

	public void onListItemClick(int position) {
		Store store = (Store) getAdapterData().get(position);
		Intent intent = new Intent(getActivity(), StoreMainActivity.class);
		intent.putExtra(StoreMainActivity.STORE, (Parcelable) store);
		intent.putExtra(StoreMainActivity.POSITION, position);
		getActivity().startActivityForResult(intent, BaseActivity.STORE_MAIN_ACTIVITY);		
	}
}