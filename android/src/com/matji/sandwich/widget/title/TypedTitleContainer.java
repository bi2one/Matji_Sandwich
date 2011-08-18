package com.matji.sandwich.widget.title;

import android.content.Context;
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