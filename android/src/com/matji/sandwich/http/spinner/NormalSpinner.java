package com.matji.sandwich.http.spinner;

import android.content.Context;
import android.view.ViewGroup;

import com.matji.sandwich.R;

public class NormalSpinner extends AnimationSpinner {
    public NormalSpinner(Context context, ViewGroup layout) {
	super(context, layout, R.drawable.animationlist_loading_large);
    }
}