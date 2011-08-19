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

    protected abstract TitleButton getRightButton1();
    protected abstract TitleButton getRightButton2();
    protected abstract TitleButton getRightButton3();
    
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
		addRightButton(rightButton1);
		addRightButton(rightButton2);
		addRightButton(rightButton3);
	}
	
	/**
	 * @see com.matji.sandwich.widget.title.TypedTitleContainer#setButtons()
	 */
	@Override
	final protected void setButtons() {
	    rightButton1 = getRightButton1();
	    rightButton2 = getRightButton2();
	    rightButton3 = getRightButton3();
	}
	
    /**
     * @see com.matji.sandwich.widget.title.TypedTitleContainer#setButtons()
     */
	@Override
	protected boolean isExistLeftButton() {
	    return false;
	}
}
