package com.matji.sandwich.widget.title;

import com.matji.sandwich.widget.title.button.HomeButton;
import com.matji.sandwich.widget.title.button.PageDownButton;
import com.matji.sandwich.widget.title.button.PageUpButton;
import com.matji.sandwich.widget.title.button.TitleButton;

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
	 * {@link Pageable} 객체를 각 버튼에 지정한다.
	 * @param pageable  버튼에 지정할 {@link Pageable} 객체
	 */
	public void setPageable(Pageable pageable) {
		((PageUpButton) rightButton1).setPageable(pageable);
		((PageDownButton) rightButton2).setPageable(pageable);
	}
	
	public interface Pageable {
		public void prev();
		public void next();
	}

    @Override
    protected TitleButton getLeftButton1() {
        return  new HomeButton(getContext());
    }

    @Override
    protected TitleButton getRightButton1() {
        return new PageUpButton(getContext());
    }

    @Override
    protected TitleButton getRightButton2() {
        return new PageDownButton(getContext());
    }
}