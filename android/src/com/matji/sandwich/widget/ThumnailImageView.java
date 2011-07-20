package com.matji.sandwich.widget;

import com.matji.sandwich.util.ImageUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Thumnail 에 사용하기 위해 ImageView 를 확장한 클래스.
 * 
 * @author mozziluv
 *
 */
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
	
	/**
	 * 현재 ImageView 에 저장되어 있는 Bitmap 의 corner 를 rounded 하게 바꾸는 메소드.
	 */
	public void convertToRoundedCornerImage() {
		Drawable d = getDrawable();
		if (d.getIntrinsicWidth() > 0 && d.getIntrinsicHeight() > 0) {
			Bitmap bm = ImageUtil.getBitmap(getDrawable());
			ImageUtil.getRoundedCornerBitmap(bm, 7);
			setImageBitmap(ImageUtil.getRoundedCornerBitmap(bm, 7));
		}
	}
}