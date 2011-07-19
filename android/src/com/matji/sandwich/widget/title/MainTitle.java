package com.matji.sandwich.widget.title;

import com.matji.sandwich.widget.title.buttons.AddStoreButton;
import com.matji.sandwich.widget.title.buttons.DetailInfoButton;
import com.matji.sandwich.widget.title.buttons.SettingsButton;

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
		rightButton1 = new AddStoreButton(context);
		rightButton2 = new DetailInfoButton(context);
		rightButton3 = new SettingsButton(context);
	}
}