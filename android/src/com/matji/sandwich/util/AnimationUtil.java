//package com.matji.sandwich.util;
//
//import android.view.View;
//import android.view.animation.AccelerateInterpolator;
//
//import com.matji.sandwich.widget.animation.Flip3dAnimation;
//import com.matji.sandwich.widget.animation.DisplayNextView;
//
//public class AnimationUtil {
//    public static void applyRotation(float start, float end, View currentView, View v1, View v2) {
//    	// Find the center of view
//	float centerX = v1.getWidth() / 2.0f;
//	float centerY = v1.getHeight() / 2.0f;
//
//    	// Create a new 3D rotation with the supplied parameter
//    	// The animation listener is used to trigger the next animation
//    	final Flip3dAnimation rotation = new Flip3dAnimation(start, end, centerX, centerY);
//    	rotation.setDuration(500);
//    	rotation.setFillAfter(true);
//    	rotation.setInterpolator(new AccelerateInterpolator());
//    	rotation.setAnimationListener(new DisplayNextView((currentView == v1), v1, v2));
//
//    	if (currentView == v1) {
//    	    v1.startAnimation(rotation);
//    	} else {
//    	    v2.startAnimation(rotation);
//    	}
//    }
//}
