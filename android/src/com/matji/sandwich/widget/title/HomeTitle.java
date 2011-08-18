package com.matji.sandwich.widget.title;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.widget.title.button.HomeButton;
import com.matji.sandwich.widget.title.button.TitleButton;

public class HomeTitle extends TitleContainerTypeC {

	public HomeTitle(Context context) {
		super(context);
	}

	public HomeTitle(Context context, AttributeSet attr) {
		super(context, attr);
	}

    @Override
    protected TitleButton getLeftButton1() {
        return new HomeButton(getContext());
    }
}