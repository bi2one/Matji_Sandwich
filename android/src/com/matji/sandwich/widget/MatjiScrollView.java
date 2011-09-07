package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.matji.sandwich.R;
import com.matji.sandwich.util.MatjiConstants;

public class MatjiScrollView extends ScrollView {

	/**
	 * 기본 생성자 (XML)
	 * 
	 * @param context
	 * @param attr
	 */
	public MatjiScrollView(Context context, AttributeSet attr) {
		super(context, attr);
		setFadingEdgeLength((int) MatjiConstants.dimen(R.dimen.default_fading_edge_length));
	}
	
	
	/**x
	 * ScrollView 의 기본 fade edge 색상(Scroll 위, 아래의 그림자 같은 부분)을
	 * 지정
	 */
	@Override
	public int getSolidColor() {
		return MatjiConstants.color(R.color.listview_divider1_gray);
	}
}
