package com.matji.sandwich.http.spinner;

import android.app.Activity;
import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.util.Log;

import com.matji.sandwich.R;

public class Spinner extends RelativeLayout {
    protected Context context;
    private LayoutParams layoutParams;
    private ViewGroup layout;

    public Spinner(Context context, ViewGroup layout, int layoutReference) {
	super(context);
	this.context = context;
	LayoutInflater.from(context).inflate(layoutReference, this, true);
	this.layout = layout;

	setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	
	layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
    }

    public void start() {
	layout.addView(this, layoutParams);
    	setVisibility(View.VISIBLE);
	bringToFront();
    }
    
    public void stop() {
	// setVisibility(View.GONE);
	layout.removeView(this);
    }
}