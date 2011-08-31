package com.matji.sandwich.widget.title.button;

import android.content.Context;
import android.util.Log;

import com.matji.sandwich.R;

/**
 * TODO
 *
 * @author mozziluv
 *
 */
public class AddStoreButton extends TitleImageButton {

	public AddStoreButton(Context context) {
		super(context);
	}

	/**
	 * @see com.matji.sandwich.widget.title.button.TitleImageButton#init()
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		super.init();
		setImageDrawable(context.getResources().getDrawable(R.drawable.icon_navi_delicious_write));
	}
	
	/**
	 * @see com.matji.sandwich.widget.title.button.TitleItem#onTitleItemClicked()
	 */
	@Override
	public void onTitleItemClicked() {
		// TODO Auto-generated method stub
		Log.d("Matji", "AddStoreButtonClicked");
	}
}
