package com.matji.sandwich.widget;

import android.view.View;
import android.content.Context;
import android.widget.TextView;

import com.matji.sandwich.R;

public class BaseViewContainer extends ViewContainer {
    public BaseViewContainer(Context context, String data) {
	super(context, R.layout.header_test);

	((TextView)getRootView().findViewById(R.id.textView2)).setText(data);
    }
}