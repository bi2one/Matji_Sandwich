package com.matji.sandwich;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.matji.sandwich.widget.SwipeView;

public class SwipeActivity extends Activity {
    private SwipeView swipeView;
    private GestureDetector gestureDetector;

    public void onCreate(Bundle savedInstanceState, int layoutRef) {
	super.onCreate(savedInstanceState);
    }

    public void setContentView(int layoutRef) {
	SwipeView.GestureListener gestureListener = new SwipeView.GestureListener();
	super.setContentView(layoutRef);
	swipeView = (SwipeView)findViewById(R.id.SwipeView);
    	gestureDetector = new GestureDetector(this, gestureListener);
	gestureDetector.setIsLongpressEnabled(false);
	// swipeView.setOnTouchListener(gestureListener);
    }

    public SwipeView getSwipeView() {
	return swipeView;
    }

    public boolean onTouchEvent(MotionEvent event) {
	if (gestureDetector.onTouchEvent(event))
	    return true;
	else
	    return false;
    }
}