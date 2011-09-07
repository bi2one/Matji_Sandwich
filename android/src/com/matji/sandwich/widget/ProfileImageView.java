package com.matji.sandwich.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.matji.sandwich.R;
import com.matji.sandwich.http.util.ImageLoader;
import com.matji.sandwich.http.util.RoundRectConvertable;
import com.matji.sandwich.util.DisplayUtil;
import com.matji.sandwich.util.ImageUtil;

/**
 * Thumnail 에 사용하기 위해 ImageView 를 확장한 클래스.
 * 
 * @author mozziluv
 *
 */
public class ProfileImageView extends ImageView implements ImageLoader.ImageConvertable {
    protected ImageView border;
    private Context context;
    private ImageLoader imageLoader;

    public ProfileImageView(Context context) {
	super(context);
	init(context);
    }

    public ProfileImageView(Context context, AttributeSet attr) {
	super(context, attr, 0);
	init(context);
    }
	
    public ProfileImageView(Context context, AttributeSet attr, int defStyle) {
	super(context, attr, defStyle);
	init(context);
    }

    private void init(Context context) {
	this.context = context;
	imageLoader = new ImageLoader(context, R.drawable.user_img54);
	imageLoader.setImageConvertable(new RoundRectConvertable());
    }
	
    /**
     * 현재 ImageView 에 저장되어 있는 Bitmap 의 corner 를 rounded 하게 바꾸는 메소드.
     */
    public Bitmap convert(Bitmap bitmap) {
	return ImageUtil.getRoundedCornerBitmap(bitmap, DisplayUtil.PixelFromDP(4), (int) getInset());
    }
    // public void convertToRoundedCornerImage() {
    // 	Drawable d = getDrawable();
    // 	if (d.getIntrinsicWidth() > 0 && d.getIntrinsicHeight() > 0) {
    // 	    Bitmap bm = ImageUtil.getBitmap(d);
    // 	    setImageBitmap(ImageUtil.getRoundedCornerBitmap(bm, DisplayUtil.PixelFromDP(4), (int) getInset()));
    // 	}
    // }

    protected ImageLoader.ImageSize getImageSize() {
	return ImageLoader.ImageSize.SSMALL;
    }

    public void setUserId(int id) {
	imageLoader.DisplayImage((Activity)context, ImageLoader.UrlType.USER, getImageSize(), this, id);
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
    
    // @Override
    // protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        // convertToRoundedCornerImage();
    //     super.onDraw(canvas);
    // }
}