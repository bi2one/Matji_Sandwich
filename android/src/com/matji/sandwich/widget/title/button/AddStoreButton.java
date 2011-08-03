package com.matji.sandwich.widget.title.button;

import com.matji.sandwich.R;

import android.content.Context;
import android.util.Log;

/**
 * TODO
 *
 * @author mozziluv
 *
 */
public class AddStoreButton extends TitleButton {

	public AddStoreButton(Context context) {
		super(context);
	}

	/**
	 * @see com.matji.sandwich.widget.title.button.TitleButton#init()
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		super.init();
		setImageDrawable(context.getResources().getDrawable(R.drawable.btn_register_store));
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
