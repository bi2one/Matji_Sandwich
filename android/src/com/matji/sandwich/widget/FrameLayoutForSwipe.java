package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public class FrameLayoutForSwipe extends FrameLayout {
	private int height = 0;
	private int width = 0;

	public FrameLayoutForSwipe(Context context) {
		super( context );
	}

	public FrameLayoutForSwipe(Context context, AttributeSet attrs) {
		super( context, attrs );
	}

	public FrameLayoutForSwipe(Context context, AttributeSet attrs, int defStyle) {
		super( context, attrs, defStyle );
	}

	@Override
	protected void onMeasure( int widthMeasureSpec, int heightMeasureSpec ) {
		// child at position 1 = front view
		View v = getChildAt(1);

		int viewWidth = v.getWidth();
		int viewHeight = v.getHeight();
		width = viewWidth;
		height = viewHeight;

		getChildAt(0).setLayoutParams(new FrameLayout.LayoutParams(width, height));
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}