package com.matji.sandwich.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.widget.TabHost;

import com.matji.sandwich.widget.indicator.MainIndicator;

public class MainTabHost extends TabHost {
    private Context context;
    
    public MainTabHost(Context context) {
	super(context);
	this.context = context;
    }

    public MainTabHost(Context context, AttributeSet attrs) {
	super(context, attrs);
	this.context = context;
    }

    public void addTab(String specLabel, int drawableRef, int textRef, Intent content) {
	TabHost.TabSpec spec = newTabSpec(specLabel);
	spec.setIndicator(new MainIndicator(context, drawableRef, textRef));
	spec.setContent(content);
	
	addTab(spec);
    }
}