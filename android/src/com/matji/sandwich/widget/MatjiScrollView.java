package com.matji.sandwich.widget;

import com.matji.sandwich.R;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class MatjiScrollView extends ScrollView {

	/**
	 * 기본 생성자 (XML)
	 * 
	 * @param context
	 * @param attr
	 */
	public MatjiScrollView(Context context, AttributeSet attr) {
		super(context, attr);
		setFadingEdgeLength((int) getResources().getDimension(R.dimen.default_fading_edge_length));
	}
	
	
	/**
	 * ScrollView 의 기본 fade edge 색상(Scroll 위, 아래의 그림자 같은 부분)을 
	 * 검은색으로 설정.
	 */
	@Override
	public int getSolidColor() {
//		return super.getSolidColor();
		return Color.rgb(0x0, 0x0, 0x0);
	}
}
