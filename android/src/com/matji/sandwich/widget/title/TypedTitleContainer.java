package com.matji.sandwich.widget.title;

import com.matji.sandwich.R;
import com.matji.sandwich.util.MatjiConstants;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

/**
 * 
 * 
 * @author mozziluv
 *
 */
public abstract class TypedTitleContainer extends TitleContainer {
	/**
	 * 각 위치에 맞는 버튼을 set 한다.
	 */
	protected abstract void setButtons();
	/**
	 * 버튼을 추가한다. 
	 */
	protected abstract void addButtons();

	protected final Drawable titleBgLeft = MatjiConstants.drawable(R.drawable.title_left_btn_bg);
	protected final Drawable titleBgRight = MatjiConstants.drawable(R.drawable.title_btn_bg);

	
	public TypedTitleContainer(Context context) {
		super(context);
		setButtons();
		addButtons();
	}
	
	public TypedTitleContainer(Context context, AttributeSet attr) {
		super(context, attr);
		setButtons();
		addButtons();
	}
}