package com.matji.sandwich.widget.title;

import com.matji.sandwich.widget.title.button.TitleButton;

import android.content.Context;
import android.util.AttributeSet;

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
		addRightButton(rightButton1);
		addRightButton(rightButton2);
		addRightButton(rightButton3);
	}
}
