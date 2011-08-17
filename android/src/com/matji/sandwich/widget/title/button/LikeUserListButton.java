package com.matji.sandwich.widget.title.button;

import com.matji.sandwich.LikeUserListActivity;
import com.matji.sandwich.R;
import com.matji.sandwich.data.MatjiData;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * {@link LikeUserListActivity}로 이동하게 하는 버튼
 * 
 * @author mozziluv
 *
 */
public class LikeUserListButton extends TitleButton {
    private final MatjiData data;
    
	public LikeUserListButton(Context context, MatjiData data) {
		super(context);
        this.data = data;
	}

	/**
	 * @see com.matji.sandwich.widget.title.button.TitleButton#init()
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		super.init();
		setImageDrawable(context.getResources().getDrawable(R.drawable.btn_like));
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
