package com.matji.sandwich.http;

import android.view.LayoutInflater;
import android.content.Context;
import android.app.Activity;
import com.matji.sandwich.R;

public class NormalSpinner extends Spinner {
    public NormalSpinner(Context context, Activity activity) {
	super(context, activity);
	LayoutInflater.from(context).inflate(R.layout.popup_loading, this, true);
    }
}