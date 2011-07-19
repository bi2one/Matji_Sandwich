package com.matji.sandwich.widget.title;

import com.matji.sandwich.widget.title.buttons.HomeButton;
import com.matji.sandwich.widget.title.buttons.LikeButton;
import com.matji.sandwich.widget.title.buttons.WriteButton;

import android.content.Context;
import android.util.AttributeSet;

public class StoreTitle extends TitleContainerTypeA {

	public StoreTitle(Context context) {
		super(context);
	}

	public StoreTitle(Context context, AttributeSet attr) {
		super(context, attr);
	}

	/**
	 * @see com.matji.sandwich.widget.title.TypedTitleContainer#setButtons()
	 */
	@Override
	protected void setButtons() {
		leftButton1 = new HomeButton(context);
		rightButton1 = new WriteButton(context);
		rightButton2 = new LikeButton(context);
	}
}