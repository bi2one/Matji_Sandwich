package com.matji.sandwich.widget.indicator;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class Indicator extends LinearLayout {
    public Indicator(Context context, int layoutRef) {
	super(context);
	LayoutInflater.from(context).inflate(layoutRef, this);
    }
}