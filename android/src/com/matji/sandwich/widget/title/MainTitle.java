package com.matji.sandwich.widget.title;

import com.matji.sandwich.widget.title.button.ActivityButton;
import com.matji.sandwich.widget.title.button.SearchButton;
import com.matji.sandwich.widget.title.button.SettingsButton;

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
		rightButton1 = new SearchButton(getContext());
		rightButton2 = new ActivityButton(getContext());
		rightButton3 = new SettingsButton(getContext());
	}
}