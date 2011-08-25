package com.matji.sandwich.widget.indicator;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.matji.sandwich.R;

public class MainIndicator extends Indicator {
    public static final int LAYOUT_REFERENCE = R.layout.indicator_main;
    private ImageView iconView;
    private TextView labelView;

    public MainIndicator(Context context, int drawableRef, int textRef) {
        super(context, LAYOUT_REFERENCE);

        iconView = (ImageView)findViewById(R.id.indicator_main_icon);
        labelView = (TextView)findViewById(R.id.indicator_main_label);

        iconView.setImageResource(drawableRef);
        labelView.setText(textRef);
    }
    
    public void setLabel(int textRef) {
        labelView.setText(textRef);
    }
    
    public void setLabel(String text) {
        labelView.setText(text);
    }
}