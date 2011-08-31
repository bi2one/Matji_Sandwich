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
public abstract class TitleContainerTypeRRR extends TypedTitleContainer {

    protected abstract TitleImageButton getRightButton1();
    protected abstract TitleImageButton getRightButton2();
    protected abstract TitleImageButton getRightButton3();
    
	protected TitleImageButton rightButton1;
	protected TitleImageButton rightButton2;
	protected TitleImageButton rightButton3;
	
	public TitleContainerTypeRRR(Context context) {
		super(context);
	}
	
	public TitleContainerTypeRRR(Context context, AttributeSet attr) {
		super(context, attr);
	}
	
	/**
	 * @see com.matji.sandwich.widget.title.TypedTitleContainer#addButtons()
	 */
	@Override
	final protected void addButtons() {
		addViewInRightContainer(rightButton1);
		addViewInRightContainer(rightButton2);
		addViewInRightContainer(rightButton3);
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
