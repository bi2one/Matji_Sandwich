package com.matji.sandwich.widget.indicator;

import android.content.Context;
import android.widget.TextView;

import com.matji.sandwich.R;

public class RoundCenterIndicator extends Indicator {
    public static final int LAYOUT_REFERENCE = R.layout.indicator_round_center;
    private TextView labelView;

    /**
     * 이 indicator를 채우기 위한 값을 받아서 레이아웃을 만든다.
     *
     * @param context 이 레이아웃의 기반 Context
     * @param textRef 레이아웃에 채울 String의 참조 id
     */
    public RoundCenterIndicator(Context context, int textRef) {
	super(context, LAYOUT_REFERENCE);

	labelView = (TextView)findViewById(R.id.indicator_round_center_label);
	labelView.setText(textRef);
    }
}