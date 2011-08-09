package com.matji.sandwich.widget.title;

import com.matji.sandwich.widget.title.button.TitleButton;

import android.content.Context;
import android.util.AttributeSet;

/**
 * 왼쪽 버튼 1개가 있는 타이틀바
 * 
 * @author mozziluv
 *
 */
public abstract class TitleContainerTypeC extends TypedTitleContainer {
	protected TitleButton leftButton1;
	
	public TitleContainerTypeC(Context context) {
		super(context);
	}

	public TitleContainerTypeC(Context context, AttributeSet attr) {
		super(context, attr);
	}
	
	/**
	 * @see com.matji.sandwich.widget.title.TypedTitleContainer#addButtons()
	 */
	@Override
	final protected void addButtons() {
		leftButton1.setBackgroundDrawable(titleBgLeft);
		setTitleBackground(titleBgRight);

		addLeftButton(leftButton1);
	}	
}