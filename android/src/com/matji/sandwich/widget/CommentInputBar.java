package com.matji.sandwich.widget;

import com.matji.sandwich.PostMainActivity;
import com.matji.sandwich.R;
import com.matji.sandwich.util.MatjiConstants;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageButton;
import android.widget.LinearLayout;

/**
 * {@link PostMainActivity}에서 사용하는 InputBar.
 * 
 * @author mozziluv
 *
 */
public class CommentInputBar extends InputBar {
    private ImageButton likeButton;

    public CommentInputBar(Context context, AttributeSet attr) {
        super(context, attr);
    }

    @Override
    protected void init() {
        super.init();
        likeButton = new ImageButton(getContext());
        likeButton.setBackgroundResource(R.drawable.btn_likehand_bg);
        LinearLayout.LayoutParams params = 
            new LinearLayout.LayoutParams(
                    (int) MatjiConstants.dimen(R.dimen.likehand_btn_width),
                    (int) MatjiConstants.dimen(R.dimen.input_bar_item_height));
        params.setMargins((int) MatjiConstants.dimen(R.dimen.default_distance), 0, 0, 0);
        params.gravity = Gravity.CENTER_VERTICAL;
        likeButton.setLayoutParams(params);

        addView(likeButton, 0);
        setBackgroundResource(R.drawable.reply_bg);
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

    public void onLikehand() {
        likeButton.setImageResource(R.drawable.icon_tabbtn_likehand_touch);
    }

    public void offLikehand() {
        likeButton.setImageResource(R.drawable.icon_tabbtn_likehand);
    }

    public void setLikeListener(OnClickListener listener) {
        likeButton.setOnClickListener(listener);
    }
}