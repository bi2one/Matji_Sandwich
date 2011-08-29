package com.matji.sandwich.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.matji.sandwich.R;
import com.matji.sandwich.util.MatjiConstants;

/**
 * 기본 폰트는 한글이 bold 스타일을 지정해주더라도 적용이 되지 않기 때문에
 * 이 클래스를 사용해 bold 스타일을 적용합니다.
 * 
 * @author mozziluv
 *
 */
public class PalatinoiFontTextView extends TextView {

    public PalatinoiFontTextView(Context context) {
        super(context);
        init();
    }

    public PalatinoiFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PalatinoiFontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    
    protected void init() {
        setTypeface(Typeface.createFromAsset(getContext().getAssets(), MatjiConstants.string(R.string.path_palai)));
    }
}
