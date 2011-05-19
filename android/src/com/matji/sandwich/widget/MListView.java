package com.matji.sandwich.widget;

import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
// import android.view.View.OnTouchListener;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.AdapterView;
import android.widget.ListView;
import android.app.Activity;

// import com.matji.sandwich.http.HttpRequestManager;
// import com.matji.sandwich.Requestable;

public abstract class MListView extends ListView implements OnItemClickListener {
    private Activity activity;
    public abstract void onListItemClick(int position);

    public MListView(Context context, AttributeSet attrs) {
	super(context, attrs);
	setOnItemClickListener(this);
    }

    protected Activity getActivity() {
	return activity;
    }

    public void start(Activity activity) {
	this.activity = activity;
    }

    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	onListItemClick(arg2);
    }
}