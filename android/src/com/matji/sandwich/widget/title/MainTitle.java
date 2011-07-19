package com.matji.sandwich.widget.title;

import com.matji.sandwich.widget.title.buttons.HomeButton;
import com.matji.sandwich.widget.title.buttons.LikeButton;
import com.matji.sandwich.widget.title.buttons.WriteButton;

import android.content.Context;
import android.util.AttributeSet;

public class MainTitle extends TitleContainerTypeB {

	public MainTitle(Context context) {
		super(context);
	}
	
	public MainTitle(Context context, AttributeSet attr) {
		super(context, attr);
	}

	/**
	 * @see com.matji.sandwich.widget.title.TypedTitleContainer#setButtons()
	 */
	@Override
	protected void setButtons() {
		rightButton1 = new HomeButton(context);
		rightButton2 = new WriteButton(context);
		rightButton3 = new LikeButton(context);
	}
}