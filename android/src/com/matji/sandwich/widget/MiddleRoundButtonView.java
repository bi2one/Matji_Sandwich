package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.R;

public class MiddleRoundButtonView extends ButtonView {
    public MiddleRoundButtonView(Context context) {
	super(context);
    }
    
    public MiddleRoundButtonView(Context context, AttributeSet attrs) {
	super(context, attrs);
    }

    protected int setButtonId() {
	return R.id.middle_rounded_button;
    }

    protected int setRootViewId() {
	return R.layout.middle_rounded_button;
    }
}