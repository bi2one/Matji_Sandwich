package com.matji.sandwich.http.spinner;

import android.content.Context;
import android.view.ViewGroup;

import com.matji.sandwich.R;

public class SSmallSpinner extends Spinner {
// public class SmallSpinner extends AnimationSpinner {
    public SSmallSpinner(Context context, ViewGroup layout) {
	super(context, layout, R.layout.popup_ssmall_loading);
    }
}