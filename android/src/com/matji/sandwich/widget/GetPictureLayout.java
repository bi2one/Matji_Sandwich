package com.matji.sandwich.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.util.AttributeSet;

import com.matji.sandwich.R;

public class GetPictureLayout extends RelativeLayout {
    private static final int LAYOUT_REFERENCE = R.layout.get_picture_layout;
    private Context context;

    public GetPictureLayout(Context context) {
	super(context);
	init(context);
    }

    public GetPictureLayout(Context context, AttributeSet attrs) {
	super(context, attrs);
	init(context);
    }

    private void init(Context context) {
	this.context = context;
	LayoutInflater.from(context).inflate(LAYOUT_REFERENCE, this, true);
    }
}