package com.matji.sandwich.widget;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 기본 폰트는 한글이 bold 스타일을 지정해주더라도 적용이 되지 않기 때문에
 * 이 클래스를 사용해 bold 스타일을 적용합니다.
 * 
 * @author mozziluv
 *
 */
public class BoldStyleTextView extends TextView {

    public BoldStyleTextView(Context context) {
        super(context);
        setPaintFlags(getPaintFlags()|Paint.FAKE_BOLD_TEXT_FLAG);

    }

    public BoldStyleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPaintFlags(getPaintFlags()|Paint.FAKE_BOLD_TEXT_FLAG);
    }

    public BoldStyleTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setPaintFlags(getPaintFlags()|Paint.FAKE_BOLD_TEXT_FLAG);
    }
    
}
