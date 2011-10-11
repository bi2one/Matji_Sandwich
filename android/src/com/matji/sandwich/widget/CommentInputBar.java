package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.matji.sandwich.PostMainActivity;
import com.matji.sandwich.R;
import com.matji.sandwich.data.provider.DBProvider;
import com.matji.sandwich.util.MatjiConstants;

/**
 * {@link PostMainActivity}에서 사용하는 InputBar.
 * 
 * @author mozziluv
 *
 */
public class CommentInputBar extends InputBar {
    private ImageButton likeButton;

    public enum Type {
        PRIVATE, PUBLIC
    }

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

    public void refreshLikehand(int post_id) {
        DBProvider dbProvider = DBProvider.getInstance(getContext());
        if (dbProvider.isExistLike(post_id, DBProvider.POST)) {
            onLikehand();
        } else {
            offLikehand();
        }
    }

    public void offLikehand() {
        likeButton.setImageResource(R.drawable.icon_tabbtn_likehand);
    }

    public void setType(Type type) {
        if (type == Type.PRIVATE) {
            likeButton.setVisibility(View.GONE);
        } else if (type == Type.PUBLIC) {
            likeButton.setVisibility(View.VISIBLE);
        }
    }

    public void setLikeListener(OnClickListener listener) {
        likeButton.setOnClickListener(listener);
    }
}