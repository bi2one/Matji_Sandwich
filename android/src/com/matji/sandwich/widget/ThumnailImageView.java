package com.matji.sandwich.widget;

import com.matji.sandwich.util.ImageUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ThumnailImageView extends ImageView {
	private int n = 5;
	
	private int rWidth = 0;
	private int rHeight = 0;
	
	public ThumnailImageView(Context context) {
		super(context);
	}

	public ThumnailImageView(Context context, AttributeSet attr) {
		super(context, attr, 0);
	}
	public ThumnailImageView(Context context, AttributeSet attr, int defStyle) {
		super(context, attr, defStyle);
	}

	public void setrHeight(int rHeight) {
		this.rHeight = rHeight;
	}
	
	public void setrWidth(int rWidth) {
		this.rWidth = rWidth;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
//		Drawable d = getDrawable();
//		if (d.getIntrinsicWidth() > 0 && d.getIntrinsicHeight() > 0) {
//			Bitmap bm = ImageUtil.getBitmap(getDrawable());
//			setImageBitmap(ImageUtil.getRoundedCornerBitmap(bm, 7));
//		}

//		super.onDraw(canvas);
//		float[] outerR = new float[] {n, n, n, n, n, n, n, n};
//		RectF inset = new RectF(5, 5, 5, 5); // 모서리 둥근 사각형 띠의 두께.
//		float[] innerR = new float[] {n, n, n, n, n, n, n, n};
//		ShapeDrawable mDrawables = new ShapeDrawable(new RoundRectShape(outerR,
//				inset, innerR));
//		mDrawables.getPaint().setColor(Color.WHITE);
//		mDrawables.getPaint().setAntiAlias(true);
//		mDrawables.setBounds(-2, -2, rWidth + 1, rHeight + 1);
//		mDrawables.draw(canvas);
	}
}