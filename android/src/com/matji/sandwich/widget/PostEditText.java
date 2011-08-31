package com.matji.sandwich.widget;

import android.widget.RelativeLayout;
import android.widget.EditText;
import android.widget.Button;
import android.util.AttributeSet;
import android.content.Context;
import android.view.LayoutInflater;

import com.matji.sandwich.R;

public class PostEditText extends RelativeLayout {
    private Context context;
    private EditText postText;
    private Button storeButton;
    private Button tagButton;
    private Button serviceButton;
    private Button keyboardToggleButton;

    public PostEditText(Context context, AttributeSet attrs) {
	super(context, attrs);
	
	this.context = context;
	LayoutInflater.from(context).inflate(R.layout.post_edit_text, this, true);
	
    }
}
