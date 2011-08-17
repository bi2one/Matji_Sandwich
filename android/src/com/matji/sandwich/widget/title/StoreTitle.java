package com.matji.sandwich.widget.title;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.StoreMainActivity;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.widget.title.button.HomeButton;
import com.matji.sandwich.widget.title.button.LikeButton;
import com.matji.sandwich.widget.title.button.WriteButton;

/**
 * {@link StoreMainActivity}에서 사용하는 Titlebar.
 * Titlebar에서 {@link Store} 객체를 사용해야 하므로 생성자에서 Store 정보를 무조건 받도록 한다. 
 * 
 * @author mozziluv
 *
 */
public class StoreTitle extends TitleContainerTypeA {
    private Store store;
    
	public StoreTitle(Context context, Store store) {
		super(context);
	    this.store = store;
	}

	public StoreTitle(Context context, AttributeSet attr) {
		super(context, attr);
	}

	/**
	 * @see com.matji.sandwich.widget.title.TypedTitleContainer#setButtons()
	 */
	@Override
	protected void setButtons() {
		leftButton1 = new HomeButton(getContext());
		rightButton1 = new WriteButton(getContext());
		rightButton2 = new LikeButton(getContext());
	}
}