package com.matji.sandwich.widget;

import android.app.Activity;
import android.view.ViewGroup;
import android.view.View;
import android.content.*;
import android.util.Log;
import android.util.AttributeSet;

import com.matji.sandwich.*;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.adapter.StoreAdapter;
import com.matji.sandwich.http.request.StoreHttpRequest;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.exception.MatjiException;

import java.util.ArrayList;

public class StoreListView extends RequestableMListView {
	private StoreHttpRequest storeRequest;
	private int page = 1;
	private final static int STORE_LIMIT = 10;

	public StoreListView(Context context, AttributeSet attrs) {
		super(context, attrs, new StoreAdapter(context), STORE_LIMIT);
		storeRequest = new StoreHttpRequest();
	}

	public void start(Activity activity) {
		super.start(activity);
	}

	public void requestNext() {
		storeRequest.actionList(page, STORE_LIMIT);
		page++;
		getHttpRequestManager().request(getActivity(), storeRequest, 0);
	}

	public void requestReload() {
		page = 1;
		requestNext();
	}

	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		super.requestCallBack(tag, data);
	}

	public void requestExceptionCallBack(int tag, MatjiException e) {
		super.requestExceptionCallBack(tag, e);
	}

	@Override
	public void onItemClickEvent(int position) {
		// TODO Auto-generated method stub
		Log.d("Matji", "called onItemClickEvent");
		Store store = (Store) getData(position);
		FlipperTestActivity activity = (FlipperTestActivity) getActivity();
		Intent intent = new Intent(activity, StoreInfoActivity.class);
		intent.putExtra("store", store);
		activity.startActivity(intent);
	}
}