package com.matji.sandwich.widget.title.button;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.matji.sandwich.LikeUserListActivity;
import com.matji.sandwich.R;
import com.matji.sandwich.data.MatjiData;

/**
 * {@link LikeUserListActivity}로 이동하게 하는 버튼
 * 
 * @author mozziluv
 *
 */
public class LikeUserListButton extends TitleImageButton {
    private final MatjiData data;
    
	public LikeUserListButton(Context context, MatjiData data) {
		super(context);
        this.data = data;
	}

	/**
	 * @see com.matji.sandwich.widget.title.button.TitleImageButton#init()
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		super.init();
		setImageDrawable(context.getResources().getDrawable(R.drawable.icon_navi_like_list));
	}
	
	/**
	 * @see com.matji.sandwich.widget.title.button.TitleItem#onTitleItemClicked()
	 */
	@Override
	public void onTitleItemClicked() {
		// TODO Auto-generated method stub
		Log.d("Matji", "LikeUserListButtonClicked");
		Intent intent = new Intent(getContext(), LikeUserListActivity.class);
		intent.putExtra(LikeUserListActivity.DATA, data);
		getContext().startActivity(intent);
	}
}
