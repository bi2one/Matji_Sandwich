package com.matji.sandwich.widget;

import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.matji.sandwich.widget.HorizontalPager.OnScrollListener;

public class SwipeView extends HorizontalPager implements OnScrollListener {

    private int currentPage = 0;
    private Set<OnPageChangedListener> listeners = new HashSet<OnPageChangedListener>();

    public SwipeView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        init();
    }
    
    public SwipeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    
    private void init() {
        addOnScrollListener(this);
    }

    public void addOnPageChangedListener(OnPageChangedListener listener) {    
        listeners.add(listener);
    }

    public void removeOnPageChangedListener(OnPageChangedListener listener) {    
        listeners.remove(listener);
    }

    @Override
    public void onScroll(int scrollX) {
        int width = getResources().getDisplayMetrics().widthPixels;
        if (width/2 < Math.abs(scrollX)) {
            if (scrollX < 0 && currentPage > 0) {
                Log.d("scroll", "Scroll to previous page");
                for (OnPageChangedListener listener : listeners) {
                    listener.prev();
                }
            } else if (scrollX > 0 && currentPage < getChildCount()) {
                Log.d("scroll", "Scroll to next page");
                for (OnPageChangedListener listener : listeners) {
                    listener.next();
                }
            }
        }
    }

    @Override
    public void onViewScrollFinished(int currentPage) {
    }

    public static interface OnPageChangedListener {
        public void prev();
        public void next();
    }
}