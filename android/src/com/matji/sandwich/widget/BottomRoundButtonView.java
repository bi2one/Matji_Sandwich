package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.R;

public class BottomRoundButtonView extends ButtonView {
    public BottomRoundButtonView(Context context) {
	super(context);
    }
    
    public BottomRoundButtonView(Context context, AttributeSet attrs) {
	super(context, attrs);
    }

    protected int setButtonId() {
	return R.id.bottom_rounded_button;
    }

    protected int setRootViewId() {
	return R.layout.bottom_rounded_button;
    }
}