package com.matji.sandwich.widget.title;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.matji.sandwich.R;
import com.matji.sandwich.widget.title.button.HomeButton;
import com.matji.sandwich.widget.title.button.ScreenShotButton;
import com.matji.sandwich.widget.title.button.TitleImageButton;

public class StoreLocationTitle extends TitleContainerTypeLR {
    public StoreLocationTitle(Context context) {
	super(context);
	init();
    }

    public StoreLocationTitle(Context context, AttributeSet attr) {
	super(context, attr);
	init();
    }

    private void init() {
	setTitle(R.string.store_location_title);
    }

    public void setView(View view) {
	((ScreenShotButton)rightButton1).setView(view);
    }

    protected TitleImageButton getLeftButton1() {
	return new HomeButton(getContext());
    }

    protected TitleImageButton getRightButton1() {
	return new ScreenShotButton(getContext());
    }
}