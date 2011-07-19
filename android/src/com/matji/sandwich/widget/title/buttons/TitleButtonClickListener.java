package com.matji.sandwich.widget.title.buttons;

import android.view.View;

/**
 * 
 * @author mozziluv
 *
 */
public class TitleButtonClickListener implements View.OnClickListener {

	@Override
	public void onClick(View v) {
		((TitleItem) v).onTitleItemClicked();
	}
}
