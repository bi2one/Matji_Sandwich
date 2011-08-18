package com.matji.sandwich.widget.indicator;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;

import com.matji.sandwich.R;

public abstract class CheckableIndicator extends Indicator implements Checkable {
    public static final int CHECK_REFERENCE = R.drawable.indicator_check;
    public static final int UNCHECK_REFERENCE = R.drawable.indicator_uncheck;

    public abstract ImageView getCheckView();

    /**
     * 이 indicator를 채우기 위한 값을 받아서 레이아웃을 만든다.
     *
     * @param context 이 레이아웃의 기반 Context
     * @param textRef 레이아웃에 채울 String의 참조 id
     */
    public CheckableIndicator(Context context, int layoutRef) {
	super(context, layoutRef);
    }

    public void setCheck(boolean isCheck) {
	if (isCheck)
	    getCheckView().setImageResource(CHECK_REFERENCE);
	else
	    getCheckView().setImageResource(UNCHECK_REFERENCE);
    }
}