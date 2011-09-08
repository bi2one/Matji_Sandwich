package com.matji.sandwich.http.spinner;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

import com.matji.sandwich.R;

public class HeightScaleAnimationSpinner implements Spinnable, Animation.AnimationListener {
    private static final long ANIMATION_DURATION = 300;
    private Context context;
    private Spinnable innerSpinner;
    private ViewGroup layout;
    
    public HeightScaleAnimationSpinner(Context context, ViewGroup layout, Spinnable innerSpinner) {
	this.context = context;
	this.innerSpinner = innerSpinner;
	this.layout = layout;
    }
    
    public void start() {
	innerSpinner.start();
	// Log.d("=====", "start!")
    }

    public void stop() {
	Animation outAnimation = AnimationUtils.loadAnimation(context, R.anim.shrink_from_bottom);
	outAnimation.setAnimationListener(this);
	layout.setAnimation(outAnimation);
    }

    public void onAnimationEnd(Animation animation) {
	innerSpinner.stop();
    }

    public void onAnimationRepeat(Animation animation) { }

    public void onAnimationStart(Animation animation) { }

    private Animation outToTopAnimation() {
	return getAccelerateAnimation(0.0f, 0.0f, 0.0f, -1.0f);
    }

    /**
     * 관성이 적용된 slide애니메이션 객체를 리턴한다.
     *
     * @param fromXDelta 움직일 X좌표의 시작점
     * @param toXDelta 움직일 X좌표의 끝점
     * @param fromYDelta 움직일 Y좌표의 시작점
     * @param toYDelta 움직일 Y좌표의 끝점
     *
     * @return 관성이 적용된 slide애니메이션 객체
     */
    private Animation getAccelerateAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta) {
        Animation animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, fromXDelta,
                Animation.RELATIVE_TO_PARENT, toXDelta,
                Animation.RELATIVE_TO_PARENT, fromYDelta,
                Animation.RELATIVE_TO_PARENT, toYDelta);
        animation.setDuration(ANIMATION_DURATION);
        animation.setInterpolator(new AccelerateInterpolator());
        return animation;
    }
}