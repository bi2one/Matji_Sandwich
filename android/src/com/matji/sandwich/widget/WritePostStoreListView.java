package com.matji.sandwich.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;

import com.google.android.maps.GeoPoint;
import com.matji.sandwich.R;
import com.matji.sandwich.adapter.WritePostStoreAdapter;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.StoreHttpRequest;
import com.matji.sandwich.util.MatjiConstants;

public class WritePostStoreListView extends RequestableMListView {
	private StoreHttpRequest request;
	private GeoPoint neBound;
	private GeoPoint swBound;

	public WritePostStoreListView(Context context, AttributeSet attrs) {
		super(context, attrs, new WritePostStoreAdapter(context), 10);
		request = new StoreHttpRequest(context);
		setDivider(new ColorDrawable(MatjiConstants.color(R.color.listview_divider1_gray)));
		setDividerHeight(1);
		setFadingEdgeLength((int) MatjiConstants.dimen(R.dimen.fade_edge_length));
		setCacheColorHint(Color.TRANSPARENT);
		setSelector(android.R.color.transparent);
		setPage(1);
	}

	public void init(Activity activity) {
		setActivity(activity);
	}

	public void setNeBound(GeoPoint neBound) {
		this.neBound = neBound;
	}

	public void setSwBound(GeoPoint swBound) {
		this.swBound = swBound;
	}

	public void setBound(GeoPoint neBound, GeoPoint swBound) {
		setNeBound(neBound);
		setSwBound(swBound);
	}

	public HttpRequest request() {
		request.actionNearbyList((double) swBound.getLatitudeE6() / 1E6,
				(double) neBound.getLatitudeE6() / 1E6,
				(double) swBound.getLongitudeE6() / 1E6,
				(double) neBound.getLongitudeE6() / 1E6,
				getPage(), getLimit());
		return request;
	}

	public void onListItemClick(int position) {}
}