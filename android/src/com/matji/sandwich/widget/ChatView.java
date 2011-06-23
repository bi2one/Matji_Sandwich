//package com.matji.sandwich.widget;
//
//
//import android.content.Context;
//import android.graphics.Color;
//import android.util.AttributeSet;
//import android.view.View;
//
//import com.matji.sandwich.adapter.ChatAdapter;
//import com.matji.sandwich.data.Message;
//import com.matji.sandwich.http.request.HttpRequest;
//import com.matji.sandwich.http.request.MessageHttpRequest;
//
//public class ChatView extends RequestableMListView implements View.OnClickListener {
//	private HttpRequest request;
//	private int thread_id;
//
//	public ChatView(Context context, AttributeSet attrs) {
//		super(context, attrs, new ChatAdapter(context), 20);
//		request = new MessageHttpRequest(context);
//
//		setPage(1);
//		setDivider(null);
//		setCacheColorHint(Color.TRANSPARENT);
//		setVerticalScrollBarEnabled(false);
//		setTranscriptMode(TRANSCRIPT_MODE_ALWAYS_SCROLL);
////		setRefreshViewListener(new OnClickListener() {
////			@Override
////			public void onClick(View arg0) {
////				requestNext();
////			}
////		});
//	}
//
//	public void setThreadId(int thread_id) {
//		this.thread_id = thread_id;
//	}
//
//	public HttpRequest request() {
//		((MessageHttpRequest) request).actionChat(thread_id, getPage(), getLimit());
//		return request;
//	}
//
//	public void onListItemClick(int position) {}
//
//	public void onClick(View v) {}
//
//	public void addMessage(Message message) {
//		getAdapterData().add(message);
//		dataRefresh();
//	}
////
////	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
////		switch (tag) {
////		case REQUEST_NEXT: case REQUEST_RELOAD:
////			Collections.reverse(getAdapterData());
////			
////			if (data.size() == 0 || data.size() < getLimit()){
////				getScrollListener().requestSetOff();
////			}else{
////				getScrollListener().requestSetOn();
////			}
////
////			for (int i = 0; i < data.size(); i++) {
////				getAdapterData().add(data.get(i));
////			}
////
////			Collections.reverse(getAdapterData());
////			//	adapterData = getListByT(data);
////
////			if (data.size() > 0)
////				((MBaseAdapter) getMBaseAdapter()).notifyDataSetChanged();
////
////			if (getAdapterData().size() <= getLimit()){
////				Log.d("refresh", "Will invoke onRefreshComplete()");
////				onRefreshComplete();
////			}
////			break;
////		}
////	}
//}

package com.matji.sandwich.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;

import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.MessageHttpRequest;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Message;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.adapter.ChatAdapter;
import com.matji.sandwich.adapter.MBaseAdapter;

import java.util.ArrayList;
import java.util.Collections;

public class ChatView extends PullToRefreshListView implements ListScrollRequestable, PullToRefreshListView.OnRefreshListener {
	private ChatRequestScrollListener scrollListener;
	private ArrayList<Message> adapterData;
	private HttpRequestManager manager;
	private MBaseAdapter adapter;
	private HttpRequest request;
	private int thread_id;

	private int page = 1;
	private int limit = 20;
	
	protected final static int REQUEST_NEXT = 0;
	protected final static int REQUEST_RELOAD = 1;
	
	public ChatView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.adapter = new ChatAdapter(context);
		request = new MessageHttpRequest(context);
		manager = new HttpRequestManager(context, this);
		adapterData = new ArrayList<Message>();
		adapter.setData(adapterData);
		setAdapter(adapter);

		setDivider(null);
		setCacheColorHint(Color.TRANSPARENT); 
		setVerticalScrollBarEnabled(false);
		setTranscriptMode(TRANSCRIPT_MODE_ALWAYS_SCROLL);
		scrollListener = new ChatRequestScrollListener(this, manager);
		setPullDownScrollListener(scrollListener);
		setOnRefreshListener(this);
	}

	public void addMessage(Message message) {
		adapterData.add(message);
		adapter.notifyDataSetChanged();
	}	
	
	public HttpRequest request() {	
		((MessageHttpRequest) request).actionChat(thread_id, page, limit);
		return request;
	}

	public void setThreadId(int thread_id) {
		this.thread_id = thread_id;
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

	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		if (tag == REQUEST_NEXT) {
			setTranscriptMode(TRANSCRIPT_MODE_DISABLED);
		} else {
			setTranscriptMode(TRANSCRIPT_MODE_ALWAYS_SCROLL);
		}
		
		if (data.size() == 0 || data.size() < limit){
			scrollListener.requestSetOff();
		}else{
			scrollListener.requestSetOn();
		}

		Collections.reverse(adapterData);
		
		for (int i = 0; i < data.size(); i++) {
			adapterData.add((Message) data.get(i));
		}
		
		Collections.reverse(adapterData);

		if (data.size() > 0)
			adapter.notifyDataSetChanged();

		if (adapterData.size() <= limit){
			Log.d("refresh", "Will invoke onRefreshComplete()");
			onRefreshComplete();
		}
	}
	
	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.performExceptionHandling(getContext());
	}


	public void onRefresh() {
		Log.d("refresh", "OnRefresh!!!!");
		requestReload();
	}
	
	@Override
	public void onListItemClick(int position) {
		// TODO Auto-generated method stub
	}

	class ChatRequestScrollListener implements AbsListView.OnScrollListener, OnTouchListener {
		private ListScrollRequestable requestable;
		private HttpRequestManager manager;
		private boolean isSet;

		public ChatRequestScrollListener(ListScrollRequestable requestable, HttpRequestManager manager) {
			this.requestable = requestable;
			this.manager = manager;
			isSet = false;
		}

		public void requestSetOff() {
			isSet = false;
		}

		public void requestSetOn() {
			isSet = true;
		}

		public void onScrollStateChanged(AbsListView view, int scrollState) { }
		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
			if (isSet &&
					!manager.isRunning() &&
					totalItemCount > 0 &&
					firstVisibleItem == 0) {
				requestable.requestNext();
			}
		}

		public boolean onTouch(View v, MotionEvent event) {
			return false;
		}
	}
}
