package com.matji.sandwich.widget;

import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.view.View;
import android.widget.AbsListView;

import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.widget.RequestableMListView;

public class ListRequestScrollListener implements AbsListView.OnScrollListener, OnTouchListener {
    private ListScrollRequestable<?> requestable;
    private HttpRequestManager manager;
    private RequestableMListView<?> listView;
    private boolean isSet;
    private float downY;
    private boolean refreshable;
    private static final int REFRESH_VIEW_MAX_HEIGHT = 100;

    public ListRequestScrollListener(ListScrollRequestable<?> requestable, RequestableMListView<?> listView, HttpRequestManager manager) {
	this.requestable = requestable;
	this.listView = listView;
	this.manager = manager;
	isSet = true;
    }

    public void requestSetOff() {
	isSet = false;
    }

    public void requestSetOn() {
	isSet = true;
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) { }
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
	// Log.d("scroll: ", ((RequestableMListView)view).getVerticalScrollOffset() + "");
	if (isSet &&
	    !manager.isRunning() &&
	    totalItemCount > 0 &&
	    (firstVisibleItem + visibleItemCount) == totalItemCount) {
	    requestable.requestNext();
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