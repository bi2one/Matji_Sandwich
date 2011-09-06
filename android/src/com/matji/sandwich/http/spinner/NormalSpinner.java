package com.matji.sandwich.http.spinner;

import android.content.Context;
import android.view.ViewGroup;

import com.matji.sandwich.R;

public class NormalSpinner extends Spinner {
    public NormalSpinner(Context context, ViewGroup layout) {
	super(context, layout, R.layout.popup_loading);
    }
}