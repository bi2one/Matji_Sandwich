package com.matji.sandwich.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;

import com.matji.sandwich.StoreTabActivity;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.adapter.StoreAdapter;
import com.matji.sandwich.http.request.StoreHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
//import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.exception.MatjiException;

import java.util.ArrayList;

public class StoreListView extends RequestableMListView<Store> {
    private StoreHttpRequest storeRequest;
    
    public StoreListView(Context context, AttributeSet attrs) {
	super(context, attrs, new StoreAdapter(context), 10);
	
	storeRequest = new StoreHttpRequest(context);
	setPage(1);
    }

    public void start(Activity activity) {
	super.start(activity);
    }

    public HttpRequest request() {
	storeRequest.actionList(getPage(), getLimit());
	return storeRequest;
    }

    public void requestCallBack(int tag, ArrayList<Store> data) {
	super.requestCallBack(tag, data);
    }
    
    public void requestExceptionCallBack(int tag, MatjiException e) {
	super.requestExceptionCallBack(tag, e);
    }

    public void onListItemClick(int position) {
	Store store = (Store)(getAdapterData().get(position));
	Intent intent = new Intent(getActivity(), StoreTabActivity.class);
	intent.putExtra("store", store);
	getActivity().startActivity(intent);
    }
}