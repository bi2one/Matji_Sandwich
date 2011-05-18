package com.matji.sandwich.widget;

import android.view.ViewGroup;
import android.view.View;
import android.view.GestureDetector;
import android.view.MotionEvent;
// import android.view.View.OnTouchListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.content.Context;
import android.util.Log;
import android.util.AttributeSet;
import android.widget.Scroller;
import android.widget.Scroller;
import android.widget.ListView;
import android.widget.AbsListView;
import android.app.Activity;

// import com.matji.sandwich.http.HttpRequestManager;
// import com.matji.sandwich.Requestable;

public class MListView extends ListView {
    private Activity activity;
    // private ListScrollRequestable scrollRequestable;
    // private OnScrollListener scrollListener;

    public MListView(Context context, AttributeSet attrs) {
	super(context, attrs);
	// setOnScrollListener(this);
    }

    protected Activity getActivity() {
	return activity;
    }

    public void start(Activity activity) {
    	this.activity = activity;
    }

    // public void onScrollStateChanged(AbsListView view, int scrollState) { }
    // public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    // 	Log.d("aaa", "aaa");
    // }
}