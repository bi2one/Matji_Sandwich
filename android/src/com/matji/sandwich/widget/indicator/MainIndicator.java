package com.matji.sandwich.widget.indicator;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.matji.sandwich.R;
import com.matji.sandwich.widget.BoldStyleTextView;

public class MainIndicator extends Indicator {
    public static final int LAYOUT_REFERENCE = R.layout.indicator_main;
    private ImageView iconView;
    private TextView labelView;
    private BoldStyleTextView countView;

    public MainIndicator(Context context, int drawableRef, int textRef) {
        super(context, LAYOUT_REFERENCE);

        iconView = (ImageView)findViewById(R.id.indicator_main_icon);
        labelView = (TextView)findViewById(R.id.indicator_main_label);
        countView = (BoldStyleTextView) findViewById(R.id.indicator_main_count);
        countView.bolding();

        iconView.setImageResource(drawableRef);
        labelView.setText(textRef);
    }
    
    public void setLabel(int textRef) {
        labelView.setText(textRef);
    }
    
    public void setLabel(String text) {
        labelView.setText(text);
    }
    
    public void setCount(int count) {
        countView.setText(count + "");
        if (count > 0) {
            setVisibilityCount(View.VISIBLE);
        } else {
            setVisibilityCount(View.GONE);
        }
    }
    
    public void setVisibilityCount(int visibility) {
        countView.setVisibility(visibility);
    }
}