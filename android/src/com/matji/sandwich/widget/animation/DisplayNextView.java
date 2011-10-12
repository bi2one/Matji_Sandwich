package com.matji.sandwich.widget.animation;

import android.view.View;
import android.view.animation.Animation;

public final class DisplayNextView implements Animation.AnimationListener {
    private boolean mCurrentView;
    private View view1;
    private View view2;
    private View buttonView;

    public DisplayNextView(boolean currentView, View v1, View v2, View buttonView) {
	mCurrentView = currentView;
	this.view1 = v1;
	this.view2 = v2;
	this.buttonView = buttonView;
    }

    public void onAnimationStart(Animation animation) {
	buttonView.setClickable(false);
    }

    public void onAnimationEnd(Animation animation) {
	view1.post(new SwapViews(mCurrentView, view1, view2));
	buttonView.setClickable(true);
	if (mCurrentView) {
	    // Log.d("=====", "false to mapview");
	    // view1.setVisibility(View.INVISIBLE);
	    // view1.setClickable(false);
	    // view2.setClickable(true);
	    // view1.requestFocus();
	} else {
	    // view2.setVisibility(View.GONE);
	    // view2.setClickable(false);
	    // view1.setClickable(true);
	    // view1.requestFocus();
	}
    }

    public void onAnimationRepeat(Animation animation) {
    }
}