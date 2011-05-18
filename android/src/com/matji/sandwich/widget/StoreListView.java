package com.matji.sandwich.widget;

import android.app.Activity;
import android.view.ViewGroup;
import android.content.Context;
import android.util.Log;
import android.util.AttributeSet;

import com.matji.sandwich.Requestable;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.adapter.StoreAdapter;
import com.matji.sandwich.http.request.StoreHttpRequest;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.exception.MatjiException;

import java.util.ArrayList;

public class StoreListView extends MListView implements Requestable {
    private StoreHttpRequest storeRequest;
    private ArrayList<MatjiData> stores;
    private HttpRequestManager manager;
    private final static int STORE_LIST = 0;
    
    public StoreListView(Context context, AttributeSet attrs) {
	super(context, attrs);
	storeRequest = new StoreHttpRequest();
	manager = new HttpRequestManager(context, this);
	stores = new ArrayList<MatjiData>();
	setAdapter(new StoreAdapter(context, stores));
    }

    public void start(Activity activity) {
	super.start(activity);

	// String arr[] = {"Android","iPhone","BlackBerry","AndroidPeople", "a", "a", "a", "a", "a", "a", "a"};
	// this.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1 , arr));
	storeRequest.actionList(1, 10);
	manager.request(activity, storeRequest, STORE_LIST);
    }

    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
	for (MatjiData d : data) {
	    stores.add(d);
	}

	((StoreAdapter)getAdapter()).notifyDataSetChanged();
    }

    public void requestExceptionCallBack(int tag, MatjiException e) {
	e.performExceptionHandling(getContext());
    }
}