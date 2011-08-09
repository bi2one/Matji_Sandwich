package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.R;

public class AllRoundButtonView extends ButtonView {
    public AllRoundButtonView(Context context) {
	super(context);
    }
    
    public AllRoundButtonView(Context context, AttributeSet attrs) {
	super(context, attrs);
    }

    protected int setButtonId() {
	return R.id.all_rounded_button;
    }

    protected int setRootViewId() {
	return R.layout.all_rounded_button;
    }
}