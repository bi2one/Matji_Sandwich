package com.matji.sandwich.widget.title.button;

import com.matji.sandwich.R;
import com.matji.sandwich.SearchActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 검색 버튼.
 *
 * @author mozziluv
 *
 */
public class SearchButton extends TitleButton {

	public SearchButton(Context context) {
		super(context);
	}

	/**
	 * @see com.matji.sandwich.widget.title.button.TitleButton#init()
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		super.init();
		setImageDrawable(context.getResources().getDrawable(R.drawable.icon_navi_search));
	}
	
	/**
	 * @see com.matji.sandwich.widget.title.button.TitleItem#onTitleItemClicked()
	 */
	@Override
	public void onTitleItemClicked() {
		// TODO Auto-generated method stub
		Log.d("Matji", "SearchButtonClicked");
		context.startActivity(new Intent(context, SearchActivity.class));
	}
}
