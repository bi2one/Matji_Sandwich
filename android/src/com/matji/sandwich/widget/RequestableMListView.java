package com.matji.sandwich.widget;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.matji.sandwich.adapter.MBaseAdapter;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.RequestCommand;
import com.matji.sandwich.http.spinner.Spinnable;
import com.matji.sandwich.http.spinner.SpinnerFactory;
import com.matji.sandwich.listener.ListRequestScrollListener;

public abstract class RequestableMListView extends PullToRefreshListView implements ListScrollRequestable,
PullToRefreshListView.OnRefreshListener{
	private ListRequestScrollListener scrollListener;
	private ArrayList<MatjiData> adapterData;
	private MBaseAdapter adapter;
	protected HttpRequestManager manager;
	private boolean canRepeat = false;
//	private int prevPage = 0;
	private int page = 1;
	private int limit = 10;
	private RelativeLayout loadingFooterView;
	private RelativeLayout loadingHeaderView;
	private Spinnable reloadSpinner;

	protected final static int REQUEST_NEXT = 0;
	protected final static int REQUEST_RELOAD = 1;

	public abstract RequestCommand request();

	public RequestableMListView(Context context, AttributeSet attrs, MBaseAdapter adapter, int limit) {
		super(context, attrs);
		this.adapter = adapter;
		manager = HttpRequestManager.getInstance();

		adapterData = new ArrayList<MatjiData>();
		adapter.setData(adapterData);
		setAdapter(adapter);

		scrollListener = new ListRequestScrollListener(context, this);
		setPullDownScrollListener(scrollListener);
		setOnRefreshListener(this);
		loadingFooterView = new RelativeLayout(context);
		loadingFooterView.setClickable(false);
		loadingFooterView.setLongClickable(false);

		loadingHeaderView = new RelativeLayout(context);
		loadingHeaderView.setClickable(false);
		loadingHeaderView.setLongClickable(false);

		super.addHeaderView(loadingHeaderView);

		reloadSpinner = SpinnerFactory.createSpinner(context, SpinnerFactory.SpinnerType.NORMAL, loadingHeaderView);
		// reloadSpinner.setSpinListener(this);
		//	setFooterDividersEnabled(false);

		this.limit = limit;
	}

	public void addHeaderView(View v) {
		deleteHeaderView(loadingHeaderView);
		super.addHeaderView(v);
		super.addHeaderView(loadingHeaderView);
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
//		prevPage = page;
		page++;
	}

//	private void syncValue() {
//		Log.d("Matji", "prev page :" + page);
//		page = (adapterData.size() / limit) + 1;
//		Log.d("Matji", "next page :" + page);
//	}

	protected void requestSetOn() {
		scrollListener.requestSetOn();
	}

	protected boolean isNextRequestable() {
		return scrollListener.isNextRequestable();
	}

	public void requestNext() {
//		if ((adapterData.size() % limit == 0) && (adapterData.size() < limit * page)) {
//			syncValue();
//		}

//		if (prevPage < page) {
//			syncValue();
			Log.d("refresh" , "requestNext()");
			Log.d("refresh", (getActivity() == null) ? "activity is null" : "antivity is ok");
			addFooterView(loadingFooterView);
			manager.request(getContext(), loadingFooterView, SpinnerFactory.SpinnerType.SMALL, request(), REQUEST_NEXT, this);
//			nextValue();
//		} else if (prevPage == page) {
//			syncValue();
//			prevPage = page - 1;
//		}
	}

	public void setCanRepeat(boolean canRepeat) {
		this.canRepeat = canRepeat;
	}

	public void requestReload() {
		hideRefreshView();
		Log.d("refresh", (getActivity() == null) ? "activity is null" : "activity is ok");
		if (!manager.isRunning() || canRepeat) {
			setSelection(1);
			Log.d("refresh", "requestReload()");
			initValue();
			manager.request(reloadSpinner, request(), REQUEST_RELOAD, this);
//			nextValue();
		}
	}

	public void forceReload() {
		hideRefreshView();
		Log.d("refresh", (getActivity() == null) ? "activity is null" : "activity is ok");
		initValue();
		manager.request(reloadSpinner, request(), REQUEST_RELOAD, this);
		loadingHeaderView.requestFocus();
//		nextValue();
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
			
			if (data == null) return;
			
			if (tag == REQUEST_RELOAD) clearAdapter();
			
			if (data.size() < limit)
				scrollListener.requestSetOff();
			else
				scrollListener.requestSetOn();
			
			adapter.addAll(data);
			adapter.notifyDataSetChanged();

			if (tag == REQUEST_RELOAD) setSelection(0);

			nextValue();
			onRefreshComplete();			
		}
		removeFooterView(loadingFooterView);
	}


	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.performExceptionHandling(getContext());
		removeFooterView(loadingFooterView);
	}

	public void onRefresh() {
		Log.d("refresh", "OnRefresh!!!!");
		requestReload();
	}

	public void dataRefresh() {
		adapter.notifyDataSetChanged();
	}
}