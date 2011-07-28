package com.matji.sandwich.widget.animation;

import android.view.animation.Animation;
import android.view.View;

public final class DisplayNextView implements Animation.AnimationListener {
    private boolean mCurrentView;
    View view1;
    View view2;

    public DisplayNextView(boolean currentView, View v1, View v2) {
	mCurrentView = currentView;
	this.view1 = v1;
	this.view2 = v2;
    }

    public void onAnimationStart(Animation animation) {
    }

    public void onAnimationEnd(Animation animation) {
	view1.post(new SwapViews(mCurrentView, view1, view2));
    }

    public void onAnimationRepeat(Animation animation) {
    }
}