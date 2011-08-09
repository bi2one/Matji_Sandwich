package com.matji.sandwich.widget;

import com.matji.sandwich.CommentActivity;
import com.matji.sandwich.R;
import com.matji.sandwich.util.MatjiConstants;

import android.content.Context;
import android.util.AttributeSet;

/**
 * {@link CommentActivity}에서 사용하는 InputBar.
 * 
 * @author mozziluv
 *
 */
public class CommentInputBar extends InputBar {
	
	public CommentInputBar(Context context, AttributeSet attr) {
		super(context, attr);
	}

	@Override
	protected void setTextFieldAttribute() {
		textField.setHint(MatjiConstants.string(R.string.hint_write_comment));
		textField.setMaxLines(3);
	}

	@Override
	protected void setConfirmButtonAttribute() {
		confirmButton.setText(MatjiConstants.string(R.string.default_string_confirm));
	}	
}