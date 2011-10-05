package com.matji.sandwich.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.matji.sandwich.R;
import com.matji.sandwich.http.util.ImageLoader;
import com.matji.sandwich.http.util.RoundRectConvertable;

/**
 * Thumnail 에 사용하기 위해 ImageView 를 확장한 클래스.
 * 
 * @author mozziluv
 *
 */
public class ProfileImageView extends ImageView {
    protected ImageView border;
    protected Context context;
    protected ImageLoader imageLoader;
    private int userId;

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

    protected void init(Context context) {
        this.context = context;
        imageLoader = new ImageLoader(context, R.drawable.user_img54);
        imageLoader.setImageConvertable(new RoundRectConvertable());
    }

    protected ImageLoader.ImageSize getImageSize() {
        return ImageLoader.ImageSize.SSMALL;
    }

    public void setUserId(int id) {
	userId = id;
        imageLoader.DisplayImage((Activity)context, ImageLoader.UrlType.USER, getImageSize(), this, id);
    }

    public void reload() {
	setUserId(userId);
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

    // @Override
    // protected void onDraw(Canvas canvas) {
    // TODO Auto-generated method stub
    // convertToRoundedCornerImage();
    //     super.onDraw(canvas);
    // }
}