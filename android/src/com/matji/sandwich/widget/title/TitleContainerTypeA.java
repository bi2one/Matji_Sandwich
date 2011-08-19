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
    
    protected abstract TitleButton getLeftButton1();
    protected abstract TitleButton getRightButton1();
    protected abstract TitleButton getRightButton2();
    
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
		addLeftButton(leftButton1);
		addRightButton(rightButton1);
		addRightButton(rightButton2);
	}
	
	/**
	 * @see com.matji.sandwich.widget.title.TypedTitleContainer#setButtons()
	 */
	@Override
	final protected void setButtons() {
	    leftButton1 = getLeftButton1();
	    rightButton1 = getRightButton1();
	    rightButton2 = getRightButton2();
	}

	/**
     * @see com.matji.sandwich.widget.title.TypedTitleContainer#isExistLeftButton()
     */
	@Override
	protected boolean isExistLeftButton() {
	    return true;
	}
}