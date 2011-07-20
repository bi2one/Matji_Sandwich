package com.matji.sandwich.widget.title.button;

import com.matji.sandwich.R;

import android.content.Context;
import android.util.Log;

/**
 * Main 이 되는 Activity 로 돌아가게 하는 역할을 하는 버튼 
 * 
 * @author mozziluv
 *
 */
public class HomeButton extends TitleButton {

	public HomeButton(Context context) {
		super(context);
		init();
	}

	/**
	 * @see com.matji.sandwich.widget.title.button.TitleButton#init()
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		setImageDrawable(context.getResources().getDrawable(R.drawable.icon_home));
	}
	
	/**
	 * @see com.matji.sandwich.widget.title.button.TitleItem#onTitleItemClicked()
	 */
	@Override
	public void onTitleItemClicked() {
		// TODO Auto-generated method stub
		Log.d("Matji", "HomeButtonClicked");
	}
}
