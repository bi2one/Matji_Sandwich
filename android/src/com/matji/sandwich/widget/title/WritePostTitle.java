package com.matji.sandwich.widget.title;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.Button;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.matji.sandwich.R;

public class WritePostTitle extends RelativeLayout implements View.OnClickListener {
    private Context context;
    private OnClickListener listener;
    private Button completeButton;
    
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
	completeButton = (Button)findViewById(R.id.title_write_post_complete);
    }

    public void setOnClickListener(OnClickListener listener) {
	this.listener = listener;
    }

    public void onClick(View v) {
	if (listener != null)
	    listener.onCompleteClick();
    }

    public interface OnClickListener {
	public void onCompleteClick();
    }
}