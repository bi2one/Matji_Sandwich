package com.matji.sandwich.widget.indicator;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.matji.sandwich.R;

public class RoundHeadIndicator extends Indicator {
    public static final int LAYOUT_REFERENCE = R.layout.indicator_round_head;
    private TextView labelView;
    
    public RoundHeadIndicator(Context context, String label) {
	super(context, LAYOUT_REFERENCE);
	labelView = (TextView)findViewById(R.id.label);
	labelView.setText(label);
    }
}
