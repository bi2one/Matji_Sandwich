package com.matji.sandwich.widget.title.buttons;

import com.matji.sandwich.R;

import android.content.Context;
import android.util.Log;

/**
 * TODO
 *
 * @author mozziluv
 *
 */
public class DetailInfoButton extends TitleButton {

	public DetailInfoButton(Context context) {
		super(context);
		init();
	}

	/**
	 * @see com.matji.sandwich.widget.title.buttons.TitleButton#init()
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		setImageDrawable(context.getResources().getDrawable(R.drawable.icon_lightbulb));
	}
	
	/**
	 * @see com.matji.sandwich.widget.title.buttons.TitleItem#onTitleItemClicked()
	 */
	@Override
	public void onTitleItemClicked() {
		// TODO Auto-generated method stub
		Log.d("Matji", "DetailInfoButtonClicked");
	}
}
