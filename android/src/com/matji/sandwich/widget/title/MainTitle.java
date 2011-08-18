package com.matji.sandwich.widget.title;

import com.matji.sandwich.widget.title.button.StorePostWriteButton;
import com.matji.sandwich.widget.title.button.SearchButton;
import com.matji.sandwich.widget.title.button.SettingsButton;
import com.matji.sandwich.widget.title.button.TitleButton;

import android.content.Context;
import android.util.AttributeSet;

public class MainTitle extends TitleContainerTypeB {

	public MainTitle(Context context) {
		super(context);
	}
	
	public MainTitle(Context context, AttributeSet attr) {
		super(context, attr);
	}

    @Override
    protected TitleButton getRightButton1() {
        // TODO Auto-generated method stub
        return new SearchButton(getContext());
    }

    @Override
    protected TitleButton getRightButton2() {
        // TODO Auto-generated method stub
        return new StorePostWriteButton(getContext());
    }

    @Override
    protected TitleButton getRightButton3() {
        // TODO Auto-generated method stub
        return new SettingsButton(getContext());
    }
}