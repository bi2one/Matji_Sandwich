package com.matji.sandwich.widget;

import android.content.Context;
import android.widget.RelativeLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;

import com.matji.sandwich.http.request.RequestCommand;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.listener.ListRequestScrollListener;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.adapter.MBaseAdapter;

import java.util.ArrayList;

public abstract class RequestableMListView extends PullToRefreshListView implements ListScrollRequestable,
										    PullToRefreshListView.OnRefreshListener {
    private ListRequestScrollListener scrollListener;
    private ArrayList<MatjiData> adapterData;
    private MBaseAdapter adapter;
    private HttpRequestManager manager;
    private boolean canRepeat = false;
    private int prevPage = 0;
    private int page = 1;
    private int limit = 10;
    private RelativeLayout loadingFooterView;

    protected final static int REQUEST_NEXT = 0;
    protected final static int REQUEST_RELOAD = 1;

    public abstract RequestCommand request();

    public RequestableMListView(Context context, AttributeSet attrs, MBaseAdapter adapter, int limit) {
	super(context, attrs);
	this.adapter = adapter;
	manager = HttpRequestManager.getInstance(context);

	adapterData = new ArrayList<MatjiData>();
	adapter.setData(adapterData);
	setAdapter(adapter);

	scrollListener = new ListRequestScrollListener(context, this);
	setPullDownScrollListener(scrollListener);

	setOnRefreshListener(this);
	loadingFooterView = new RelativeLayout(context);
	addFooterView(loadingFooterView);
	setFooterDividersEnabled(false);
	
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
	page = 1;
    }

    public void clearAdapter() {
	adapter.clear();
    }

    public void nextValue() {			
	prevPage = page;
	page++;
    }

    private void syncValue() {
	page = (adapterData.size() / limit) + 1;
    }

    protected void requestSetOn() {
	scrollListener.requestSetOn();
    }

    protected boolean isNextRequestable() {
	return scrollListener.isNextRequestable();
    }

    public void requestNext() {
	if ((adapterData.size() % limit == 0) && (adapterData.size() < limit * page)) {
	    syncValue();
	}

	if (prevPage < page) {
	    Log.d("refresh" , "requestNext()");
	    Log.d("refresh", (getActivity() == null) ? "activity is null" : "antivity is ok");
	    manager.request(loadingFooterView, request(), REQUEST_NEXT, this);
	    nextValue();
	} else if (prevPage == page) {
	    prevPage = page - 1;
	}
    }

    public void setCanRepeat(boolean canRepeat) {
	this.canRepeat = canRepeat;
    }

    public void requestReload() {
	hideRefreshView();
	Log.d("refresh", (getActivity() == null) ? "activity is null" : "activity is ok");
	if (!manager.isRunning() || canRepeat) {
	    Log.d("refresh", "requestReload()");
	    initValue();
	    // manager.request((ViewGroup)getParent(), request(), REQUEST_RELOAD, this);
	    manager.request(loadingFooterView, request(), REQUEST_RELOAD, this);
	    nextValue();
	    setSelection(0);
	}
    }

    public void forceReload() {
	hideRefreshView();
	Log.d("refresh", "forceReload()");
	Log.d("refresh", (getActivity() == null) ? "activity is null" : "activity is ok");
	initValue();
	// manager.request((ViewGroup)getParent(), request(), REQUEST_RELOAD, this);
	manager.request(loadingFooterView, request(), REQUEST_RELOAD, this);
	nextValue();
	setSelection(0);
	setCanRepeat(true);
    }

    public RelativeLayout getLoadingFooterView() {
	return loadingFooterView;
    }

    public void requestConditionally() {
	if (adapterData == null || adapterData.size() == 0){
	    requestReload();
	}
    }


    public int getVerticalScrollOffset() {
	return computeVerticalScrollOffset();
    }

    protected HttpRequestManager getHttpRequestManager() {
	return manager;
    }

    protected MBaseAdapter getMBaseAdapter() {
	return adapter;
    }

    protected ArrayList<MatjiData> getAdapterData() {
	return adapterData;
    }

    public void requestCallBack(int tag, ArrayList<MatjiData> data) {		
	if (tag == REQUEST_RELOAD || tag == REQUEST_NEXT) {
	    if (tag == REQUEST_RELOAD) {
		clearAdapter();
	    }
	    if (data.size() == 0 || data.size() < limit){
		scrollListener.requestSetOff();
	    }else{
		scrollListener.requestSetOn();
	    }

	    adapter.addAll(data);
	    adapter.notifyDataSetChanged();

	    // if (adapterData.size() <= limit) {
	    // Log.d("refresh", "Will invoke onRefreshComplete()");
	    onRefreshComplete();
	    // }
	}
    }


    public void requestExceptionCallBack(int tag, MatjiException e) {
	e.performExceptionHandling(getContext());
    }

    public void onRefresh() {
	Log.d("refresh", "OnRefresh!!!!");
	requestReload();
    }

    public void dataRefresh() {
	adapter.notifyDataSetChanged();
    }
}