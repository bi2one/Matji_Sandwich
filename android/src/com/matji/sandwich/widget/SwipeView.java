package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;

public class SwipeView extends HorizontalPager {


    public SwipeView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        init();
    }
    
    public SwipeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    
    private void init() {
    }
}