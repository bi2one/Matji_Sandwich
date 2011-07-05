package com.matji.sandwich.widget;

import java.util.ArrayList;
import java.util.Collections;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;

import com.matji.sandwich.ChatActivity;
import com.matji.sandwich.R;
import com.matji.sandwich.Requestable;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Message;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.MessageHttpRequest;
import com.matji.sandwich.listener.ListItemSwipeListener;

public class MessageThreadListView extends MessageListView implements OnClickListener, Requestable {
	private ListItemSwipeListener listener;

	private int clickedPosition;
	private int curDeletePos;
	
	private static final int THREAD_DELETE_REQUEST = 10;

	public MessageThreadListView(final Context context, AttributeSet attrs) {
		super(context, attrs);
		this.listener = new ListItemSwipeListener(context, this, R.id.message_adapter_thread, R.id.adapter_swipe_rear, 0) {
			@Override
			public void onListItemClicked(int position) {
				clickedPosition = position - 1;
				Message message = (Message) getAdapterData().get(clickedPosition);
				((BaseActivity) context).startActivityWithMatjiData(new Intent(context, ChatActivity.class), message);
			}

			@Override
			public boolean isMyItem(int position) {
				return true;
			}
		};

		setOnTouchListener(listener);
	}
	
	public void sort() {
		ArrayList<Message> messages = new ArrayList<Message>();
		for (MatjiData data : getAdapterData()) {
			messages.add((Message) data);
		}
		Collections.sort(messages);
		getAdapterData().clear();
		getAdapterData().addAll(messages);
		getMBaseAdapter().notifyDataSetChanged();
	}
	
	public HttpRequest request() {
		if (request == null || !(request instanceof MessageHttpRequest)) {
			request = new MessageHttpRequest(context);
		}
		
		((MessageHttpRequest) request).actionThreadList(getPage(), getLimit());
		return request;
	}
	
	public HttpRequest deleteRequest(int thread_id) {
		if (request == null || !(request instanceof MessageHttpRequest)) {
			request = new MessageHttpRequest(context);
		}

		((MessageHttpRequest) request).actionDeleteThread(thread_id);
		return request;
	}
	
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.message_adapter_thumnail: case R.id.message_adapter_nick:
			int position = Integer.parseInt((String) v.getTag()); 
			gotoUserPage((Message) getAdapterData().get(position));
			break;
		case R.id.delete_btn:
			onDeleteButtonClicked(v);
			break;
		}
	}
	
	public void onDeleteButtonClicked(View v) {
		if (session.isLogin() && !getHttpRequestManager().isRunning(getActivity())) {
			curDeletePos = Integer.parseInt((String) v.getTag());

			AlertDialog.Builder alert = new AlertDialog.Builder(context);
			alert.setTitle(R.string.default_string_delete);
			alert.setMessage(R.string.default_string_check_delete);
			alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					int thread_id = ((Message) getAdapterData().get(curDeletePos)).getThreadId();
					deleteThread(thread_id);
				}
			}); 
			alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {}
			});
			alert.show();
		}
	}
	
	public void deleteThread(int thread_id) {
		getHttpRequestManager().request(getActivity(), deleteRequest(thread_id), THREAD_DELETE_REQUEST, this);
	}

	public void initItemVisible() {
		listener.initItemVisible();
	}
	
	@Override
	public void onRefresh() {
		super.onRefresh();
		initItemVisible();
	}
	
	@Override
	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		if (tag == THREAD_DELETE_REQUEST) {
			initItemVisible();
			getAdapterData().remove(curDeletePos);
			getMBaseAdapter().notifyDataSetChanged();
		} else {
			super.requestCallBack(tag, data);
		}
	}
}