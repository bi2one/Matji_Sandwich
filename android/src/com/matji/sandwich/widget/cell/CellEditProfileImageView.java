package com.matji.sandwich.widget.cell;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.R;

/**
 * Cell에서 사용하는 ProfileImageView
 * 다운로드 받는 이미지의 크기가 더 큼.
 * 
 * @author mozziluv
 *
 */
public class CellEditProfileImageView extends CellProfileImageView {

    public CellEditProfileImageView(Context context) {
        super(context);
    }

    public CellEditProfileImageView(Context context, AttributeSet attr) {
        super(context, attr);
    }

    public CellEditProfileImageView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
    }
    
    @Override
    public void showInsetBackground() {
        if (border == null) findBorder();
        border.setImageResource(R.drawable.user_img90_bg_edit);
    }
}