package com.matji.sandwich.widget.title.button;

import android.content.Context;
import android.util.Log;

import com.matji.sandwich.R;

/**
 * 좋아요 버튼
 * 
 * @author mozziluv
 *
 */
public class LikeButton extends TitleImageButton {
//TODO 나중에 Like가 여러개로  나눠질 수 있으니 후에 수정
	public LikeButton(Context context) {
		super(context);
	}

	/**
	 * @see com.matji.sandwich.widget.title.button.TitleImageButton#init()
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		super.init();
		setImageDrawable(context.getResources().getDrawable(R.drawable.icon_navi_like));
	}
	
	/**
	 * @see com.matji.sandwich.widget.title.button.TitleItem#onTitleItemClicked()
	 */
	@Override
	public void onTitleItemClicked() {
		// TODO Auto-generated method stub
		Log.d("Matji", "LikeButtonClicked");
	}
}
