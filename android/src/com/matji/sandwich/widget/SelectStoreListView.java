package com.matji.sandwich.widget;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.google.android.maps.GeoPoint;
import com.matji.sandwich.R;
import com.matji.sandwich.adapter.SimpleStoreAdapter;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.StoreHttpRequest;
import com.matji.sandwich.session.SessionMapUtil;
import com.matji.sandwich.util.MatjiConstants;

public class SelectStoreListView extends RequestableMListView implements View.OnClickListener {
	private StoreHttpRequest request;
	private SessionMapUtil sessionUtil;
	private ArrayList<MatjiData> adapterData;
	private GeoPoint neBound;
	private GeoPoint swBound;
	private OnItemClickListener listener;

	public SelectStoreListView(Context context, AttributeSet attrs) {
		super(context, attrs, new SimpleStoreAdapter(context), 10);
		// super(context, attrs, new WritePostStoreAdapter(context), 10);

		request = new StoreHttpRequest(context);
		sessionUtil = new SessionMapUtil(context);

		setDivider(new ColorDrawable(MatjiConstants.color(R.color.listview_divider1_gray)));
		setDividerHeight(1);
		setFadingEdgeLength((int) MatjiConstants.dimen(R.dimen.fade_edge_length));
		setCacheColorHint(Color.TRANSPARENT);
		setSelector(android.R.color.transparent);
		adapterData = getAdapterData();

		SimpleStoreAdapter adapter = (SimpleStoreAdapter)getMBaseAdapter();
		adapter.setOnClickListener(this);

		setPage(1);
	}

	public void setOnItemClickListener(OnItemClickListener listener) {
		this.listener = listener;
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

	public void onClick(View v) {
		if (listener != null)
			listener.onItemClick(v);
	}

	public void onListItemClick(int position) { }

	public interface OnItemClickListener {
		public void onItemClick(View v);
	}
}