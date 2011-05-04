package com.matji.sandwich.http;

import android.widget.RelativeLayout;
import android.content.Context;
import android.app.Activity;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;

import com.matji.sandwich.R;

public class Spinner extends RelativeLayout {
    protected Context context = null;
    protected Activity activity = null;

    public Spinner(Context context, Activity activity) {
	super(context);
	this.context = context;
	this.activity = activity;
	LayoutInflater.from(context).inflate(R.layout.popup_loading, this, true);
	activity.addContentView(this, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	setVisibility(View.GONE);
    }

    public void start() {
	setVisibility(View.VISIBLE);
    }
    
    public void stop() {
	setVisibility(View.GONE);
    }
}