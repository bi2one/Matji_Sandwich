package com.matji.sandwich.http.spinner;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

import com.matji.sandwich.R;
import com.matji.sandwich.util.AnimationUtil;

public class HeightScaleAnimationSpinner implements Spinnable, Animation.AnimationListener {
    private Context context;
    private Spinnable innerSpinner;
    private ViewGroup layout;
    private SpinListener listener;
    
    public HeightScaleAnimationSpinner(Context context, ViewGroup layout, Spinnable innerSpinner) {
	this.context = context;
	this.innerSpinner = innerSpinner;
	this.layout = layout;
    }

    public void setSpinListener(SpinListener listener) {
	this.listener = listener;
    }
    
    public void start() {
	if (listener != null) {
	    listener.onStart(this);
	}
	innerSpinner.start();
	// Log.d("=====", "start!")
    }

    public void stop() {
	// Animation outAnimation = AnimationUtils.loadAnimation(context, R.anim.shrink_from_bottom);
	Animation outAnimation = AnimationUtil.outToTopAnimation();
	outAnimation.setAnimationListener(this);

	if (listener != null) {
	    listener.onStop(this);
	}
	
	layout.setAnimation(outAnimation);
    }

    public void onAnimationEnd(Animation animation) {
	innerSpinner.stop();
    }

    public void onAnimationRepeat(Animation animation) { }

    public void onAnimationStart(Animation animation) { }

}