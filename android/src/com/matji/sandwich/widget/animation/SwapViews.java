package com.matji.sandwich.widget.animation;

import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

public final class SwapViews implements Runnable {
    private boolean mIsFirstView;
    View view1;
    View view2;

    public SwapViews(boolean isFirstView, View v1, View v2) {
	mIsFirstView = isFirstView;
	this.view1 = v1;
	this.view2 = v2;
    }

    public void run() {
	final float centerX = view1.getWidth() / 2.0f;
	final float centerY = view1.getHeight() / 2.0f;
	Flip3dAnimation rotation;

	if (mIsFirstView) {
	    rotation = new Flip3dAnimation(-90, 0, centerX, centerY);
	} else {
	    rotation = new Flip3dAnimation(90, 0, centerX, centerY);
	}

	rotation.setDuration(500);
	rotation.setFillAfter(true);
	rotation.setInterpolator(new DecelerateInterpolator());

	if (mIsFirstView) {
	    view2.startAnimation(rotation);
	    
	    view1.setVisibility(View.GONE);
	    view2.setVisibility(View.VISIBLE);
	    Log.d("=====", "view2 focus: " + view2.requestFocus());

	    view2.setEnabled(true);
	    view1.setEnabled(false);
	    // view2.requestFocus(true);
	} else {
	    view1.startAnimation(rotation);
	    
	    view2.setVisibility(View.GONE);
	    view1.setVisibility(View.VISIBLE);
	    // view1.requestFocus(true);
	    Log.d("=====", "view1 focus: " + view1.requestFocus());

	    view2.setEnabled(false);
	    view1.setEnabled(true);
	}
    }
}