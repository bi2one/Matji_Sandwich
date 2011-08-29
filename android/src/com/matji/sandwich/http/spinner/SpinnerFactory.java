package com.matji.sandwich.http.spinner;

import android.content.Context;
import android.widget.RelativeLayout;

public class SpinnerFactory {
    public enum SpinnerType {
	NORMAL
    }
    
    public Spinner createSpinner(Context context, SpinnerType type, RelativeLayout layout) {
	switch(type) {
	case NORMAL:
	    return new NormalSpinner(context, layout);
	default:
	    return null;
	}
    }
}