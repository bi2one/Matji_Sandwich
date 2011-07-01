package com.matji.sandwich.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.matji.sandwich.ChatActivity;
import com.matji.sandwich.R;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.Message;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.MessageHttpRequest;
import com.matji.sandwich.listener.ListItemSwipeListener;

public class MessageThreadListView extends MessageListView implements View.OnClickListener {
	private ListItemSwipeListener listener;
	private int clickedPosition;

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
	
	public void setFirstMessage() {
		getAdapterData().add(0, getAdapterData().get(clickedPosition));
		getAdapterData().remove(clickedPosition);
		getMBaseAdapter().notifyDataSetChanged();
	}
	
	public HttpRequest request() {
		((MessageHttpRequest) request).actionThreadList(getPage(), getLimit());
		return request;
	}
	
	
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.delete_btn:
			onDeleteButtonClicked();
			break;
		}
	}

	private void onDeleteButtonClicked() {
		Log.d("Matji", "DELETE");
	}

	public void initItemVisible() {
		listener.initItemVisible();
	}
	
	@Override
	public void onRefresh() {
		super.onRefresh();
		initItemVisible();
	}
}