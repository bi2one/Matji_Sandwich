package com.matji.sandwich.widget.title;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.widget.title.button.WritePostCompleteButton;
import com.matji.sandwich.widget.title.button.HomeButton;
import com.matji.sandwich.widget.title.button.TitleButton;
import com.matji.sandwich.widget.title.button.TitleImageButton;

public class CompleteTitle extends TitleContainerTypeD {

	public CompleteTitle(Context context) {
		super(context);
	}

	public CompleteTitle(Context context, AttributeSet attr) {
		super(context, attr);
	}

    @Override
    protected TitleImageButton getLeftButton1() {
        return new HomeButton(getContext());
    }
    
    @Override
    protected TitleButton getRightButton1() {
        return new WritePostCompleteButton(getContext());
    }
}