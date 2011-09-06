package com.matji.sandwich.http.spinner;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.matji.sandwich.R;

public class AnimationSpinner extends Spinner {
    ImageView animationView;
    AnimationDrawable animation;
    
    public AnimationSpinner(Context context, ViewGroup layout, int animationDrawableId) {
	super(context, layout, R.layout.popup_loading);
	// animationView = (ImageView)findViewById(R.id.popup_loading_animation);
	// animationView.setBackgroundResource(animationDrawableId);
	// Drawable background = animationView.getBackground();

	// if (background instanceof AnimationDrawable) {
	//     animation = (AnimationDrawable) background;
	//     Log.d("=====", "success  : " + background.toString());
	// } else {
	//     Log.d("=====", "exception: " + background.toString());
	// }
    }

    public void start() {
	super.start();
	// if (animation != null)
	//     animation.start();
    }

    public void stop() {
	super.stop();
	// if (animation != null)
	//     animation.stop();
    }
}