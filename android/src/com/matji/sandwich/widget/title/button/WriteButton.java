package com.matji.sandwich.widget.title.button;

import com.matji.sandwich.R;

import android.content.Context;
import android.util.Log;

/**
 * 쓰기 버튼
 * 
 * @author mozziluv
 *
 */
public class WriteButton extends TitleButton {
//TODO 나중에 Post write, Comment write 등 나눠질 수 있으니 후에 수정
	public WriteButton(Context context) {
		super(context);
		init();
	}

	/**
	 * @see com.matji.sandwich.widget.title.button.TitleButton#init()
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		setImageDrawable(context.getResources().getDrawable(R.drawable.icon_doc_edit));
	}
	
	/**
	 * @see com.matji.sandwich.widget.title.button.TitleItem#onTitleItemClicked()
	 */
	@Override
	public void onTitleItemClicked() {
		// TODO Auto-generated method stub
		Log.d("Matji", "WriteButtonClicked");
	}
}
