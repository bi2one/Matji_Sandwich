package com.matji.sandwich.widget;

import android.widget.RelativeLayout;
import android.content.Context;
import android.app.Activity;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.matji.sandwich.R;

public class SpinnerTest extends RelativeLayout {
    public SpinnerTest(Context context) {
	super(context);
	LayoutInflater.from(context).inflate(R.layout.popup_loading, this, true);
    }

    public void start() {
    	setVisibility(View.VISIBLE);
    }
    
    public void stop() {
    	ViewGroup vGroup = (ViewGroup)this.getParent();
    	if (vGroup != null){
        	vGroup.removeView(this);
    	}
    }
}