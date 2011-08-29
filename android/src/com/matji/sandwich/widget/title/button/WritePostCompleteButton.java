package com.matji.sandwich.widget.title.button;

import android.content.Context;
import android.util.Log;

import com.matji.sandwich.R;
import com.matji.sandwich.util.MatjiConstants;

/**
 * 쓰기 버튼
 * 
 * @author mozziluv
 *
 */
public class WritePostCompleteButton extends TitleButton {
//TODO 나중에 Post write, Comment write 등 나눠질 수 있으니 후에 수정
	public WritePostCompleteButton(Context context) {
		super(context);
	}

	/**
	 * @see com.matji.sandwich.widget.title.button.TitleButton#init()
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		super.init();
		setText(MatjiConstants.string(R.string.default_string_complete));
		enlarge();
	}
	
	/**
	 * @see com.matji.sandwich.widget.title.button.TitleItem#onTitleItemClicked()
	 */
	@Override
	public void onTitleItemClicked() {
		// TODO Auto-generated method stub
		Log.d("Matji", "CompleteClicked");
	}
}
