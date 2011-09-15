package com.matji.sandwich.widget.cell;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.R;
import com.matji.sandwich.http.util.ImageLoader;
import com.matji.sandwich.http.util.RoundRectConvertable;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.ProfileImageView;

/**
 * Cell에서 사용하는 ProfileImageView
 * 다운로드 받는 이미지의 크기가 더 큼.
 * 
 * @author mozziluv
 *
 */
public class CellProfileImageView extends ProfileImageView {

    public CellProfileImageView(Context context) {
        super(context);
        showInsetBackground();
    }

    public CellProfileImageView(Context context, AttributeSet attr) {
        super(context, attr);
    }

    public CellProfileImageView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
    }
    

    @Override
    protected void init(Context context) {
        this.context = context;
        imageLoader = new ImageLoader(context, R.drawable.user_img90);
        imageLoader.setImageConvertable(new RoundRectConvertable(
                MatjiConstants.dimen(R.dimen.default_round_pixel2),
                MatjiConstants.dimenInt(R.dimen.default_round_inset2)));
    }


    @Override
    protected ImageLoader.ImageSize getImageSize() {
        return ImageLoader.ImageSize.SSMALL;
    }
    
    @Override
    public void showInsetBackground() {
        if (border == null) findBorder();
        border.setImageResource(R.drawable.user_img90_bg);
    }
    
    @Override
    public void showReliefBackground() {}
}