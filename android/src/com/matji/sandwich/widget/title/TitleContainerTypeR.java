package com.matji.sandwich.widget.title;

import com.matji.sandwich.widget.title.button.TitleImageButton;

import android.content.Context;
import android.util.AttributeSet;

/**
 * 오른쪽 버튼 3개가 있는 타이틀바
 * 
 * @author mozziluv
 *
 */
public abstract class TitleContainerTypeR extends TypedTitleContainer {

    protected abstract TitleImageButton getRightButton1();
    
	protected TitleImageButton rightButton1;
	
	public TitleContainerTypeR(Context context) {
		super(context);
	}
	
	public TitleContainerTypeR(Context context, AttributeSet attr) {
		super(context, attr);
	}
	
	/**
	 * @see com.matji.sandwich.widget.title.TypedTitleContainer#addButtons()
	 */
	@Override
	final protected void addButtons() {
		addViewInRightContainer(rightButton1);
	}
	
	/**
	 * @see com.matji.sandwich.widget.title.TypedTitleContainer#setButtons()
	 */
	@Override
	final protected void setButtons() {
	    rightButton1 = getRightButton1();
	}
	
    /**
     * @see com.matji.sandwich.widget.title.TypedTitleContainer#setButtons()
     */
	@Override
	protected boolean isExistLeftButton() {
	    return false;
	}
}
