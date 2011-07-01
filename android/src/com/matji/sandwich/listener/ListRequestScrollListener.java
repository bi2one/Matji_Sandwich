package com.matji.sandwich.listener;

import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.view.View;
import android.widget.AbsListView;

import com.matji.sandwich.widget.ListScrollRequestable;
import com.matji.sandwich.widget.RequestableMListView;

public class ListRequestScrollListener implements AbsListView.OnScrollListener, OnTouchListener {
	private ListScrollRequestable requestable;
	private boolean isSet;
	private int curFirstVisibleItem = 0;
	
	public ListRequestScrollListener(ListScrollRequestable requestable) {
		this.requestable = requestable;
		isSet = false;
	}

	public void requestSetOff() {
		isSet = false;
	}

	public void requestSetOn() {
		isSet = true;
	}

	public void onScrollStateChanged(AbsListView view, int scrollState) {}
	
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		int currentScrollState = ((RequestableMListView) requestable).getScrollState();
		if (isSet && totalItemCount > 0 &&
				(firstVisibleItem + visibleItemCount) == totalItemCount &&
				firstVisibleItem != curFirstVisibleItem) {
			if (currentScrollState != SCROLL_STATE_IDLE) {
				requestable.requestNext();
			}else {
				((RequestableMListView) requestable).setSelection(firstVisibleItem);
			}
		}
	}

	public boolean onTouch(View v, MotionEvent event) {
		// switch(event.getAction()) {
		// case MotionEvent.ACTION_MOVE:
		//     if (!refreshable && listView.getVerticalScrollOffset() == 0) {
		// 	refreshable = true;
		// 	downY = event.getY();
		// 	return true;
		//     } else if (refreshable) {
		// 	int padding = (int)(event.getY() - downY);
		// 	if (padding > REFRESH_VIEW_MAX_HEIGHT) padding = REFRESH_VIEW_MAX_HEIGHT;
		// 	listView.setPadding(0, padding, 0, 0);
		// 	return true;
		//     } 
		//     break;
		// case MotionEvent.ACTION_UP:
		//     listView.setPadding(0, 0, 0, 0);
		//     refreshable = false;
		//     break;
		// }
		return false;
	}
}