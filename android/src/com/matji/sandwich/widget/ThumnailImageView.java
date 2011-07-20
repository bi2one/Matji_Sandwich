package com.matji.sandwich.widget;

import com.matji.sandwich.util.ImageUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ThumnailImageView extends ImageView {
	public ThumnailImageView(Context context) {
		super(context);
	}

	public ThumnailImageView(Context context, AttributeSet attr) {
		super(context, attr, 0);
	}
	public ThumnailImageView(Context context, AttributeSet attr, int defStyle) {
		super(context, attr, defStyle);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Drawable d = getDrawable();
		if (d.getIntrinsicWidth() > 0 && d.getIntrinsicHeight() > 0) {
			Bitmap bm = ImageUtil.getBitmap(getDrawable());
			setImageBitmap(ImageUtil.getRoundedCornerBitmap(bm, 7));
		}
	}
}