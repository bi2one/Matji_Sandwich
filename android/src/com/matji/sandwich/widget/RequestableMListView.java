package com.matji.sandwich.widget;

import android.app.Activity;
//import android.view.ViewGroup;
//import android.view.View;
//import android.view.GestureDetector;
//import android.view.MotionEvent;
//import android.view.LayoutInflater;
import android.content.Context;
//import android.util.Log;
import android.util.AttributeSet;

//import com.matji.sandwich.R;
//import com.matji.sandwich.Requestable;
import com.matji.sandwich.data.MatjiData;
//import com.matji.sandwich.data.Store;
//import com.matji.sandwich.adapter.StoreAdapter;
//import com.matji.sandwich.http.request.StoreHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.adapter.MBaseAdapter;

import java.util.ArrayList;

public abstract class RequestableMListView extends PullToRefreshListView implements ListScrollRequestable,
										    PullToRefreshListView.OnRefreshListener {
    private ListRequestScrollListener scrollListener;
//	private LayoutInflater inflater;
    private ArrayList<MatjiData> adapterData;
    private HttpRequestManager manager;
//    private View header;
    private MBaseAdapter adapter;
    private int page = 1;
    private int limit = 10;
    
    protected final int REQUEST_NEXT = 0;
    protected final int REQUEST_RELOAD = 1;
    
    public abstract HttpRequest request();
    
    public RequestableMListView(Context context, AttributeSet attrs, MBaseAdapter adapter, int limit) {
	super(context, attrs);
	this.adapter = adapter;
	manager = new HttpRequestManager(context, this);
//	inflater = LayoutInflater.from(context);
	
	adapterData = new ArrayList<MatjiData>();
	adapter.setData(adapterData);
	setAdapter(adapter);

	scrollListener = new ListRequestScrollListener(this, this, manager);
	setPullDownScrollListener(scrollListener);

	setOnRefreshListener(this);

	this.limit = limit;
    }

    protected void setPage(int page) {
	this.page = page;
    }

    protected void setLimit(int limit) {
	this.limit = limit;
    }

    protected int getPage() {
	return page;
    }

    protected int getLimit() {
	return limit;
    }
    
    public void initValue() {
	adapterData.clear();
	page = 1;
    }

    public void nextValue() {
	page++;
    }

    public void requestNext() {
	manager.request(getActivity(), request(), REQUEST_NEXT);
	nextValue();
    }

    public void requestReload() {
	initValue();
	manager.request(getActivity(), request(), REQUEST_RELOAD);
	nextValue();
    }


    public int getVerticalScrollOffset() {
	return computeVerticalScrollOffset();
    }

    protected HttpRequestManager getHttpRequestManager() {
	return manager;
    }

    protected ArrayList<MatjiData> getAdapterData() {
	return adapterData;
    }

    public void start(Activity activity) {
	super.start(activity);
	requestNext();
    }

    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
	if (data.size() == 0 || data.size() < limit)
	    scrollListener.requestSetOff();
	    
	for (MatjiData d : data) {
	    adapterData.add(d);
	}

	((MBaseAdapter)adapter).notifyDataSetChanged();

	if (adapterData.size() <= limit)
	    onRefreshComplete();
    }

    public void requestExceptionCallBack(int tag, MatjiException e) {
	e.performExceptionHandling(getContext());
    }

    public void onRefresh() {
	requestReload();
    
    // protected MatjiData getData(int position) {
    // 	return adapterData.get(position);
    // }
    }
}