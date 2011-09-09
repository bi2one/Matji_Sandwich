package com.matji.sandwich.widget.title.button;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.matji.sandwich.R;
import com.matji.sandwich.SettingsActivity;

/**
 * TODO
 *
 * @author mozziluv
 *
 */
public class SettingButton extends TitleImageButton {

	public SettingButton(Context context) {
		super(context);
	}

	/**
	 * @see com.matji.sandwich.widget.title.button.TitleImageButton#init()
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		super.init();
		setImageDrawable(context.getResources().getDrawable(R.drawable.icon_navi_setting));
	}
	
	/**
	 * @see com.matji.sandwich.widget.title.button.TitleItem#onTitleItemClicked()
	 */
	@Override
	public void onTitleItemClicked() {
		// TODO Auto-generated method stub
		Log.d("Matji", "SettingsButtonClicked");
		
		getContext().startActivity(new Intent(getContext(), SettingsActivity.class));
	}
}
