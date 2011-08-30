package com.matji.sandwich.widget;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.matji.sandwich.R;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.data.SimpleTag;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.util.MatjiImageDownloader;
import com.matji.sandwich.listener.GotoImageSliderAction;
import com.matji.sandwich.listener.GotoStoreMainAction;
import com.matji.sandwich.listener.GotoUserMainAction;
import com.matji.sandwich.util.TimeUtil;

public class PostSubHeader extends ViewContainer {

	private int[] previewWrapperIds = {
			R.id.header_post_preview1,
			R.id.header_post_preview2,
			R.id.header_post_preview3
	};

	private MatjiImageDownloader downloader;

	private Post post;
	private LikeUsersElement holder;

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
		holder = new LikeUsersElement();
		
		holder.firstUserNick = (TextView) getRootView().findViewById(R.id.header_post_sub_first_user_nick);
//		holder.likeCount = (TextView) getRootView().findViewById(R.id.header_post_sub_like_count);
	}

	public void setPost(Post post) {
		this.post = post;
		setViewData();
	}
	
	public void setViewData() {
	    getRootView().setVisibility(View.GONE);
	    holder.firstUserNick.setText("");
	    holder.likeCount.setText("");
	}
	
    private class LikeUsersElement {
        private TextView firstUserNick;
        private TextView likeCount;
    }
}
