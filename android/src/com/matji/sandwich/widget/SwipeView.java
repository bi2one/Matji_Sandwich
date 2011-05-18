package com.matji.sandwich.widget;

import android.view.ViewGroup;
import android.view.View;
import android.view.GestureDetector;
import android.view.MotionEvent;
// import android.view.View.OnTouchListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.content.Context;
import android.util.Log;
import android.util.AttributeSet;
import android.widget.Scroller;
import android.widget.Scroller;

public class SwipeView extends HorizontalPager {
    // private GestureDetector gestureDetector;
    private Context context;
    private Scroller scroller;

    public SwipeView(Context context, AttributeSet attrs) {
	super(context, attrs, 0);
	this.context = getContext();
	// gestureDetector = new GestureDetector(new GestureListener());
	// scroller = new Scroller(context);
    }

    // protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    //     int childLeft = 0;

    //     final int count = getChildCount();
    // 	Log.d("====count", "count: " + count);
	
    //     for (int i = 0; i < count; i++) {
    //         final View child = getChildAt(i);
    //         if (child.getVisibility() != View.GONE) {
    //             final int childWidth = this.getMeasuredWidth();
    // 		// Log.d("====width", "width: " + this.getMeasuredWidth());
		
    //             // child.layout(childLeft, 0, childLeft + childWidth, child.getMeasuredHeight());
    //             child.layout(childLeft, 0, childLeft + childWidth, this.getMeasuredHeight());
    //             childLeft += childWidth;
    //         }
    //     }
    // }

    // public static class GestureListener extends SimpleOnGestureListener {
    // 	private float downX;
	
    // 	public boolean onDown(MotionEvent e) {
    // 	    downX = e.getX();
    // 	    return false;
    // 	}

    // 	public boolean onSingleTapUp(MotionEvent e) {
    // 	    // Log.d("++++++", "up     :" + e.getX() + ", " + e.getY());
    // 	    return false;
    // 	}
	
    // 	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
    // 	    // Log.d("======", "fling  :" + e1.getX() + ", " + e2.getY());
    // 	    return false;
    // 	}
	
    // 	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
    // 	    Log.d("======", "distance: " + ((downX - e2.getX()) + distanceX));
    // 	    // Log.d("======", "aaaaaaaa: " + distanceX);
    // 	    // e2좌표지점으로 view를 옮겨주는 작업이 필요함.
    // 	    // scrollBy((int)(downX - e2.getX()), 0);
    // 	    // Log.d("======", "start  : " + e1.getX() + ", " + e1.getY());
    // 	    // Log.d("======", "scroll :" + distanceX + ", " + distanceY);
    // 	    // Log.d("======", "down   : " + downEvent.getX() + ", " + downEvent.getY());
    // 	    // Log.d("======", "end    : " + e2.getX() + ", " + e2.getY());
    // 	    // Log.d("======", "=======================================");
    // 	    // Log.d("======", context.test);
	    
    // 	    return false;
    // 	}
 //    }
}