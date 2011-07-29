package com.matji.sandwich.widget;

import com.matji.sandwich.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ImageView.ScaleType;

public class WhiteBorderImageView extends LinearLayout {

	private final ImageView image = new ImageView(getContext());
	
	public WhiteBorderImageView(Context context) {
		super(context);
		init();
	}

	public WhiteBorderImageView(Context context, AttributeSet attr) {
		super(context, attr);
		init();
	}
	
	public void init() {
		image.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		image.setScaleType(ScaleType.CENTER_CROP);
		addView(image);
	}
	
	public void visibleBackground() {
		setBackgroundDrawable(getResources().getDrawable(R.drawable.white_border_bg));
	}
	
	public ImageView getImageView() {
		return image;
	}
	
	public void setImageDrawable(Drawable drawable) {
		image.setImageDrawable(drawable);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
}
