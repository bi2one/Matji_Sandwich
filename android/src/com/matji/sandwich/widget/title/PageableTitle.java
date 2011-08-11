package com.matji.sandwich.widget.title;

import com.matji.sandwich.widget.title.button.HomeButton;
import com.matji.sandwich.widget.title.button.PageDownButton;
import com.matji.sandwich.widget.title.button.PageUpButton;

import android.content.Context;
import android.util.AttributeSet;

public class PageableTitle extends TitleContainerTypeA {

	public PageableTitle(Context context) {
		super(context);
	}

	public PageableTitle(Context context, AttributeSet attr) {
		super(context, attr);
	}

	/**
	 * @see com.matji.sandwich.widget.title.TypedTitleContainer#setButtons()
	 */
	@Override
	protected void setButtons() {
		leftButton1 = new HomeButton(getContext());
		rightButton1 = new PageUpButton(getContext());
		rightButton2 = new PageDownButton(getContext());
	}

	public void setPageable(Pageable pageable) {
		((PageUpButton) rightButton1).setPageable(pageable);
		((PageDownButton) rightButton2).setPageable(pageable);
	}
	
	public interface Pageable {
		public void prev();
		public void next();
	}
}