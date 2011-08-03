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

import java.util.ArrayList;

public class ChatView extends MListView implements ListScrollRequestable, PullToRefreshListView.OnRefreshListener {
	private ChatRequestScrollListener scrollListener;
	private ArrayList<Message> adapterData;
	private HttpRequestManager manager;
	private ChatAdapter adapter;
	private HttpRequest request;
	private boolean scrollable;
	private int thread_id;

	private int page = 1;
	private int limit = 30;

	protected final static int REQUEST_NEXT = 0;
	protected final static int REQUEST_RELOAD = 1;
	
	public ChatView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.adapter = new ChatAdapter(context);
		request = new MessageHttpRequest(context);
		manager = HttpRequestManager.getInstance(context);
		adapterData = new ArrayList<Message>();
		adapter.setData(adapterData);
		scrollable = true;
		setAdapter(adapter);

		setDivider(null);
		setCacheColorHint(Color.TRANSPARENT);
		setItemsCanFocus(false);
		setChoiceMode(CHOICE_MODE_MULTIPLE);

		scrollListener = new ChatRequestScrollListener(this, manager);
		setOnScrollListener(scrollListener);
	}

	public void addMessage(Message message) {
		setTranscriptMode(TRANSCRIPT_MODE_ALWAYS_SCROLL);

		adapterData.add(message);

		if (adapterData.size() > limit * (page - 1)) {
			scrollListener.requestSetOn();
			adapterData.remove(0);
		}
		
		adapter.initSelectedPosition();
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
		adapter.notifyDataSetChanged();
		page = 1;
	}

	public void nextValue() {
		page++;
	}
	
	
	public void requestNext() {
		Log.d("refresh" , "requestNext()");
		Log.d("refresh", (getActivity() == null) ? "activity is null" : "antivity is ok");
		manager.request(getActivity(), request(), REQUEST_NEXT, this);
		nextValue();
	}

	
	public void requestReload() {
		Log.d("refresh", "requestReload()");
		Log.d("refresh", (getActivity() == null) ? "activity is null" : "antivity is ok");
		initValue();
		manager.request(getActivity(), request(), REQUEST_RELOAD, this);
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
		setScrollable(true);
		if (data.size() == 0 || data.size() < limit) {
			scrollListener.requestSetOff();
		} else {
			scrollListener.requestSetOn();
		}

		for (int i = 0; i < data.size(); i++) {
			adapterData.add(0, (Message) data.get(i));
		}

		if (data.size() > 0) {
			adapter.notifyDataSetChanged();
			switch (tag) {
			case REQUEST_NEXT:
				setSelection(data.size()+1);
				break;
			case REQUEST_RELOAD:
				setSelection(adapterData.size());
//				Message recentMessage = (Message) adapterData.get(data.size()-1);
//				Message threadMessage = (Message) SharedMatjiData.getInstance().top();
//				threadMessage.setMessage(recentMessage.getMessage());
//				threadMessage.setAgo(recentMessage.getAgo());
				break;
			}
		}
	}

	
	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.performExceptionHandling(getContext());
	}

	
	public void onRefresh() {
		Log.d("refresh", "OnRefresh!!!!");
		requestReload();
	}

	
	public void onListItemClick(int position) {}

	class ChatRequestScrollListener implements AbsListView.OnScrollListener, OnTouchListener {
		private ListScrollRequestable requestable;
		private HttpRequestManager manager;
		private boolean isSet;
		private int cFirstVisibleItem;
		private int cTotalItemCount;

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

		public void onScrollStateChanged(AbsListView v, int state) {
			switch (state) {
			case SCROLL_STATE_IDLE: case SCROLL_STATE_FLING:
				if (isSet && !manager.isRunning(getActivity()) && cTotalItemCount > 0 && cFirstVisibleItem == 0) {
					setScrollable(false);
					requestable.requestNext();
				}
				break;
			case SCROLL_STATE_TOUCH_SCROLL:
				setTranscriptMode(TRANSCRIPT_MODE_NORMAL);
				break;
			}
		}

		
		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
			this.cTotalItemCount = totalItemCount;
			this.cFirstVisibleItem = firstVisibleItem;
		}

		
		public boolean onTouch(View v, MotionEvent event) {
			return false;
		}
	}
	
	public void setScrollable(boolean scrollable) {
		this.scrollable = scrollable;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return (scrollable) ? super.onTouchEvent(ev) : scrollable;
	}
	
	public HttpRequestManager getManager() {
		return manager;
	}
	
	
	
}