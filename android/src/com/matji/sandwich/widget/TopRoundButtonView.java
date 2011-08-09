package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.R;

public class TopRoundButtonView extends ButtonView {
    public TopRoundButtonView(Context context) {
	super(context);
    }
    
    public TopRoundButtonView(Context context, AttributeSet attrs) {
	super(context, attrs);
    }

    protected int setButtonId() {
	return R.id.top_rounded_button;
    }

    protected int setRootViewId() {
	return R.layout.top_rounded_button;
    }
}