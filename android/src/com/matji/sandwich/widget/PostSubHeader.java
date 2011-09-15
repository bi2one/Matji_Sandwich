package com.matji.sandwich.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.matji.sandwich.R;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.util.MatjiConstants;

public class PostSubHeader extends ViewContainer {
    private Post post;
    private TextView shText;

    public PostSubHeader(Context context) {
        super(context, R.layout.header_post_sub);
        init();
    }

    public PostSubHeader(Context context, Post post) {
        this(context);
        init(post);
    }

    protected void init(Post post) {
        init();
        setPost(post);
    }

    protected void init() {
        shText = (TextView) getRootView().findViewById(R.id.header_post_sub_text);
    }

    public void setPost(Post post) {
        this.post = post;
        setViewData();
    }

    public void setViewData() {
        if (post.getLikeCount() > 0) {
            getRootView().setVisibility(View.VISIBLE);

            if (post.getLikeUser() == null) {
                shText.setText(
                        String.format(
                                MatjiConstants.string(R.string.post_sub_header_txt),
                                post.getLikeCount()));
            } else  if (post.getLikeCount() == 1) {
                shText.setText(
                        String.format(
                                MatjiConstants.string(R.string.post_sub_header_txt_one),
                                post.getLikeUser().getNick()));
            } else {
                shText.setText(
                        String.format(
                                MatjiConstants.string(R.string.post_sub_header_txt_other), 
                                post.getLikeUser().getNick(), 
                                post.getLikeCount() - 1));
            }
        } else {
            getRootView().setVisibility(View.GONE);
        }
    }
}