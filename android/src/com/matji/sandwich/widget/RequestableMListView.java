package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.adapter.MBaseAdapter;

import java.util.ArrayList;

public abstract class RequestableMListView extends PullToRefreshListView implements ListScrollRequestable,
PullToRefreshListView.OnRefreshListener {
	private ListRequestScrollListener scrollListener;
//	private LayoutInflater inflater;
	private ArrayList<MatjiData> adapterData;
	private HttpRequestManager manager;
//	private View header;
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

	// if (container != null)
	//     addHeaderView(container.getRootView());

	
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
		Log.d("refresh" , "requestNext()");
		Log.d("refresh", (getActivity() == null) ? "activity is null" : "antivity is ok");
		manager.request(getActivity(), request(), REQUEST_NEXT);
		nextValue();
	}

	
	public void requestReload() {
		Log.d("refresh", "requestReload()");
		Log.d("refresh", (getActivity() == null) ? "activity is null" : "antivity is ok");
		initValue();
		manager.request(getActivity(), request(), REQUEST_RELOAD);
		nextValue();
	}
	
	
	public void requestConditionally(){
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

//	public void setActivity(Activity activity) {
//		super.setActivity(activity);
//		requestNext();
//	}

	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		if (data.size() == 0 || data.size() < limit){
			scrollListener.requestSetOff();
		}else{
			scrollListener.requestSetOn();
		}
		
		for (int i = 0; i < data.size(); i++) {
			adapterData.add(data.get(i));
		}
		//	adapterData = getListByT(data);

		((MBaseAdapter)adapter).notifyDataSetChanged();

		if (adapterData.size() <= limit){
			Log.d("refresh", "Will invoke onRefreshComplete()");
			onRefreshComplete();
		}
	}


	// function for wild card data
	//	private <T> ArrayList<T> getListByT(ArrayList<T> list) {
	//		ArrayList<T> tList = new ArrayList<T>(list);
	//		for (T tData : list)
	//			tList.add(tData);
	//		return tList;
	//	}

	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.performExceptionHandling(getContext());
	}

	
	public void onRefresh() {
		Log.d("refresh", "OnRefresh!!!!");
		requestReload();

		//		 protected MatjiData getData(int position) {
		//		 	return adapterData.get(position);
		//		 }
	}
}