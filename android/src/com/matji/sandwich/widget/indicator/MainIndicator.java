package com.matji.sandwich.widget.indicator;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;

import com.matji.sandwich.R;

public class MainIndicator extends Indicator {
    public static final int LAYOUT_REFERENCE = R.layout.indicator_main;
    private ImageView iconView;
    private TextView labelView;
    
    public MainIndicator(Context context, int drawableRef, int textRef) {
	super(context, LAYOUT_REFERENCE);

	iconView = (ImageView)findViewById(R.id.icon);
	labelView = (TextView)findViewById(R.id.label);

	iconView.setImageResource(drawableRef);
	labelView.setText(textRef);
    }
}