package com.matji.sandwich.widget;

import com.matji.sandwich.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * EditText 왼쪽에 버튼이 있는 뷰.
 * 
 * @author mozziluv
 *
 */
public abstract class InputBar extends LinearLayout {
	protected abstract void setTextFieldAttribute();
	protected abstract void setConfirmButtonAttribute();
	
	protected EditText textField;
	protected Button confirmButton;
	
	/**
	 * 기본 생성자. (XML)
	 * 
	 * @param context
	 * @param attr
	 */
	public InputBar(Context context, AttributeSet attr) {
		super(context, attr);
		init();
	}

	/**
	 * 초기화한다.
	 * 이 클래스를 확장한 클래스에서 EditText의 속성과 Button의 속성에 더 추가할 것이 있을 때
	 * {@link InputBar#setTextFieldAttribute()}와 {@link InputBar#setConfirmButtonAttribute()}를 이용해 추가해준다.
	 */
	protected void init() {
		LayoutInflater inflate = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflate.inflate(R.layout.input_bar, this);
		
		textField = (EditText) findViewById(R.id.input_bar_text_field);
		confirmButton = (Button) findViewById(R.id.input_bar_confirm_btn);

		setTextFieldAttribute();
		setConfirmButtonAttribute();
	}

	/**
	 * EditText는 이 클래스와 이 클래스를 확장한 클래스에서만 접근이 가능하도록 하고,
	 * setter와 getter 메소드를 작성하여 이것을 이용하도록 한다.
	 * 
	 * @param text
	 */
	public void setText(String text) {
		textField.setText(text);
	}
	
	/**
	 * EditText는 이 클래스와 이 클래스를 확장한 클래스에서만 접근이 가능하도록 하고,
	 * setter와 getter 메소드를 작성하여 이것을 이용하도록 한다.
	 * textField 내의 문자열을 리턴한다.
	 * 
	 * @return textField 내의 문자열 
	 */
	public String getText() {
		return textField.getText().toString();
	}

	/**
	 * EditText는 이 클래스와 이 클래스를 확장한 클래스에서만 접근이 가능하도록 하고,
	 * setter와 getter 메소드를 작성하여 이것을 이용하도록 한다.
	 * 
	 */
	public void requestFocusToTextField() {
		textField.requestFocus();
	}
}