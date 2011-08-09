package com.matji.sandwich.widget.title;

import com.matji.sandwich.widget.title.button.TitleButton;

import android.content.Context;
import android.util.AttributeSet;

/**
 * 오른쪽 버튼 3개가 있는 타이틀바
 * 
 * @author mozziluv
 *
 */
public abstract class TitleContainerTypeB extends TypedTitleContainer {
	protected TitleButton rightButton1;
	protected TitleButton rightButton2;
	protected TitleButton rightButton3;
	
	public TitleContainerTypeB(Context context) {
		super(context);
	}
	
	public TitleContainerTypeB(Context context, AttributeSet attr) {
		super(context, attr);
	}
	
	/**
	 * @see com.matji.sandwich.widget.title.TypedTitleContainer#addButtons()
	 */
	@Override
	final protected void addButtons() {
		setTitleBackground(titleBgLeft);
		rightButton1.setBackgroundDrawable(titleBgRight);
		rightButton2.setBackgroundDrawable(titleBgRight);
		rightButton3.setBackgroundDrawable(titleBgRight);
		
		addRightButton(rightButton1);
		addRightButton(rightButton2);
		addRightButton(rightButton3);
	}
}
