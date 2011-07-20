package com.matji.sandwich.widget.title;

import com.matji.sandwich.R;
import com.matji.sandwich.widget.title.button.TitleButton;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
		Drawable titleBg1 = getContext().getResources().getDrawable(R.drawable.title_left_btn_bg);
		Drawable titleBg2 = getContext().getResources().getDrawable(R.drawable.title_btn_bg);
		
		setTitleBackground(titleBg1);
		rightButton1.setBackgroundDrawable(titleBg2);
		rightButton2.setBackgroundDrawable(titleBg2);
		rightButton3.setBackgroundDrawable(titleBg2);
		
		addRightButton(rightButton1);
		addRightButton(rightButton2);
		addRightButton(rightButton3);
	}
}
