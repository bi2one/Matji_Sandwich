package com.matji.sandwich.widget.indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

import com.matji.sandwich.R;
import com.matji.sandwich.util.MatjiConstants;

public class MainIndicatorTextView extends TextView {

    public MainIndicatorTextView(Context context) {
        super(context);
    }

    public MainIndicatorTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MainIndicatorTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isPressed() || isFocused() || isSelected()) {
            setShadowLayer(1, 0, (float) 1.5, MatjiConstants.color(R.color.tabbar_text_touch_shadow));
        } else {
            setShadowLayer(1, 0, (float) -1.5, MatjiConstants.color(R.color.tabbar_text_shadow));
        }
        super.onDraw(canvas);
    }    
}
