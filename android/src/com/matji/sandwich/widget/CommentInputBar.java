package com.matji.sandwich.widget;

import com.matji.sandwich.PostActivity;
import com.matji.sandwich.R;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.util.MatjiConstants;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

/**
 * {@link PostActivity}에서 사용하는 InputBar.
 * 
 * @author mozziluv
 *
 */
public class CommentInputBar extends InputBar {
	
	private Post post;
	private ImageButton likeButton;
	
	public CommentInputBar(Context context, AttributeSet attr) {
		super(context, attr);
	}

	@Override
	protected void init() {
		super.init();
		likeButton = new ImageButton(getContext());
		likeButton.setImageDrawable(MatjiConstants.drawable(R.drawable.like_btn));
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
	
	public void setPost(Post post) {
		this.post = post;
		likeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("Matji", "CommentInputBar : likebutton clicked");
			}
		});
	}
}