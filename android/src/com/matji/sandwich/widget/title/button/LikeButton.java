package com.matji.sandwich.widget.title.button;

import com.matji.sandwich.R;

import android.content.Context;
import android.util.Log;

/**
 * 좋아요 버튼
 * 
 * @author mozziluv
 *
 */
public class LikeButton extends TitleButton {
//TODO 나중에 Like가 여러개로  나눠질 수 있으니 후에 수정
	public LikeButton(Context context) {
		super(context);
		init();
	}

	/**
	 * @see com.matji.sandwich.widget.title.button.TitleButton#init()
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		setImageDrawable(context.getResources().getDrawable(R.drawable.btn_like));
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
