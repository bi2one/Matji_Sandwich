package com.matji.sandwich.widget.title;

import com.matji.sandwich.R;
import com.matji.sandwich.widget.title.button.TitleButton;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

/**
 * 왼쪽 버튼 1개, 오른쪽 버튼 2개가 있는 타이틀바
 * 
 * @author mozziluv
 *
 */
public abstract class TitleContainerTypeA extends TypedTitleContainer {
	protected TitleButton leftButton1;
	protected TitleButton rightButton1;
	protected TitleButton rightButton2;
	
	public TitleContainerTypeA(Context context) {
		super(context);
	}

	public TitleContainerTypeA(Context context, AttributeSet attr) {
		super(context, attr);
	}
	
	/**
	 * @see com.matji.sandwich.widget.title.TypedTitleContainer#addButtons()
	 */
	@Override
	final protected void addButtons() {
		Drawable titleBg1 = getContext().getResources().getDrawable(R.drawable.title_left_btn_bg);
		Drawable titleBg2 = getContext().getResources().getDrawable(R.drawable.title_btn_bg);
		
		leftButton1.setBackgroundDrawable(titleBg1);
		setTitleBackground(titleBg2);
		rightButton1.setBackgroundDrawable(titleBg2);
		rightButton2.setBackgroundDrawable(titleBg2);
		
		addLeftButton(leftButton1);
		addRightButton(rightButton1);
		addRightButton(rightButton2);
	}	
}