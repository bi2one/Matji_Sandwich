package com.matji.sandwich.widget.title;

import com.matji.sandwich.widget.title.button.TitleButton;

import android.content.Context;
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
		leftButton1.setBackgroundDrawable(titleBgLeft);
		setTitleBackground(titleBgRight);
		rightButton1.setBackgroundDrawable(titleBgRight);
		rightButton2.setBackgroundDrawable(titleBgRight);
		
		addLeftButton(leftButton1);
		addRightButton(rightButton1);
		addRightButton(rightButton2);
	}	
}