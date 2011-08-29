package com.matji.sandwich.http.spinner;

import android.content.Context;
import android.widget.RelativeLayout;

import com.matji.sandwich.R;

public class NormalSpinner extends Spinner {
    public NormalSpinner(Context context, RelativeLayout layout) {
	super(context, layout, R.layout.popup_loading);
    }
}