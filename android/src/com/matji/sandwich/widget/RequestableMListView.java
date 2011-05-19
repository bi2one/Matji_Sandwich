package com.matji.sandwich.widget;

import android.app.Activity;
import android.view.ViewGroup;
import android.view.View;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.LayoutInflater;
import android.content.Context;
import android.util.Log;
import android.util.AttributeSet;

import com.matji.sandwich.R;
import com.matji.sandwich.Requestable;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.adapter.StoreAdapter;
import com.matji.sandwich.http.request.StoreHttpRequest;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.adapter.MBaseAdapter;

import java.util.ArrayList;

public abstract class RequestableMListView extends MListView implements ListScrollRequestable {
    private ListRequestScrollListener scrollListener;
    private LayoutInflater inflater;
    private ArrayList<MatjiData> adapterData;
    private HttpRequestManager manager;
    private int limit;

    public abstract void requestNext();
    public abstract void requestReload();
    
    public RequestableMListView(Context context, AttributeSet attrs, MBaseAdapter adapter, int limit) {
	super(context, attrs);
	manager = new HttpRequestManager(context, this);
	inflater = LayoutInflater.from(context);

	adapterData = new ArrayList<MatjiData>();
	adapter.setData(adapterData);
	setAdapter(adapter);

	scrollListener = new ListRequestScrollListener(this, this, manager);
	setOnScrollListener(scrollListener);
	setOnTouchListener(scrollListener);
	
	// addView(inflater.inflate(R.layout.list_reload, null),
	// 	0,
	// 	new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	
	this.limit = limit;
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
	
	((MBaseAdapter)getAdapter()).notifyDataSetChanged();
    }

    public void requestExceptionCallBack(int tag, MatjiException e) {
	e.performExceptionHandling(getContext());
    }
    
    protected MatjiData getData(int position) {
    	return adapterData.get(position);
    }
}