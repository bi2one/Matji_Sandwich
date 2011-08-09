package com.matji.sandwich.widget.title;

import com.matji.sandwich.widget.title.button.HomeButton;

import android.content.Context;
import android.util.AttributeSet;

public class HomeTitle extends TitleContainerTypeC {

	public HomeTitle(Context context) {
		super(context);
	}

	public HomeTitle(Context context, AttributeSet attr) {
		super(context, attr);
	}

	/**
	 * @see com.matji.sandwich.widget.title.TypedTitleContainer#setButtons()
	 */
	@Override
	protected void setButtons() {
		leftButton1 = new HomeButton(getContext());
	}
}