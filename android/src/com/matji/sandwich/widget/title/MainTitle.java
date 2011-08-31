package com.matji.sandwich.widget.title;

import com.matji.sandwich.widget.title.button.StorePostWriteButton;
import com.matji.sandwich.widget.title.button.SearchButton;
import com.matji.sandwich.widget.title.button.SettingsButton;
import com.matji.sandwich.widget.title.button.TitleImageButton;

import android.content.Context;
import android.util.AttributeSet;

public class MainTitle extends TitleContainerTypeRRR {

	public MainTitle(Context context) {
		super(context);
	}
	
	public MainTitle(Context context, AttributeSet attr) {
		super(context, attr);
	}

    @Override
    protected TitleImageButton getRightButton1() {
        // TODO Auto-generated method stub
        return new SearchButton(getContext());
    }

    @Override
    protected TitleImageButton getRightButton2() {
        // TODO Auto-generated method stub
        return new StorePostWriteButton(getContext());
    }

    @Override
    protected TitleImageButton getRightButton3() {
        // TODO Auto-generated method stub
        return new SettingsButton(getContext());
    }
}