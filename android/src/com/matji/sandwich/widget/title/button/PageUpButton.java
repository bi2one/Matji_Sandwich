package com.matji.sandwich.widget.title.button;

import android.content.Context;
import android.util.Log;

import com.matji.sandwich.R;
import com.matji.sandwich.widget.title.PageableTitle.Pageable;

/**
 * {@link Pageable} 인터페이스를 구현한 객체를 전달받아 해당 객체의 {@link Pageable#prev()} 메소드를 실행한다.
 * 
 * @author mozziluv
 *
 */
public class PageUpButton extends TitleImageButton {

	private Pageable pageable;
	
	public PageUpButton(Context context) {
		super(context);
	}
	
	/**
	 * 파라미터로 전달받은 {@link Pageable}를 저장한다.
	 * @param pageable
	 */
	public void setPageable(Pageable pageable) {
		this.pageable = pageable;
	}

	/**
	 * @see com.matji.sandwich.widget.title.button.TitleImageButton#init()
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		super.init();
		setImageDrawable(context.getResources().getDrawable(R.drawable.icon_navi_previous));
	}
	
	/**
	 * @see com.matji.sandwich.widget.title.button.TitleItem#onTitleItemClicked()
	 */
	@Override
	public void onTitleItemClicked() {
		// TODO Auto-generated method stub
		Log.d("Matji", "PageUpButtonClicked");
		pageable.prev();
	}
}
