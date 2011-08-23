package com.matji.sandwich.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.matji.sandwich.R;
import com.matji.sandwich.http.util.MatjiImageDownloader;
import com.matji.sandwich.util.ImageUtil;
import com.matji.sandwich.util.MatjiConstants;

/**
 * Thumnail 에 사용하기 위해 ImageView 를 확장한 클래스.
 * 
 * @author mozziluv
 *
 */
public class ProfileImageView extends ImageView {
	private final MatjiImageDownloader downloader = new MatjiImageDownloader(getContext());

	public ProfileImageView(Context context) {
		super(context);
        setBackgroundRoundedBitmap();
	}

	public ProfileImageView(Context context, AttributeSet attr) {
		super(context, attr, 0);
        setBackgroundRoundedBitmap();
	}
	
	public ProfileImageView(Context context, AttributeSet attr, int defStyle) {
		super(context, attr, defStyle);
	    setBackgroundRoundedBitmap();
	}
	
	/**
	 * 현재 ImageView 에 저장되어 있는 Bitmap 의 corner 를 rounded 하게 바꾸는 메소드.
	 */
	public void convertToRoundedCornerImage() {
		Drawable d = getDrawable();
		if (d.getIntrinsicWidth() > 0 && d.getIntrinsicHeight() > 0) {
			Bitmap bm = ImageUtil.getBitmap(d);
			setImageBitmap(ImageUtil.getRoundedCornerBitmap(bm, 5));
		}
	}
	
	private void setBackgroundRoundedBitmap() {
	    Drawable d = MatjiConstants.drawable(R.drawable.user_img90);
	    Bitmap bm = ImageUtil.getBitmap(d);
	    BitmapDrawable bd = new BitmapDrawable(ImageUtil.getRoundedCornerBitmap(bm, 10));
	    setBackgroundDrawable(bd);
	}

	protected String getImageSize() {
	    return MatjiImageDownloader.IMAGE_SSMALL;
	}
	
	public void setUserId(int id) {
		downloader.downloadUserImage(id, getImageSize(), this);
	}
}