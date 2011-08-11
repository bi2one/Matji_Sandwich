package com.matji.sandwich.widget;

import com.matji.sandwich.PostActivity;
import com.matji.sandwich.R;
import com.matji.sandwich.util.MatjiConstants;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

/**
 * {@link PostActivity}에서 사용하는 InputBar.
 * 
 * @author mozziluv
 *
 */
public class CommentInputBar extends InputBar {
	private ImageButton likeButton;
	public static final int LIKE_BUTTON = 1010;

	public CommentInputBar(Context context, AttributeSet attr) {
		super(context, attr);
	}

	@Override
	protected void init() {
		super.init();
		likeButton = new ImageButton(getContext());
		likeButton.setImageDrawable(MatjiConstants.drawable(R.drawable.like_btn));
		likeButton.setTag(new Integer(LIKE_BUTTON));
		
		addView(likeButton, 0);
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

	public void setLikeListener(OnClickListener listener) {
		likeButton.setOnClickListener(listener);
	}
}