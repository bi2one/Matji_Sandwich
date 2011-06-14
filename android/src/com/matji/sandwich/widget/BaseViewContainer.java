package com.matji.sandwich.widget;

import android.content.Context;
import android.widget.TextView;

import com.matji.sandwich.R;

public class BaseViewContainer extends ViewContainer {
    public BaseViewContainer(Context context, String data) {
	super(context, R.layout.header_base);

	((TextView)getRootView().findViewById(R.id.header_base_text)).setText(data);
    }
}