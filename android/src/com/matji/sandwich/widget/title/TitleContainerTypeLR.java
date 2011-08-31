package com.matji.sandwich.widget.title;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.widget.title.button.TitleImageButton;

/**
 * 왼쪽에 버튼1개와 오른쪽에 일반 버튼 1개(텍스트 버튼)가 있는 타이틀바
 * 
 * @author mozziluv
 *
 */
public abstract class TitleContainerTypeLR extends TypedTitleContainer {
	
    protected abstract TitleImageButton getLeftButton1();
    protected abstract TitleImageButton getRightButton1();
    
    protected TitleImageButton leftButton1;
    protected TitleImageButton rightButton1;
	
	public TitleContainerTypeLR(Context context) {
		super(context);
	}

	public TitleContainerTypeLR(Context context, AttributeSet attr) {
		super(context, attr);
	}
	
	/**
	 * @see com.matji.sandwich.widget.title.TypedTitleContainer#addButtons()
	 */
	@Override
	final protected void addButtons() {
        addViewInLeftContainer(leftButton1);
        addViewInRightContainer(rightButton1);
	}
	
	/**
	 * @see com.matji.sandwich.widget.title.TypedTitleContainer#setButtons()
	 */
	@Override
	final protected void setButtons() {
	    leftButton1 = getLeftButton1();
	    rightButton1 = getRightButton1();
	}
	
	/**
	 * @see com.matji.sandwich.widget.title.TypedTitleContainer#isExistLeftButton()
	 */
	@Override
	final protected boolean isExistLeftButton() {
	    return true;
	}
}