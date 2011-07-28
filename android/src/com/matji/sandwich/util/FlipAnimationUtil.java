package com.matji.sandwich.util;

import android.app.Activity;
import android.view.View;
import android.view.Display;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Animation;
import android.util.Log;

import com.matji.sandwich.widget.animation.Flip3dAnimation;
import com.matji.sandwich.widget.animation.DisplayNextView;

public class FlipAnimationUtil implements Animation.AnimationListener, Runnable {
    private static final int ROTATION_DURATION = 500;
    private View v1;
    private View v2;
    private View disabledView;
    private View currentView;
    private float centerX;
    private float centerY;
    private Flip3dAnimation broadToNarrowFromLeftAnimation;
    private Flip3dAnimation broadToNarrowFromRightAnimation;
    private Flip3dAnimation narrowToBroadFromLeftAnimation;
    private Flip3dAnimation narrowToBroadFromRightAnimation;
    private Animation lastEndedAnimation;
    
    public FlipAnimationUtil(Activity parent, View v1, View v2, View disabledView) {
	Display display = parent.getWindowManager().getDefaultDisplay();
	this.v1 = v1;
	this.v2 = v2;
	this.disabledView = disabledView;
	currentView = v1;
	centerX = display.getWidth() / 2.0f;
	centerY = display.getHeight() / 2.0f;
	// Log.d("=====", "centerX: " + centerX);
	// Log.d("=====", "centerY: " + centerY);
	broadToNarrowFromLeftAnimation = new Flip3dAnimation(0, 90, centerX, centerY);
	broadToNarrowFromRightAnimation = new Flip3dAnimation(0, -90, centerX, centerY);
	narrowToBroadFromLeftAnimation = new Flip3dAnimation(-90, 0, centerX, centerY);
	narrowToBroadFromRightAnimation = new Flip3dAnimation(90, 0, centerX, centerY);

	broadToNarrowFromLeftAnimation.setDuration(ROTATION_DURATION);
	broadToNarrowFromRightAnimation.setDuration(ROTATION_DURATION);
	narrowToBroadFromLeftAnimation.setDuration(ROTATION_DURATION);
	narrowToBroadFromRightAnimation.setDuration(ROTATION_DURATION);

	broadToNarrowFromLeftAnimation.setFillAfter(true);
	broadToNarrowFromRightAnimation.setFillAfter(true);
	narrowToBroadFromLeftAnimation.setFillAfter(true);
	narrowToBroadFromRightAnimation.setFillAfter(true);

	broadToNarrowFromLeftAnimation.setInterpolator(new AccelerateInterpolator());
	broadToNarrowFromRightAnimation.setInterpolator(new AccelerateInterpolator());
	narrowToBroadFromLeftAnimation.setInterpolator(new DecelerateInterpolator());
	narrowToBroadFromRightAnimation.setInterpolator(new DecelerateInterpolator());

	broadToNarrowFromLeftAnimation.setAnimationListener(this);
	broadToNarrowFromRightAnimation.setAnimationListener(this);
	narrowToBroadFromLeftAnimation.setAnimationListener(this);
	narrowToBroadFromRightAnimation.setAnimationListener(this);
    }
    
    public void applyFlipRotation() {
	if (currentView == v1) {
	    v1.startAnimation(broadToNarrowFromLeftAnimation);
	} else {
	    v2.startAnimation(broadToNarrowFromRightAnimation);
	}
    }

    public void onAnimationStart(Animation animation) {
	disabledView.setClickable(false);
    }

    public void onAnimationEnd(Animation animation) {
	lastEndedAnimation = animation;
	v1.post(this);
	
    }

    public void onAnimationRepeat(Animation animation) { }

    public void run() {
	if (lastEndedAnimation == broadToNarrowFromLeftAnimation ||
	    lastEndedAnimation == broadToNarrowFromRightAnimation) {
	    if (currentView == v1) {
		v1.setVisibility(View.GONE);
		v1.setEnabled(false);
		
		
		v2.startAnimation(narrowToBroadFromLeftAnimation);
		currentView = v2;
	    } else {
		v2.setVisibility(View.GONE);
		v2.setEnabled(false);

		v1.startAnimation(narrowToBroadFromRightAnimation);
		currentView = v1;
	    }
	} else {
	    currentView.setEnabled(true);
	    currentView.setClickable(true);
	    currentView.setVisibility(View.VISIBLE);
	    Log.d("=====", currentView.toString());
	    Log.d("=====", "focus: " + currentView.requestFocus());
	    // currentView.requestFocus();
	    disabledView.setClickable(true);
	}
    }
}