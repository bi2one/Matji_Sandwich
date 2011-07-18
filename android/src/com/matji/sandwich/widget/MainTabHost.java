package com.matji.sandwich.widget;

import android.widget.TabHost;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;

import com.matji.sandwich.widget.indicator.MainIndicator;

public class MainTabHost extends TabHost {
    private Context context;
    
    public MainTabHost(Context context) {
	super(context);
	this.context = context;
    }

    public MainTabHost(Context context, AttributeSet attrs) {
	super(context, attrs);
    }

    public void addTab(String specLabel, int drawableRef, int textRef, Intent content) {
	addTab(newTabSpec(specLabel)
	       .setIndicator(new MainIndicator(context, drawableRef, textRef))
	       .setContent(content));
    }
}