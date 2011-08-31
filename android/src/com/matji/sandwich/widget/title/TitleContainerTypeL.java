package com.matji.sandwich.widget.title;

import com.matji.sandwich.widget.title.button.TitleImageButton;

import android.content.Context;
import android.util.AttributeSet;

/**
 * 왼쪽 버튼 1개가 있는 타이틀바
 * 
 * @author mozziluv
 *
 */
public abstract class TitleContainerTypeL extends TypedTitleContainer {
	
    protected abstract TitleImageButton getLeftButton1();
    
    protected TitleImageButton leftButton1;
	
	public TitleContainerTypeL(Context context) {
		super(context);
	}

	public TitleContainerTypeL(Context context, AttributeSet attr) {
		super(context, attr);
	}
	
	/**
	 * @see com.matji.sandwich.widget.title.TypedTitleContainer#addButtons()
	 */
	@Override
	final protected void addButtons() {
	    addViewInLeftContainer(leftButton1);
	}
	
	/**
	 * @see com.matji.sandwich.widget.title.TypedTitleContainer#setButtons()
	 */
	@Override
	final protected void setButtons() {
	    leftButton1 = getLeftButton1();
	}
	
	/**
	 * @see com.matji.sandwich.widget.title.TypedTitleContainer#isExistLeftButton()
	 */
	@Override
	final protected boolean isExistLeftButton() {
	    return true;
	}
}