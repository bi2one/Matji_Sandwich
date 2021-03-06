package com.matji.sandwich.http.spinner;

import android.content.Context;
import android.view.ViewGroup;

public class SpinnerFactory {
    public enum SpinnerType {
	NORMAL, SMALL, SSMALL, HEIGHT_SCALE_NORMAL,
    }
    
    public static Spinnable createSpinner(Context context, SpinnerType type, ViewGroup layout) {
	if (layout == null) {
	    return new NullSpinner();
	}
	
	switch(type) {
	case NORMAL:
	    return new NormalSpinner(context, layout);
	case SMALL:
	    return new SmallSpinner(context, layout);
	case SSMALL:
	    return new SSmallSpinner(context, layout);
	case HEIGHT_SCALE_NORMAL:
	    return new HeightScaleAnimationSpinner(context, layout, new NormalSpinner(context, layout));
	default:
	    return new NullSpinner();
	}
    }
}