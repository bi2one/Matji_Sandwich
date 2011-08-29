package com.matji.sandwich.widget.title.button;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.matji.sandwich.MainTabActivity;
import com.matji.sandwich.R;

/**
 * {@link MainTabActivity}로 돌아가게 하는 버튼 
 * 
 * @author mozziluv
 *
 */
public class HomeButton extends TitleImageButton {

	public HomeButton(Context context) {
		super(context);
	}

	/**
	 * @see com.matji.sandwich.widget.title.button.TitleImageButton#init()
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		super.init();
		setImageDrawable(context.getResources().getDrawable(R.drawable.btn_home));
	}
	
	/**
	 * @see com.matji.sandwich.widget.title.button.TitleItem#onTitleItemClicked()
	 */
	@Override
	public void onTitleItemClicked() {
		// TODO Auto-generated method stub
		Log.d("Matji", "HomeButtonClicked");
		context.startActivity(new Intent(context, MainTabActivity.class));
	}
}
