package com.matji.sandwich.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.matji.sandwich.R;
import com.matji.sandwich.StoreMainActivity;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.adapter.WritePostStoreAdapter;
import com.matji.sandwich.session.SessionMapUtil;
import com.matji.sandwich.http.request.StoreHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.util.MatjiConstants;

import com.google.android.maps.GeoPoint;

import java.util.ArrayList;

public class WritePostStoreListView extends RequestableMListView {
    private Context context;
    private StoreHttpRequest request;
    private SessionMapUtil sessionUtil;
    private ArrayList<MatjiData> adapterData;
    private GeoPoint neBound;
    private GeoPoint swBound;

    public WritePostStoreListView(Context context, AttributeSet attrs) {
        super(context, attrs, new WritePostStoreAdapter(context), 10);
        this.context = context;

        request = new StoreHttpRequest(context);
        sessionUtil = new SessionMapUtil(context);

        setDivider(new ColorDrawable(MatjiConstants.color(R.color.listview_divider1_gray)));
        setDividerHeight(1);
        setFadingEdgeLength((int) MatjiConstants.dimen(R.dimen.fade_edge_length));
        setCacheColorHint(Color.TRANSPARENT);
        setSelector(android.R.color.transparent);
        adapterData = getAdapterData();

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

    public void onListItemClick(int position) { }
}