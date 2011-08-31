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
public class SearchButton extends TitleImageButton {

    private final int page;
    
    public SearchButton(Context context) {
        super(context);
        this.page = 0;
    }
    
    public SearchButton(Context context, int page) {
        super(context);
        this.page = page; 
    }

	/**
	 * @see com.matji.sandwich.widget.title.button.TitleImageButton#init()
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
		
		Intent intent = new Intent(context, SearchActivity.class);
		intent.putExtra(SearchActivity.PAGE, page);
		context.startActivity(intent);
	}
}
