package com.matji.sandwich.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.matji.sandwich.R;
import com.matji.sandwich.http.util.MatjiImageDownloader;
import com.matji.sandwich.util.DisplayUtil;
import com.matji.sandwich.util.ImageUtil;

/**
 * Thumnail 에 사용하기 위해 ImageView 를 확장한 클래스.
 * 
 * @author mozziluv
 *
 */
public class ProfileImageView extends ImageView {
	private final MatjiImageDownloader downloader = new MatjiImageDownloader(getContext());
	protected ImageView border;

	public ProfileImageView(Context context) {
		super(context);
	}

	public ProfileImageView(Context context, AttributeSet attr) {
		super(context, attr, 0);
	}
	
	public ProfileImageView(Context context, AttributeSet attr, int defStyle) {
		super(context, attr, defStyle);
	}
	
	/**
	 * 현재 ImageView 에 저장되어 있는 Bitmap 의 corner 를 rounded 하게 바꾸는 메소드.
	 */
	public void convertToRoundedCornerImage() {
		Drawable d = getDrawable();
		if (d.getIntrinsicWidth() > 0 && d.getIntrinsicHeight() > 0) {
			Bitmap bm = ImageUtil.getBitmap(d);
			setImageBitmap(ImageUtil.getRoundedCornerBitmap(bm, DisplayUtil.PixelFromDP(5), (int) getInset()));
		}
	}
	
	protected String getImageSize() {
	    return MatjiImageDownloader.IMAGE_SSMALL;
	}
	
	public void setUserId(int id) {
		downloader.downloadUserImage(id, getImageSize(), this);
	}
	
	public void findBorder() {
	    border = (ImageView) ((View) getParent()).findViewById(R.id.profile_border);
	}
	
	public void showInsetBackground() {
	    if (border == null) findBorder();
	    border.setImageResource(R.drawable.user_img54_bg02);
	}
	
	public void showReliefBackground() {
	    if (border == null) findBorder();
	    border.setImageResource(R.drawable.user_img54_bg01);
	}
	
	public float getInset() {
	    return DisplayUtil.PixelFromDP(1);
	}
}