package com.matji.sandwich.listener;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;

import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.widget.ListScrollRequestable;

public class ListRequestScrollListener implements AbsListView.OnScrollListener, OnTouchListener {
    private ListScrollRequestable requestable;
    private boolean isSet;
    private int curFirstVisibleItem = 0;

    public ListRequestScrollListener(Context context, ListScrollRequestable requestable) {
        this.requestable = requestable;
        isSet = false;
    }

    public void requestSetOff() {
        isSet = false;
    }

    public void requestSetOn() {
        isSet = true;
    }

    public boolean isNextRequestable() {
        return isSet;
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {}

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (isSet &&
                !HttpRequestManager.getInstance().isRunning() &&
                (firstVisibleItem + visibleItemCount) == totalItemCount &&
                totalItemCount > 0 &&
                firstVisibleItem != curFirstVisibleItem) {
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