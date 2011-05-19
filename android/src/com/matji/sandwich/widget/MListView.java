package com.matji.sandwich.widget;

import android.view.View;
// import android.view.View.OnTouchListener;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.AdapterView;
import android.widget.ListView;
import android.app.Activity;

// import com.matji.sandwich.http.HttpRequestManager;
// import com.matji.sandwich.Requestable;

public abstract class MListView extends ListView {
	private Activity activity;
	// private ListScrollRequestable scrollRequestable;
	// private OnScrollListener scrollListener;

	public MListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// setOnScrollListener(this);
		this.setOnItemClickListener(new MItemClickListener());
	}

	protected Activity getActivity() {
		return activity;
	}

	public void start(Activity activity) {
		this.activity = activity;
	}

	public abstract void onItemClickEvent(int position);

	// public void onScrollStateChanged(AbsListView view, int scrollState) { }
	// public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
	// 	Log.d("aaa", "aaa");
	// }
	
	private class MItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// TODO Auto-generated method stub
			onItemClickEvent(arg2);
		}

	}
}