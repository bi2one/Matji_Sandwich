package com.matji.sandwich.widget.title;

import android.content.Context;
import android.widget.RelativeLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.matji.sandwich.R;

public class WritePostTitle extends RelativeLayout {
    private Context context;
    
    public WritePostTitle(Context context) {
	super(context);
	init(context);
    }

    public WritePostTitle(Context context, AttributeSet attrs) {
	super(context, attrs);
	init(context);
    }

    private void init(Context context) {
	this.context = context;
	LayoutInflater.from(context).inflate(R.layout.title_write_post, this, true);
    }
}